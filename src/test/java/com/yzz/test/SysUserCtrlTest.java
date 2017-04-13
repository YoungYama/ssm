package com.yzz.test;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yzz.ctrl.SysUserCtrl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:springmvc-servlet.xml", "classpath:applicationContext.xml" })
public class SysUserCtrlTest {

	private static Logger logger = Logger.getLogger(SysUserCtrlTest.class);

	@Autowired
	private SysUserCtrl sysUserCtrl;

//	@Autowired
//	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@Before
	public void init() {
		// 可以对所有的controller来进行测试
		// mockMvc =
		// MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		// 仅仅对单个Controller来进行测试
		mockMvc = MockMvcBuilders.standaloneSetup(sysUserCtrl).build();
	}
	
	@Test
	public void test() throws Exception {
		// 发送请求
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/sysUser/all")
//				.characterEncoding("UTF-8").param("sysUserName", "lllllp00"));
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/sysUser/list")
				.characterEncoding("UTF-8").param("sysUserName", "杨志钊").param("currentPage", "1"));
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/sysUser/select")
//				.characterEncoding("UTF-8").param("sysUserId", "19801253-8a98-4c53-9b51-48c5b5b0ae0b-1492054844377"));
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/sysUser/insert")
//				.characterEncoding("UTF-8").param("sysUserId", "12801253-8a98-4c53-9b51-48c5b5b0ae0b-1492054844377"));
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/sysUser/updateSelective")
//				.characterEncoding("UTF-8").param("sysUserId", "18801253-8a98-4c53-9b51-48c5b5b0ae0b-1492054844377").param("sysUserName", "12杨志钊"));
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/sysUser/delete")
//				.characterEncoding("UTF-8").param("sysUserId", "18801253-8a98-4c53-9b51-48c5b5b0ae0b-1492054844377").param("sysUserName", "12杨志钊"));
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/sysUser/deleteBatch")
//				.characterEncoding("UTF-8").param("sysUserIds", "12801253-8a98-4c53-9b51-48c5b5b0ae0b-1492054844377", "18801253-8a98-4c53-9b51-48c5b5b0ae0b-1492054844377").param("sysUserName", "12杨志钊"));

		MvcResult mvcResult = resultActions.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		logger.info("客户端获得的反馈数据:" + result);
	}

	/*@Test
	public void test() throws Exception {
		// 发送请求
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/sysUser/test2/{id}", 99)
				.characterEncoding("UTF-8").param("sysUserName", "lllllp00").param("start", "9987"));

		MvcResult mvcResult = resultActions.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		logger.info("客户端获得的反馈数据:" + result);
	}*/
}
