package com.zhangyanlong.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zhangyanlong.entity.Article;

public interface ArticleRep extends ElasticsearchRepository<Article, Integer>{

	List<Article> findByTitle(String title);
}
