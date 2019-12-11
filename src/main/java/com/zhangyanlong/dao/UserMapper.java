package com.zhangyanlong.dao;

import org.apache.ibatis.annotations.Select;

import com.zhangyanlong.entity.User;

/**
 * 
 * @author DELL
 *
 */
public interface UserMapper {

	@Select("")
	User getUserByUsername(String username);

}
