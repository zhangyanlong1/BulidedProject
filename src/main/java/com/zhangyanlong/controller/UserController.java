package com.zhangyanlong.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utils.StringUtils;
import com.zhangyanlong.entity.User;
import com.zhangyanlong.service.UserService;

/**
 * 
 * @author DELL
 *
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService service ;
	@RequestMapping("home")
	public String home() {
		
		
		return "user/home";
	}
	
	@RequestMapping("login")
	public String login() {
		
		return "redirect:home";
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
		System.out.println("111");
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
		User existUser = service.getUserByUsername(user.getUsername());
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
		int reRegister = service.register(user);
		
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
		User exName = service.getUserByUsername(username);
		return exName==null;
	}
	
}
