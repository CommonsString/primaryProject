package com.mm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class MD5Utils {

	/**
	 * @Description: 对字符串进行md5加密
	 */
	public static String getMD5Str(String strValue){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		return newstr;
	}

	public static void main(String[] args) {
		try {
			String md5 = getMD5Str("imooc");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
