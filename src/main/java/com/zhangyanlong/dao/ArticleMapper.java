package com.zhangyanlong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;

public interface ArticleMapper {

	public List<Article> getArticleByUser(Integer id);
	/**
	 * 获取所有栏目的方法
	 * @return
	 */
	@Select("SELECT id,name FROM cms_channel")
	public List<Channel> getAllChannels();
	/**
	 * 根据栏目id 获取分类
	 * @cid  ： 栏目的id
	 * @return
	 */
	@Select("SELECT id,name FROM cms_category WHERE channel_id = #{value}")
	public List<Category> getCategorisByCid(int cid);
	
	@Insert("INSERT INTO cms_article(title,content,picture,channel_id,category_id,user_id,hits,hot,status,deleted,created,updated,commentCnt,articleType)"
			+ " VALUES(#{title},#{content},#{picture},#{channelId},#{categoryId},#{userId},0,0,0,0,now(),now(),0,#{articleType})")
	public int add(Article article);
	
	@Update("UPDATE cms_article SET deleted=#{deleted} WHERE id=#{id}")
	public int del(@Param("id")int id,@Param("deleted")int status);

}
