package com.yzz.util.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Test {

	public static void main(String[] args) throws Exception {
		String dir = "E:\\eclipseworkspace\\ssm\\src\\main\\java\\com\\yzz\\temp\\DAOTEMPLATE.java";
		FileReader fis = new FileReader(new File(dir));

		BufferedReader br = new BufferedReader(fis);

		String result = "";

		String line;
		while ((line = br.readLine()) != null) {
			result += "\"" + line + "\\n" + "\"" + "+\n";
		}

		fis.close();
		br.close();

		System.out.println(result);

	}

}
