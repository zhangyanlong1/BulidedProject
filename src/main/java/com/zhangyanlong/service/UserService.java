package com.zhangyanlong.service;

import javax.validation.Valid;

import com.zhangyanlong.entity.User;

public interface UserService {

	User getUserByUsername(String username);

	int register(@Valid User user);

}
