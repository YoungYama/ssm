package com.yzz.temp;

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
* @date 2017-04-12 17:26:18 
*/ 
@Repository
public interface SysUserDao {

	int deleteByPrimaryKey(String SysUserId);

	int insert(SysUser entity);

	int insertSelective(SysUser entity);

	SysUser selectByPrimaryKey(String SysUserId);

	List<SysUser> selectByEntityAndPage(@Param("entity") SysUser entity, @Param("page") Page page);

	int updateByPrimaryKeySelective(SysUser entity);

	int updateByPrimaryKey(SysUser entity);

}
