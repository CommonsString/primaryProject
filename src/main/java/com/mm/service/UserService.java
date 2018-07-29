package com.mm.service;

import com.mm.entity.Users;

public interface UserService {

	
	/**
	 * @注册--用户名是否存在
	 */
	public boolean findUserIsExist(String username);

	
	/**
	 * @注册--保存用户
	 */
	void saveUser(Users user);


	/**
	 * @登录--用户名是否存在
	 */
	public Users findLoginUser(String username, String password);


	/**
	 * @上传头像--用户上传头像
	 */
	public void updateUserInfo(Users user);
	
}
