package com.yzz.task;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yzz.util.TimeUtil;

@Component
public class ATask implements IATask {
	@Scheduled(cron = "0/10 * *  * * ? ") // 每10秒执行一次
	@Override
	public void aTask() {
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(TimeUtil.getCurrentTimeString() + "*********A任务每10秒执行一次进入测试，但延迟了20 秒sleep(20)");
	}
}
