package com.zhangyanlong.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.github.pagehelper.PageInfo;
import com.zhangyanlong.common.CmsError;
import com.zhangyanlong.common.CmsMessage;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.service.ArticleService;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	ArticleService service;
	
	/**
	 * 跳转到管理员的主页
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		System.out.println("1");
		return "admin/index";
	}
	
	/**
	 * 跳转到文章管理页面
	 * @return
	 */
	@RequestMapping("articles")
	public String article(@RequestParam(defaultValue = "1")int page,HttpServletRequest request) {
		PageInfo<Article>  pageInfo =  service.getList(page);
		request.setAttribute("page", pageInfo);
		return "admin/article/list";

	}
	
	/**
	 * 跳转到论坛管理页面
	 * @return
	 */
	@RequestMapping("comments")
	public String comment() {
		
		return "admin/comment/list";
	}
	
	@RequestMapping("setArticeHot")
	@ResponseBody
	public CmsMessage setArticeHot(int id,int status) {
		/**
		 * 数据合法性校验 
		 */
		if(status !=0 && status!=1) {
			
		}
		
		if(id<0) {
			
		}
		
		Article article = service.getInfoById(id);
		if(article==null) {
			
		}
		if(article.getStatus()==status) {
			
		}
		int result = service.setHot(id,status);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
		
	}
	
	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("setArticeStatus")
	@ResponseBody
	public CmsMessage  setArticeStatus(int id,int status) {
		
		/**
		 * 数据合法性校验 
		 */
		if(status !=1 && status!=2) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"status参数值不合法",null);
		}
		
		if(id<0) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"id参数值不合法",null);
		}
		
		Article article = service.getInfoById(id);
		if(article==null) {
			return new CmsMessage(CmsError.NOT_EXIST,"数据不存在",null);
		}
		
		/**
		 * 
		 */
		if(article.getStatus()==status) {
			return new CmsMessage(CmsError.NEEDNT_UPDATE,"数据无需更改",null);
		}
		
		/**
		 *  修改数据
		 */
		int result = service.setCheckStatus(id,status);
		if(result<1)
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
		
	}
	
}
