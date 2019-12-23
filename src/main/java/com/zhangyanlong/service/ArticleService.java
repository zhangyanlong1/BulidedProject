package com.zhangyanlong.service;


import java.util.List;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;
import com.zhangyanlong.entity.Comment;
import com.zhangyanlong.entity.Complain;
import com.zhangyanlong.entity.Slide;

public interface ArticleService {

	/**
	 * 根据用户获取文章
	 * @param id
	 * @param pageNum
	 * @return
	 */
	PageInfo<Article> getArticleByUser(Integer id, int pageNum);

	/**
	 * 根据id获取栏目 列表
	 * @param cid
	 * @return
	 */
	List<Category> getCategorisByCid(int cid);
	/**
	 * 获取分类列表
	 * @return
	 */
	List<Channel> getChannels();

	/**
	 * 添加文章
	 * @param article
	 * @return
	 */
	int add(Article article);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	/**
	 * 修改文章
	 * @param article
	 * @param id
	 * @return
	 */
	int update(Article article, Integer id);
	/**
	 * 根据id  获取文章
	 * @param id
	 * @return
	 */
	Article getById(int id);

	/**
	 * 获取文章列表
	 * @param page
	 * @return
	 */
	PageInfo<Article> getList(int page);

	/**
	 * 修改审核状态
	 * @param id
	 * @param status
	 * @return
	 */
	int setCheckStatus(int id, int status);

	Article getInfoById(int id);

	int setHot(int id, int status);
	
	
	/**
	 * 获取热门文章
	 * @param page
	 * @return
	 */
	PageInfo<Article> hotList(int page);

	/**
	 * 获取最新文章NAG
	 * @return
	 */
	List<Article> lastList();

	/**
	 * 获取轮播图
	 * @return
	 */
	List<Slide> getSlides();

	/**
	 * 获取栏目下的文章
	 * @param channleId
	 * @param catId
	 * @param page
	 * @return
	 */
	PageInfo<Article> getArticles(int channleId, int catId, int page);

	/**
	 * 获取栏目下的分类
	 * @param channleId
	 * @return
	 */
	List<Category> getCategoriesByChannelId(int channleId);
/**
 * 实现添加评论
 * @param comment
 * @return
 */
	int addComment(Comment comment);

	/**
	 * 查询评论
	 * @param id
	 * @param page
	 * @return
	 */
	PageInfo<Comment> getComments(int id, int page);

	PageInfo<Article> getListById(int page,int id, int channelId, int categoryId, int hotId);

	PageInfo<Complain> getComplains(int articleId, int page);

	int addComplain(@Valid Complain complain);
	
}
