package com.yzz.test;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yzz.dao.SysUserDao;
import com.yzz.dto.Page;
import com.yzz.dto.ResultData;
import com.yzz.entity.SysUser;
import com.yzz.util.EncryptionUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springmvc-servlet.xml", "classpath:applicationContext.xml" })
public class SysUserDaoTest {

	private static Logger logger = Logger.getLogger(SysUserDaoTest.class);

	@Resource
	SysUserDao sysUserDao;

	@Test
	public void test() throws Exception {
		SysUser entity = new SysUser();
		entity.setSysUserName("杨志钊");
//		entity.setSysUserId(IdGenerator.generatesId());
		entity.setPassword(EncryptionUtil.encodePassword("123456"));
		Page page = new Page();
		page.setOrderField("sys_user_id");
		page.setTotalRecord(sysUserDao.countByEntity(entity));
		List<SysUser> sysUsers = sysUserDao.selectByEntityAndPage(entity, page);
		ResultData<Object> resultData = new ResultData<Object>();
		resultData.setData(sysUsers, page);
		logger.info("-----------------------" + JSON.toJSONString(resultData));
//		logger.info(sysUserDao.insert(entity));
		entity.setSysUserId("19801253-8a98-4c53-9b51-48c5b5b0ae0b-1492054844377");
		entity.setSysUserName("a杨志钊");
//		logger.info(sysUserDao.updateByPrimaryKeySelective(entity));
//		logger.info(sysUserDao.updateByPrimaryKey(entity));
//		logger.info(sysUserDao.deleteByPrimaryKey("5444828e-e90d-40f0-bcea-e4215b677527-1492061731577"));
//		String[] sysUserIds = {"24dad810-cdbc-4e12-bdd7-b629cac7c2e1-1492054887615","2158f163-d385-4d9b-863b-3ec72bafc881-1492055746418"};
//		logger.info(sysUserDao.deleteBatch(Arrays.asList(sysUserIds)));
//		String[] ids = {"d18f347f-c1cf-4812-87b1-35e154071e0f-1491206068270","d6c18384-39ab-49fb-ac55-63a44f6f7ff0-1491207012479"};
//		sysUserDao.deleteBatch(ids);
//		entity.setSysUserName("o成o");
//		entity.setSysUserId("fe4c0063-4854-4e1c-bb86-85fd3aa875b0-1491206877532");
//		sysUserDao.updateByPrimaryKey(entity);
//		sysUserDao.deleteByPrimaryKey("fe4c0063-4854-4e1c-bb86-85fd3aa875b0-1491206877532");
//		logger.info("--------------完成");
//		logger.info("-----------------------" + JSON
//				.toJSONString(sysUserDao.selectByPrimaryKey("6784a1e7-425a-47e9-8b70-4363416bb00f-1483702770271")));
//		logger.info(sysUserDao.selectByEntityAndPage(entity,null).size() + "-----------------------"
//				+ JSON.toJSONString(sysUserDao.selectByEntityAndPage(entity,null)));
	}

}
