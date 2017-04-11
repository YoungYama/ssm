package com.yzz.mapper;

import org.springframework.stereotype.Repository;

import com.yzz.entity.SysUser;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(String sysUserId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String sysUserId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}