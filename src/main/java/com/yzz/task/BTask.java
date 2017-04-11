package com.yzz.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yzz.util.TimeUtil;

@Component
public class BTask implements IBTask {
	@Scheduled(cron = "0/5 * *  * * ? ") // 每5秒执行一次
	@Override
	public void bTask() {
		System.out.println(TimeUtil.getCurrentTimeString() + "*********B任务每5秒执行一次进入测试");
	}
}
