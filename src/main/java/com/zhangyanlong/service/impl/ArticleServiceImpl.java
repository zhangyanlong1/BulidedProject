package com.zhangyanlong.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyanlong.common.CmsContant;
import com.zhangyanlong.dao.ArticleMapper;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;
import com.zhangyanlong.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper mapper;
	@Override
	public PageInfo<Article> getArticleByUser(Integer id,int pageNum) {
		PageHelper.startPage(pageNum, 5);
		PageInfo<Article> pageInfo = new PageInfo<Article>(mapper.getArticleByUser(id));
		return pageInfo;
	}
	@Override
	public List<Channel> getChannels() {
		// TODO Auto-generated method stub
		return mapper.getAllChannels();
	}

	@Override
	public List<Category> getCategorisByCid(int cid) {
		// TODO Auto-generated method stub
		return mapper.getCategorisByCid(cid);
	}
	@Override
	public int add(Article article) {
		// TODO Auto-generated method stub
		return mapper.add(article);
	}
	@Override
	public int delete(int id) {
		
		return mapper.del(id,CmsContant.ARTICLE_STATUS_DEL);
	}
}
