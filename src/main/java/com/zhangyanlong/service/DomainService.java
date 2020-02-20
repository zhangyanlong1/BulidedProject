package com.zhangyanlong.service;

import java.util.List;

import com.zhangyanlong.entity.Domain;

public interface DomainService {

	//查询列表
	List<Domain> select(Integer id);
	
	//保存收藏
	int addDomain(Domain domain);
	
	//删除收藏
	boolean delDomain(Integer id);
	
}
