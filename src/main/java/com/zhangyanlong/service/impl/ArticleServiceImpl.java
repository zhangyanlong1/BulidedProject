package com.zhangyanlong.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyanlong.common.CmsContant;
import com.zhangyanlong.dao.ArticleMapper;
import com.zhangyanlong.dao.SlideMapper;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;
import com.zhangyanlong.entity.Comment;
import com.zhangyanlong.entity.Slide;
import com.zhangyanlong.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper mapper;
	@Autowired
	SlideMapper slideMapper;
	
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
	@Override
	public int update(Article article, Integer id) {
		
		return 0;
	}
	@Override
	public Article getById(int id) {
		
		return mapper.findById(id);
	}
	@Override
	public PageInfo<Article> getList(int page) {
		PageHelper.startPage(page, 5);
		
		return new PageInfo<Article>(mapper.getList());
	}
	@Override
	public Article getInfoById(int id) {
		return mapper.getInfoById(id);
	}

	/**
	 * 
	 */
	@Override
	public int setHot(int id, int status) {
		return mapper.setHot(id,status);
	}

	@Override
	public int setCheckStatus(int id, int status) {
		 return mapper.CheckStatus(id,status);
	}

	@Override
	public List<Article> lastList() {
		// TODO Auto-generated method stub
		return mapper.lastList(CmsContant.PAGE_SIZE);
	}

	
	@Override
	public List<Slide> getSlides() {
		// TODO Auto-generated method stub
		return slideMapper.list();
	}

	@Override
	public PageInfo<Article> getArticles(int channleId, int catId, int page) {
		
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		
		return new PageInfo<Article>(mapper.getArticles(channleId, catId));
	}

	@Override
	public List<Category> getCategoriesByChannelId(int channleId) {
		// TODO Auto-generated method stub
		return mapper.getCategoriesByChannelId(channleId) ;
	}
	
	@Override
	public PageInfo<Article> hotList(int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page,CmsContant.PAGE_SIZE);
		return new PageInfo<>(mapper.hostList());
	}
	
	
	
	@Override
	public int addComment(Comment comment) {
		// TODO Auto-generated method stub
		int result =  mapper.addComment(comment);
		 //文章评论数目自增
		if(result>0)
			mapper.increaseCommentCnt(comment.getArticleId());
		
		return result;
	}

	@Override
	public PageInfo<Comment> getComments(int articleId, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, CmsContant.PAGE_SIZE);
		return new PageInfo<Comment>(mapper.getComments(articleId));
		
		
	}
	
}
