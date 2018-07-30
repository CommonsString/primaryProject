package com.mm.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.mm.utils.RedisOperator;

public class BaseController {
	
	/**
	 * @redis 操作
	 */
	@Autowired
	public RedisOperator redis;
	/**
	 * @redis session-key
	 */
	public static final String USER_SESSION_KEY = "user-session-key";
	
	
}
