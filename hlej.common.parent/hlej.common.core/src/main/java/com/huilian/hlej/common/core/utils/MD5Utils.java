package com.huilian.hlej.common.core.utils;

import java.security.MessageDigest;

/**
 *  类            名:   MD5
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年6月8日
 *  @author   qinlinhai
 *
 */
public class MD5Utils {

	/**
	 * MD5加密方法
	 * @param str
	 * @return MD5Str
	 */
	public static String md5(String str) {
		return getMD5(str, null, 1);
	}
	/**
	 * MD5加密方法
	 * 
	 * @param str
	 * @param encoding
	 *            default UTF-8
	 * @param no_Lower_Upper
	 *            0,1,2 0：不区分大小写，1：小写，2：大写
	 * @return MD5Str
	 */
	public static String getMD5(String str, String encoding, int no_Lower_Upper) {
		if (null == encoding)
			encoding = "utf-8";
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes(encoding));
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (no_Lower_Upper == 0) {
			return sb.toString();
		}
		if (no_Lower_Upper == 1) {
			return sb.toString().toLowerCase();
		}
		if (no_Lower_Upper == 2) {
			return sb.toString().toUpperCase();
		}
		return null;
	}
}