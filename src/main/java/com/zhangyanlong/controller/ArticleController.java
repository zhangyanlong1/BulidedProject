package com.zhangyanlong.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zhangyanlong.common.CmsContant;
import com.zhangyanlong.common.CmsError;
import com.zhangyanlong.common.CmsMessage;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Comment;
import com.zhangyanlong.entity.User;
import com.zhangyanlong.service.ArticleService;


@Controller
@RequestMapping("article")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
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
		public String comments(HttpServletRequest request,int id,int page) {
			PageInfo<Comment> commentPage =  articleService.getComments(id,page);
			request.setAttribute("commentPage", commentPage);
			return "comments";
		}	
}
