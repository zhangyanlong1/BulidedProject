package com.zhangyanlong;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhangyanlong.dao.ArticleMapper;
import com.zhangyanlong.dao.ArticleRep;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ImportMysql2Es {
	
	@Autowired
	ArticleMapper articleMapper; 
	@Autowired
	ArticleRep articleRep;
	
	@Test
	public void importMysql2es() {
		//从mysql中查询已经审核通过的所有文章
		//2.查询出来的文章保存到es索引库
		List<Article> findAllByStatus = articleMapper.findAllByStatus(1);
		articleRep.saveAll(findAllByStatus);
	}
}
