package com.zhangyanlong.service.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangyanlong.common.CmsUtils;
import com.zhangyanlong.dao.UserMapper;
import com.zhangyanlong.entity.User;
import com.zhangyanlong.service.UserService;
/**
 * 
 * @author DELL
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper mapper;
	
	
	@Override
	public User getUserByUsername(String username) {
		
		return mapper.getUserByUsername(username);
	}

	@Override
	public int register(@Valid User user) {
		String encryPwd=CmsUtils.encry(user.getPassword(), user.getUsername());
		user.setPassword(encryPwd);
		mapper.add(user);
		return mapper.add(user);
	}

	@Override
	public User login(User user) {
		user.setPassword(CmsUtils.encry(user.getPassword(), user.getUsername()));
		
		return mapper.login(user);
	}

}
