package com.yzz.redis;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalTest {
	
	@Resource
	RedisTemplate<String, Object> redisTemplate;

	/**
	 * 测试事务
	 */
	@Transactional
	public void testTransactional(){
		ValueOperations<String, Object> value = redisTemplate.opsForValue();
		value.set("lp", "hello0000");
		// 异常代码
		int reslt = 0 / 0;
	}
	
}
