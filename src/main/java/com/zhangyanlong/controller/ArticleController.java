package com.zhangyanlong.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.utils.StringUtils;
import com.zhangyanlong.common.CmsContant;
import com.zhangyanlong.common.CmsError;
import com.zhangyanlong.common.CmsMessage;
import com.zhangyanlong.dao.ArticleRep;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Channel;
import com.zhangyanlong.entity.Comment;
import com.zhangyanlong.entity.Complain;
import com.zhangyanlong.entity.Slide;
import com.zhangyanlong.entity.User;
import com.zhangyanlong.service.ArticleService;
import com.zhangyanlong.utils.HLUtils;

@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{

	@Autowired
	ArticleService articleService;
	
	@Autowired
	ArticleRep articleRep;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	/**
	 * @param id
	 * @return
	 */
	
	
	@RequestMapping("search")
	public String searchList(String key,Model m,HttpServletRequest request,@RequestParam(defaultValue="1") int page) {
		
		
		Thread  t1 =  new Thread() {
			public void run() {
		// 获取所有的栏目
		List<Channel> channels = articleService.getChannels();
		request.setAttribute("channels", channels);
			};
		};
		
		Thread  t3 =  new Thread() {
			public void run() {
				//1.先从redis 中获取列表
				//判断列表是否为空
				//如果为空则从mysql中查询列表  存入redis 中 并且返回 页面
				//如果不为空直接  返回页面
				
				// 获取最新文章
				//0.redis作为缓存来优化最新文章
				//1.从reids中查询最新文章
				List<Article> redisArticles = redisTemplate.opsForList().range("new_articles", 0, -1);
				if(redisArticles==null||redisArticles.size()==0) {
					//2.判断Redis中查询的是否为空(有没有最新文章)
					//3.如果为空
					//4.从mysql中查询最新文章,放入redis,
					List<Article> lastArticles = articleService.lastList();
					System.err.println("从mysql中查询了最新文章...");
					redisTemplate.opsForList().leftPushAll("new_articles", lastArticles.toArray());
					redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);
					//并且返回给前台
					request.setAttribute("lastArticles", lastArticles);
				}else {
					//5.如果不为空,直接返回给前台
					System.err.println("从redis中查询了最新文章...");
					request.setAttribute("lastArticles", redisArticles);
				}
				
				
				
		// 获取最新文章
//		List<Article> lastArticles = articleService.lastList();
//		request.setAttribute("lastArticles", lastArticles);
		
		
			};
		};
		t1.start();
		t3.start();
		//利用es的仓库来查询  无高亮
//		List<Article> list = articleRep.findByTitle(key);
//		PageInfo<Article> pageInfo = new PageInfo<Article>(list);
		//高亮显示
		PageInfo<Article> findByHighLight = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, page, 3, new String[] {"title"}, "id", key);
		System.out.println(findByHighLight);
		m.addAttribute("key", key);
		m.addAttribute("articlePage", findByHighLight);
		return "index";	
	}
	
	
	
	@RequestMapping("getDetail")
	@ResponseBody
	public CmsMessage getDetail(int id) {
		if(id<=0) {
			
		}
		// 获取文章详情
		Article article = articleService.getById(id);
		System.out.println(article);
		// 不存在
		if(article==null)
			return new CmsMessage(CmsError.NOT_EXIST, "文章不存在",null);
		
		// 返回数据
		return new CmsMessage(CmsError.SUCCESS,"",article); 
		
	}
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int id,
			@RequestParam(defaultValue = "0")Integer channelId,
			@RequestParam(defaultValue = "0")Integer categoryId,
			@RequestParam(defaultValue = "-1")Integer hotId
			,@RequestParam(defaultValue = "0")Integer page) {
		System.out.println(channelId);
		System.out.println(categoryId);
		System.out.println(hotId);
		System.out.println(page);
		PageInfo<Article> pageInfo = articleService.getListById(page,id,channelId,categoryId,hotId);	
		request.setAttribute("channel_id", channelId);
		request.setAttribute("category_id", categoryId);
		request.setAttribute("hotId",hotId );
		request.setAttribute("page", pageInfo);
		return "detail";
	}
	
	@RequestMapping("postcomment")
	@ResponseBody
	public CmsMessage postcomment(HttpServletRequest request,int articleId,String content) {
		
		User loginUser  = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		
		if(loginUser==null) {
			return new CmsMessage(CmsError.NOT_LOGIN, "您尚未登录！", null);
		}
		
		Comment comment = new Comment();
		comment.setUserId(loginUser.getId());
		comment.setContent(content);
		comment.setArticleId(articleId);
		int result = articleService.addComment(comment);
		if(result > 0)
			return new CmsMessage(CmsError.SUCCESS, "成功", null);
		
		return new CmsMessage(CmsError.FAILED_UPDATE_DB, "异常原因失败，请与管理员联系", null);
		
	}
	// {articleId:'${article.id}',content:$("#co
		//comments?id
		@RequestMapping("comments")
		public String comments(HttpServletRequest request,int id,@RequestParam(defaultValue = "1")int page) {
			PageInfo<Comment> commentPage =  articleService.getComments(id,page);
			System.out.println(commentPage);
			request.setAttribute("commentPage", commentPage);
			return "comments";
		}	
		
		/**
		 * 跳转到投诉的页面
		 * @param request
		 * @param articleId
		 * @return
		 */
		@RequestMapping(value="complain",method=RequestMethod.GET)
		public String complain(HttpServletRequest request,Integer articleId) {
			Article article= articleService.getById(articleId);
			request.setAttribute("article", article);
			request.setAttribute("complain", new Complain());
			return "article/complain";
					
		}
		
		/**
		 * 接受投诉页面提交的数据
		 * @param request
		 * @param articleId
		 * @return
		 * @throws IOException 
		 * @throws IllegalStateException 
		 */
		@RequestMapping(value="complain",method=RequestMethod.POST)
		public String complain(HttpServletRequest request,
				@ModelAttribute("complain") @Valid Complain complain,
				BindingResult result,MultipartFile file) throws IllegalStateException, IOException {
			System.out.println(complain);
			if(StringUtils.isHttpUrl(complain.getSrcUrl())) {
				result.rejectValue("srcUrl", "", "不是合法的url地址");
			}
			if(result.hasErrors()) {
				Article article= articleService.getById(complain.getArticleId());
				request.setAttribute("article", article);
				return "article/complain";
			}
			
			User loginUser  =  (User)request.getSession().getAttribute(CmsContant.USER_KEY);
			
			String picUrl = this.processFile(file);
			complain.setPicture(picUrl);

			//加上投诉人
			if(loginUser!=null)
				complain.setUserId(loginUser.getId());
			else
				complain.setUserId(0);
			
			articleService.addComplain(complain);
			
			return "redirect:/article/detail?id="+complain.getArticleId();
					
		}
		
			//complains?articleId
			@RequestMapping("complains")
			public String 	complains(HttpServletRequest request,int articleId,
					@RequestParam(defaultValue="1") int page) {
				PageInfo<Complain> complianPage=   articleService.getComplains(articleId, page);
				request.setAttribute("complianPage", complianPage);
				return "article/complainslist";
			}
		
			
		
}
