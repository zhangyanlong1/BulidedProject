package com.zhangyanlong.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.service.ArticleService;

public class MsgListener implements MessageListener<String, String>{

	@Autowired
	ArticleService articleService;
	
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		String value = data.value();
		Article article = JSON.parseObject(value,Article.class);
		
		article.setPicture("D://pic");
		article.setCategoryId(1);
		article.setChannelId(1);
		article.setUserId(36);
		article.setArticleType(0);
		System.out.println("添加成功");
		articleService.add(article);
		
	}
	
}
