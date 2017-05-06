package com.yzz.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class RedisTest {

	private static final String host = "localhost";
	private static final String port = "6379";
	private static final String password = "123456";

	public static void main(String[] args) {
		JedisShardInfo shardInfo = new JedisShardInfo(host, port);
		shardInfo.setPassword(password);
		// 1、连接本地的 Redis 服务
		Jedis jedis = getJedis(shardInfo);
		// 2、查看服务是否运行
		System.out.println("Server is running: " + jedis.ping());
		// 3、设置 redis 字符串数据
		// jedis.set("key", "yzz");
		// 4、获取存储的数据并输出
		System.out.println(jedis.get("key"));
		// 5、存储数据到列表中
		// jedis.lpush("list", "Redis");
		// jedis.lpush("list", "Mongodb");
		// jedis.lpush("list", "Mysql");
		List<String> list = jedis.lrange("list", 0, 10);
		for (String str : list) {
			System.out.println(str);
		}
	}

	public static Jedis getJedis(JedisShardInfo shardInfo) {
		Jedis jedis = new Jedis(shardInfo);

		return jedis;
	}

}
