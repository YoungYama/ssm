package com.yzz.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 
 * @description:根据数据库表自动生成相对应的entity、mapper、sql
 *
 * @author 杨志钊
 * @date 2017年1月7日 下午12:41:07
 */
public class MybatisGeneratorUtil {

	public static void main(String[] args) throws Exception {
		String dir = System.getProperty("user.dir") + "/src/main/resources/generator/";

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(dir + "generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("完成");
	}

}
