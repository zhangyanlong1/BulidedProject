package com.zhangyanlong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.zhangyanlong.entity.Domain;
/**
 * dao层
 * @author DELL
 *
 */
public interface DomainMapper {

	//查询的sql
	@Select("select * from domain where user_id = #{id} order by created desc ")
	List<Domain> select(Integer id);
	//删除的sql
	@Delete("delete from domain where id=#{id}")
	int delDomain(Integer id);
	//添加的sql
	@Insert("insert into domain values(0,#{text},#{url},#{user_id},now())")
	int addDomain(Domain domain);

	
	
}
