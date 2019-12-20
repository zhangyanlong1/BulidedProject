package com.zhangyanlong.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.utils.FileUtils;
import com.utils.HtmlUtils;
import com.utils.StringUtils;
import com.zhangyanlong.common.CmsContant;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;
import com.zhangyanlong.entity.User;
import com.zhangyanlong.service.ArticleService;
import com.zhangyanlong.service.UserService;

/**
 * 
 * @author DELL
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Value("${upload.path}")
	String picRootPath;
	
	@Value("${pic.path}")
	String picUrl;
	
	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService ;
	
	/**
	 * 跳转普通用户的个人中心
	 * @return
	 */
	@RequestMapping("home")
	public String home() {
		return "user/home";
	}
	
	
	/**
	 * 跳转个人中心
	 * @param request
	 * @return
	 */
	@RequestMapping("personCenter")
	public String home(HttpServletRequest request) {
		User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		if(loginUser.getRole()==CmsContant.USER_ROLE_ADMIN) {
			return "forward:/admin/index";
		}
		
		System.out.println("2");
		return "user/home";
	}
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value = "login",method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		
		return "user/login";
	}
	
	/**
	 * 登录操作
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "login",method = RequestMethod.POST)
	public String login(HttpServletRequest request,User user) {
		User loginUser = userService.login(user);
		
		if(loginUser==null) {
			request.setAttribute("err", "用户名或密码错误！");
			return "user/login";
		}
		
		//登录成功
		request.getSession().setAttribute(CmsContant.USER_KEY,loginUser);
		
		//跳转到主页
		return "forward:/index/index";
	}
	
	
	/**
	 * 退出登录的操作
	 */
	@RequestMapping("outUser")
	public String outUser(HttpServletRequest request) {
		request.getSession().removeAttribute(CmsContant.USER_KEY);
		return "redirect:/index/index";
	}
	
	/**
	 * 跳转到注册页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "register",method = RequestMethod.GET)
	public String register(HttpServletRequest request) {
		User user = new User();
		request.setAttribute("user", user);
		return "user/register";
		
	}
	
	
	/**
	 * 从注册也没法送过来的请求
	 */
	@RequestMapping(value = "register",method = RequestMethod.POST)
	public String register(HttpServletRequest  request,
			@Valid@ModelAttribute("user")User user,
			BindingResult result
			) {
		
		
		//如果有错误信息 返回
		if(result.hasErrors()) {
			return "user/register";
		}
		
		//进行用户是否已经存在的验证
		User existUser = userService.getUserByUsername(user.getUsername());
		if(existUser!=null) {
			result.rejectValue("username", "","用户名已存在");
			return "user/register";
		}
		
		
		//加一个手动验证
		if(StringUtils.isNumber(user.getPassword())) {
			result.rejectValue("password", "", "密码不能全是数字");
			return "user/register";
		//lang.NoClassDefFoundE
		}
		
		//去注册
		int reRegister = userService.register(user);
		
		//注册失败
		if(reRegister<1) {
			request.setAttribute("err", "注册失败请稍后再试!");
			return "user/register";
		}
		//跳转到登录页面
		return "user/login";
		
	}
	
	/**
	 * 检测姓名唯一性
	 */
	@RequestMapping("checkname")
	@ResponseBody
	public boolean checkUserName(String username) {
		User exName = userService.getUserByUsername(username);
		return exName==null;
	}
	

	//跳转我的文章
	@RequestMapping("articles")
	public String articles(HttpServletRequest request,@RequestParam(defaultValue = "1")int pageNum) {
		 User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		 System.out.println(loginUser);
		 PageInfo<Article> pageInfo =articleService.getArticleByUser(loginUser.getId(),pageNum);
		 request.setAttribute("page", pageInfo);
		return "user/article/list";
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping("deletearticle")
	@ResponseBody
	public boolean deleteArticle(int id) {
		int result  = articleService.delete(id);
		return result > 0;
	}
	
	
	//跳转我的论坛
	@RequestMapping("comments")
	public String comments() {
		return "user/comment/list";
	}
	
	//跳转发表文章
	@RequestMapping("articlePutUp")
	public String articlePutUp(HttpServletRequest request) {
		List<Channel> channels= articleService.getChannels();
		request.setAttribute("channels", channels);
		return "user/article/articlePutUp";
	}
	
	/**
	 * 	执行添加文章
	 * @param request
	 * @param article
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "postArticle",method=RequestMethod.POST)
	@ResponseBody
	public boolean postArticle(HttpServletRequest request, Article article, 
			MultipartFile file
			) {
		String picUrl;
		try {
			// 处理上传文件
			picUrl = processFile(file);
			article.setPicture(picUrl);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//当前用户是文章的作者
		User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		article.setUserId(loginUser.getId());
		return articleService.add(article)>0;
	}
	

	/**
	 * 跳转到修改文章的页面
	 * @return
	 */
	@RequestMapping(value="updateArticle",method=RequestMethod.GET)
	public String updateArticle(HttpServletRequest request,int id) {	
		
		//获取栏目
		List<Channel> channels= articleService.getChannels();
		request.setAttribute("channels", channels);
		
		//获取文章
		Article article = articleService.getById(id);
		User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		if(loginUser.getId() != article.getUserId()) {
			// todo 准备做异常处理的！！
		}
		request.setAttribute("article", article);
		request.setAttribute("content1",  HtmlUtils.htmlspecialchars(article.getContent()));
		return "user/article/update";
	}
	
	
	
	/**
	 * 接受修改文章的页面提交的数据
	 * @return
	 */
	@RequestMapping(value="updateArticle",method=RequestMethod.POST)
	@ResponseBody
	public  boolean  updateArticle(HttpServletRequest request,Article article,MultipartFile file) {
		
		System.out.println("aarticle is  "  + article);
		
		String picUrl;
		try {
			// 处理上传文件
			picUrl = processFile(file);
			article.setPicture(picUrl);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//当前用户是文章的作者
		User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		//article.setUserId(loginUser.getId());
		int updateREsult  = articleService.update(article,loginUser.getId());
		
		
		return updateREsult>0;
		
	}
	
	
	/**
	 * 
	 * @param request
	 * @param cid
	 * @return
	 */
	@RequestMapping("getCategoris")
	@ResponseBody
	public List<Category>  getCategoris(int cid) {	
		List<Category> categoris = articleService.getCategorisByCid(cid);
		System.out.println("吱吱吱吱");
		return categoris;
	}
	
	//跳转用户设置
		@RequestMapping("userSetting")
		public String userSetting() {
			return "user/setting/settingUser";
		}
		
		
		/**
		 * 跳转到首页
		 */
		@RequestMapping("index")
		public String index() {
			
			return "index";
			
		}
		
		
		
		/**
		 * 	处理传的数据
		 * @param file
		 * @return
		 * @throws IOException 
		 * @throws IllegalStateException 
		 */
		private String processFile(MultipartFile file) throws IllegalStateException, IOException {
			// 判断目标目录时间否存在
			//picRootPath + ""
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String subPath = sdf.format(new Date());
			//图片存放的路径
			File path= new File(picRootPath+"/" + subPath);
			//路径不存在则创建
			if(!path.exists())
				path.mkdirs();
			
			//计算新的文件名称
			String suffixName = FileUtils.getSuffixName(file.getOriginalFilename());
			
			//随机生成文件名
			String fileName = UUID.randomUUID().toString() + suffixName;
			//文件另存
			file.transferTo(new File(picRootPath+"/" + subPath + "/" + fileName));
			return  subPath + "/" + fileName;
			
		}
}
