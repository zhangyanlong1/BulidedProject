package com.zhangyanlong.service.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		
		return 0;
	}

	
}
