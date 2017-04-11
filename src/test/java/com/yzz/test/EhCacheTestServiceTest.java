package com.yzz.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yzz.encache.EhCacheTestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springmvc-servlet.xml", "classpath:applicationContext.xml" })
public class EhCacheTestServiceTest {

	@Resource
	EhCacheTestService ehCacheTestService;

	@Test
	public void test() throws InterruptedException {
		System.out.println("第一次调用：" + ehCacheTestService.getTimestamp("param"));
		Thread.sleep(2000);
		System.out.println("2秒之后调用：" + ehCacheTestService.getTimestamp("param"));
		Thread.sleep(11000);
		System.out.println("再过11秒之后调用：" + ehCacheTestService.getTimestamp("param"));
	}

}
