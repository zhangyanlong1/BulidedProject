package com.zhangyanlong.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.github.pagehelper.PageInfo;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;
import com.zhangyanlong.entity.Slide;
import com.zhangyanlong.service.ArticleService;

/**
 * 
 * @author zhuzg
 *
 */
@Controller
@RequestMapping(value= {"index","/"})
public class IndexController {
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping(value= {"index","/"})
	public String index(HttpServletRequest request,@RequestParam(defaultValue="1") int page) throws InterruptedException {
		
		Thread  t1 =  new Thread() {
			public void run() {
		// 获取所有的栏目
		List<Channel> channels = articleService.getChannels();
		request.setAttribute("channels", channels);
			};
		};
		
		Thread  t2 =  new Thread() {
			public void run() {
		// 获取热门文章
		//优化热门文章		
			
				List<Article> hotList = redisTemplate.opsForList().range("hot_articles", 0, -1);
				if(hotList==null||hotList.size()==0) {
					//2.判断Redis中查询的是否为空(有没有最新文章)
					//3.如果为空
					//4.从mysql中查询最新文章,放入redis,
					PageInfo<Article> pageInfo = articleService.hotList(page);
					System.err.println(pageInfo);
					List<Article> list = pageInfo.getList();
					System.err.println("1从mysql中查询了热门文章...");
					redisTemplate.opsForList().leftPushAll("hot_articles",list);
					redisTemplate.expire("hot_articles", 5, TimeUnit.MINUTES);
					//并且返回给前台
					request.setAttribute("articlePage", pageInfo);
				}else {
					
					PageInfo<Article> pageInfo =new PageInfo<Article>(hotList);
					
					//5.如果不为空,直接返回给前台
					System.err.println(pageInfo);
					System.err.println("2从redis中查询了热门文章...");
					request.setAttribute("articlePage", pageInfo);
				}
				
//				PageInfo<Article> articlePage= articleService.hotList(page);
//				request.setAttribute("articlePage", articlePage);
			};
		};
		
		Thread  t3 =  new Thread() {
			public void run() {
				//1.先从redis 中获取列表
				//判断列表是否为空
				//如果为空则从mysql中查询列表  存入redis 中 并且返回 页面
				//如果不为空直接  返回页面
				
				// 获取最新文章
				//0.redis作为缓存来优化最新文章
				//1.从reids中查询最新文章
				List<Article> redisArticles = redisTemplate.opsForList().range("new_articles", 0, -1);
				if(redisArticles==null||redisArticles.size()==0) {
					//2.判断Redis中查询的是否为空(有没有最新文章)
					//3.如果为空
					//4.从mysql中查询最新文章,放入redis,
					List<Article> lastArticles = articleService.lastList();
					System.err.println("从mysql中查询了最新文章...");
					redisTemplate.opsForList().leftPushAll("new_articles", lastArticles.toArray());
					redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);
					//并且返回给前台
					request.setAttribute("lastArticles", lastArticles);
				}else {
					//5.如果不为空,直接返回给前台
					System.err.println("从redis中查询了最新文章...");
					request.setAttribute("lastArticles", redisArticles);
				}
				
				
				
		// 获取最新文章
//		List<Article> lastArticles = articleService.lastList();
//		request.setAttribute("lastArticles", lastArticles);
		
		
			};
		};
		
		Thread  t4 =  new Thread() {
			public void run() {
		// 轮播图
		List<Slide> slides = articleService.getSlides();
		request.setAttribute("slides", slides);
		
			};
		};
		
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		
		return "index";
		
	}
	
	/**
	 * 
	 * @param request  请求
	 * @param channleId  栏目的id
	 * @param catId 分类的id
	 * @param page 页码
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping("channel")
	public String channel(HttpServletRequest request,
			int channelId,
			@RequestParam(defaultValue="0") int catId,
			@RequestParam(defaultValue="1")  int page) throws InterruptedException {
		
		Thread  t1 =  new Thread() {
			public void run() {
		// 获取所有的栏目
		List<Channel> channels = articleService.getChannels();
		request.setAttribute("channels", channels);
			};
		};
		
		Thread  t2 =  new Thread() {
			public void run() {
		// 当前栏目下  当前分类下的文章
		PageInfo<Article> articlePage= articleService.getArticles(channelId,catId, page);
		request.setAttribute("articlePage", articlePage);
			};
		};
		
		Thread  t3 =  new Thread() {
			public void run() {
		// 获取最新文章
		List<Article> lastArticles = articleService.lastList();
		request.setAttribute("lastArticles", lastArticles);
			};
		};
		
		Thread  t4 =  new Thread() {
			public void run() {
		// 轮播图
		List<Slide> slides = articleService.getSlides();
		request.setAttribute("slides", slides);
		
			};
		};
		
		// 获取当前栏目下的所有的分类 catId
		Thread  t5 =  new Thread() {
			public void run() {
		// 
		List<Category> categoris= articleService.getCategoriesByChannelId(channelId);
		request.setAttribute("categoris", categoris);
		System.err.println("categoris is " + categoris);
			};
		};
		
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		
		// 参数回传
		request.setAttribute("catId", catId);
		request.setAttribute("channelId", channelId);
		
		return "channel";
	}
}
