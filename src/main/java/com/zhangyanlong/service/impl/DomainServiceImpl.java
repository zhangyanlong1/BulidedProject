package com.zhangyanlong.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.utils.StringUtils;
import com.zhangyanlong.dao.DomainMapper;
import com.zhangyanlong.entity.Domain;
import com.zhangyanlong.service.DomainService;
/**
 * 业务层
 */
@Service
public class DomainServiceImpl implements DomainService {

	@Autowired
	DomainMapper domainMapper;
	
	@Override
	public List<Domain> select(Integer id) {
		
		return domainMapper.select(id);
	}

	@Override
	public boolean delDomain(Integer id) {
		
		return domainMapper.delDomain(id)>0;
	}

	@Override
	public int addDomain(Domain domain) {
		
		String url = domain.getUrl();
		boolean httpUrl = StringUtils.isHttpUrl(url);
		System.out.println("aaa"+httpUrl);
		//如果符合地址规范就添加 返回1 否则返回2
		if(httpUrl) {
			domainMapper.addDomain(domain);
			return 1;
		}else {
			return 2;
		}
	}

}
