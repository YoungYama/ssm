package com.yzz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yzz.dao.SysUserDao;
import com.yzz.dto.Page;
import com.yzz.dto.ResultData;
import com.yzz.entity.SysUser;
import com.yzz.service.SysUserService;
import com.yzz.util.DBOperatedState;

/** 
* 
* @description: SysUserService接口的实现类SysUserServiceImpl 
* 
* @author 杨志钊 
* @date 2017-04-13 19:03:53 
*/ 
@Service
public class SysUserServiceImpl implements SysUserService {

	@Resource
	SysUserDao sysUserDao;

	//单个实体全部字段添加
	@Override
	public ResultData<Void> insertOne(SysUser entity) {
		ResultData<Void> resultData = new ResultData<>();
		try {
			int rows = sysUserDao.insert(entity);
			if (rows < 0) {
				resultData.setCode(400);
				resultData.setMsg(DBOperatedState.INSERT_FAILURE);
			} else {
				resultData.setMsg(DBOperatedState.INSERT_SUCCESS);
			}
		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}
		return resultData;
	}

	//根据实体ID单个实体删除
	@Override
	public ResultData<Void> deleteOne(String sysUserId) {
		ResultData<Void> resultData = new ResultData<>();
		try {
			int rows = sysUserDao.deleteByPrimaryKey(sysUserId);
			if (rows < 0) {
				resultData.setCode(400);
				resultData.setMsg(DBOperatedState.DELETE_FAILURE);
			} else {
				resultData.setMsg(DBOperatedState.DELETE_SUCCESS);
			}
		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}
		return resultData;
	}

	//根据实体ID数组批量删除实体
	@Override
	public ResultData<Void> deleteBatch(String[] sysUserIds) {
		ResultData<Void> resultData = new ResultData<>();
		try {
			int rows = sysUserDao.deleteBatch(Arrays.asList(sysUserIds));
			if (rows < 0) {
				resultData.setCode(400);
				resultData.setMsg(DBOperatedState.DELETE_FAILURE);
			} else {
				resultData.setMsg(DBOperatedState.DELETE_SUCCESS);
			}
		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}
		return resultData;
	}

	//单个实体全部字段更新
	@Override
	public ResultData<Void> updateOne(SysUser entity) {
		ResultData<Void> resultData = new ResultData<>();
		try {
			int rows = sysUserDao.updateByPrimaryKey(entity);
			if (rows < 0) {
				resultData.setCode(400);
				resultData.setMsg(DBOperatedState.UPDATE_FAILURE);
			} else {
				resultData.setMsg(DBOperatedState.UPDATE_SUCCESS);
			}
		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}
		return resultData;
	}

	//单个实体选择性字段更新
	@Override
	public ResultData<Void> updateOneSelective(SysUser entity) {
		ResultData<Void> resultData = new ResultData<>();
		try {
			int rows = sysUserDao.updateByPrimaryKeySelective(entity);
			if (rows < 0) {
				resultData.setCode(400);
				resultData.setMsg(DBOperatedState.UPDATE_FAILURE);
			} else {
				resultData.setMsg(DBOperatedState.UPDATE_SUCCESS);
			}
		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}
		return resultData;
	}

	//根据实体ID查询单个实体
	@Override
	public ResultData<SysUser> selectOne(String sysUserId) {
		ResultData<SysUser> resultData = new ResultData<>();
		try {
			SysUser sysUser = sysUserDao.selectByPrimaryKey(sysUserId);
			if (sysUser == null) {
				resultData.setMsg(DBOperatedState.NO_DATA);
			} else {
				resultData.setMsg(DBOperatedState.SELECT_SUCCESS);
				resultData.setData(sysUser);
			}
		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}

		return resultData;
	}

	//根据选择性实体字段分页查询实体数组
	@Override
	public ResultData<List<SysUser>> selectList(SysUser entity, Page page) {
		ResultData<List<SysUser>> resultData = new ResultData<>();
		try {
			List<SysUser> sysUsers = new ArrayList<>();
			int count = sysUserDao.countByEntity(entity);
			if (count > 0) {// 总记录大于则有数据，可以进一步分页查询
				page.setTotalRecord(count);
				sysUsers = sysUserDao.selectByEntityAndPage(entity, page);

				if (sysUsers.size() > 0) {
					resultData.setMsg(DBOperatedState.SELECT_SUCCESS);
				} else {
					resultData.setMsg(DBOperatedState.NO_DATA);
				}
				
				resultData.setData(sysUsers, page);
			} else {
				resultData.setMsg(DBOperatedState.NO_DATA);
			}

		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}

		return resultData;
	}

	//查询全部实体
	@Override
	public ResultData<List<SysUser>> selectAll() {
		ResultData<List<SysUser>> resultData = new ResultData<>();
		try {
			List<SysUser> sysUsers = sysUserDao.selectByEntityAndPage(null, null);

			if (sysUsers.size() > 0) {
				resultData.setMsg(DBOperatedState.SELECT_SUCCESS);
				resultData.setData(sysUsers);
			} else {
				resultData.setMsg(DBOperatedState.NO_DATA);
			}

		} catch (RuntimeException e) {
			resultData.setCode(400);
			resultData.setMsg(DBOperatedState.INNER_ERROR);
		}

		return resultData;
	}

}
