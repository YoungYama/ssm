package com.yzz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yzz.dto.Page;
import com.yzz.entity.SysUser;

@Repository
public interface SysUserMapper {

	int deleteByPrimaryKey(String sysUserId);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(String sysUserId);

	List<SysUser> selectByEntityAndPage(@Param("entity") SysUser entity, @Param("page") Page page);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

}