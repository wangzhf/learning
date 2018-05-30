package com.wangzhf.redis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	/**
	 * 加载properties文件
	 * @param propertyFile
	 * @return
	 */
	public static Properties loadProperties(String propertyFile) {
		Properties prop = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertyFile);
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
