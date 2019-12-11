package com.zhangyanlong.common;

import org.apache.commons.codec.digest.DigestUtils;

public class CmsUtils {

	
	/**
	 * 加密计算
	 */
	public static String encry(String src,String salt) {
		
		return DigestUtils.md5Hex(salt+src+salt);
	}
}
