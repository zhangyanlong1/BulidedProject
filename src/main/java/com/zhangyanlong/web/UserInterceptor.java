package com.zhangyanlong.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhangyanlong.common.CmsContant;
import com.zhangyanlong.entity.User;



public class UserInterceptor implements HandlerInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
	
				User loginUser = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
				if(loginUser == null)
				{
					//request.s
					response.sendRedirect("/user/login");
					//request.getRequestDispatcher("/user/login").forward(request, response);
					return false;
				}
				
				//放行 
				return true;
	}
	
	
}
