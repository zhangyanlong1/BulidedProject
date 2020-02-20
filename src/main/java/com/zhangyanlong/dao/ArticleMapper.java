package com.zhangyanlong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.zhangyanlong.entity.Article;
import com.zhangyanlong.entity.Category;
import com.zhangyanlong.entity.Channel;
import com.zhangyanlong.entity.Comment;
import com.zhangyanlong.entity.Complain;

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
	
	public int add(Article article);
	
	@Update("UPDATE cms_article SET deleted=#{deleted} WHERE id=#{id}")
	public int del(@Param("id")int id,@Param("deleted")int status);
	
	
	public Article findById(int id);
	
	
	@Update("UPDATE cms_article SET title=#{title},content=#{content},picture=#{picture},channel_id=#{channelId},"
			+ " category_id=#{categoryId},status=0,"
			+ "updated=now() WHERE id=#{id} ")
	int update(Article article);
	
	
	List<Article> lastList(int pageSize);
	
	/**
	 * 
	 * @return
	 */
	public List<Article> getList();

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT id,title,channel_id channelId , category_id categoryId,status ,hot "
			+ " FROM cms_article WHERE id = #{value} ")
	Article getInfoById(int id);

	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("UPDATE cms_article SET hot=#{hot} WHERE id=#{myid}")
	int setHot(@Param("myid") int id, @Param("hot") int status);

	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("UPDATE cms_article SET status=#{myStatus} WHERE id=#{myid}")
	int CheckStatus(@Param("myid") int id, @Param("myStatus") int status);
	
	/**
	 * 根据分类和栏目获取文章
	 * @param channleId
	 * @param catId
	 * @return
	 */
	List<Article> getArticles(@Param("channelId")  int channleId, @Param("catId") int catId);

	/**
	 * 
	 * @param channleId
	 * @return
	 */
	@Select("SELECT id,name FROM cms_category where channel_id=#{value}")
	@ResultType(Category.class)
	List<Category> getCategoriesByChannelId(int channleId);
	
	/**
	 * 热门列表
	 * @return
	 */
	List<Article> hostList();
	
	
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	@Insert("INSERT INTO cms_comment(articleId,userId,content,created)"
			+ " VALUES(#{articleId},#{userId},#{content},NOW())")
	int addComment(Comment comment);
	
	/**
	 * 增加文章的评论数量
	 * @param id
	 * @return
	 */
	@Update("UPDATE cms_article SET commentCnt=commentCnt+1 WHERE id=#{value}")
	int increaseCommentCnt(int id);

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	@Select("SELECT c.id,c.articleId,c.userId,u.username as userName,c.content,c.created FROM cms_comment as c "
			+ " LEFT JOIN cms_user as u ON u.id=c.userId "
			+ " WHERE articleId=#{value} ORDER BY c.created DESC")
	List<Comment> getComments(int articleId);
	
	
	public List<Article> getListById(@Param("channelId")int channelId, @Param("categoryId")int categoryId, @Param("hotId")int hotId);
	
	/**
	 * 
	 * @param complain
	 * @return
	 */
	@Insert("INSERT INTO cms_complain(article_id,user_id,complain_type,"
			+ "compain_option,src_url,picture,content,email,mobile,created)"
			+ "   VALUES(#{articleId},#{userId},"
			+ "#{complainType},#{compainOption},#{srcUrl},#{picture},#{content},#{email},#{mobile},now())")
	int addCoplain(Complain complain);

	/**
	 * 
	 * @param articleId
	 */
	@Update("UPDATE cms_article SET complainCnt=complainCnt+1,status=if(complainCnt>10,2,status)  "
			+ " WHERE id=#{value}")
	void increaseComplainCnt(Integer articleId);

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	List<Complain> getComplains(int articleId);
	
	@Select("select * from cms_article where status=#{i} and deleted=0")
	List<Article> findAllByStatus(int i);
	
	
}
