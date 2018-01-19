package com.huilian.hlej.common.core.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 获取资源文件键值对.
 * @author gjl
 */
public final class PropertyUtil {

	private static Properties properties = new Properties();

	/**
	 * @param key -
	 * @param classPathFileName  CLASSPATH 根路径下的资源文件.
	 * @return -
	 */
	public static String getProperty(String key, String classPathFileName) {
		//从配置中心获取
		//String value = ConfigUtils.getProperties(classPathFileName).getProperty(key);
		String value = "";
		if(StringUtils.isBlank(value)){
			if (!classPathFileName.startsWith("/")) {
				classPathFileName = "/" + classPathFileName;
			}
			if (properties.containsKey(key)) {
				return properties.getProperty(key);
			}
			try {
				System.out.println(PropertyUtil.class
						.getResourceAsStream(classPathFileName));
				properties.load(PropertyUtil.class
						.getResourceAsStream(classPathFileName));
				return properties.getProperty(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			return value;
		}
		return value;
	}
}