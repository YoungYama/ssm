package com.yzz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yzz.dto.Page;
import com.yzz.entity.SysUser;

/** 
* 
* @description: 实体类SysUser的DAO接口 
* 
* @author 杨志钊 
* @date 2017-04-13 19:03:53 
*/ 
@Repository
public interface SysUserDao {

	//单个实体全部字段添加
	int insert(SysUser entity);

	//根据实体ID单个实体删除
	int deleteByPrimaryKey(String sysUserId);

	//根据实体ID数组批量删除实体
	int deleteBatch(List<String> sysUserIds);

	//单个实体全部字段更新
	int updateByPrimaryKey(SysUser entity);

	//单个实体选择性字段更新
	int updateByPrimaryKeySelective(SysUser entity);

	//根据实体ID查询单个实体
	SysUser selectByPrimaryKey(String sysUserId);

	//根据选择性实体字段分页查询实体数组
	List<SysUser> selectByEntityAndPage(@Param("entity") SysUser entity, @Param("page") Page page);

	//根据选择性实体字段查询实体数量
	int countByEntity(@Param("entity") SysUser entity);

}
