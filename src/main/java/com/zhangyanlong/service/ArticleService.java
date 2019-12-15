package com.zhangyanlong.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;

public interface ArticleService {

	PageInfo<Article> getArticleByUser(Integer id, int pageNum);

	List<Category> getCategorisByCid(int cid);
	
	List<Channel> getChannels();

	int add(Article article);

	int delete(int id);
}
