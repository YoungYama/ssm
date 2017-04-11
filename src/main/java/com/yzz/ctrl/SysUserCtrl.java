package com.yzz.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzz.dto.ResultData;
import com.yzz.entity.SysUser;
import com.yzz.log.LogInfo;
import com.yzz.mapper.SysUserMapper;
import com.yzz.util.ConstantUtil;
import com.yzz.util.EncryptionUtil;
import com.yzz.util.IdGenerator;

@Controller
@RequestMapping("/sysuser")
public class SysUserCtrl {

	@Resource
	SysUserMapper sysUserMapper;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	@LogInfo(modelTypeName = "系统用户", operationTypeName = "登录", operationContent = "用户登录了")
	public ResultData<SysUser> login(HttpSession session) throws Exception {
		System.out.println("in");
		SysUser entity = new SysUser();
		entity.setSysUserName("杨志钊");
		entity.setSysUserId(IdGenerator.generatesId());
		entity.setPassword(EncryptionUtil.encodePassword("123456"));
		// sysUserDao.insert(entity);

		ResultData<SysUser> resultData = new ResultData<>();

		entity = sysUserMapper.selectByPrimaryKey("6784a1e7-425a-47e9-8b70-4363416bb00f-1483702770271");
		session.setAttribute(ConstantUtil.LOGINING_SYSUSER, entity);
		resultData.setData(entity);
		
		return resultData;
	}

}
