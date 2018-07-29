package com.mm.service.Impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mm.entity.Users;
import com.mm.mapper.UsersMapper;
import com.mm.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService{


	@Autowired
	private UsersMapper userMapper;
	
	@Autowired
	private Sid sid;
	
	/**
	 * @用户名是否存在
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean findUserIsExist(String username) {
		Users user = new Users();
		user.setUsername(username);
		Users result = this.userMapper.selectOne(user);
		return result == null ? false : true;
	}	
	

	/**
	 * @保存用户
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		//用户Id插件 idwork, 生成全局唯一Id
		String id = sid.nextShort();
//		String id = "你好";
		user.setId(id);
		this.userMapper.insert(user);
	}


	/**
	 * @登录--用户名是否存在
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users findLoginUser(String username, String password) {
		Example userLoginExample = new Example(Users.class);
		Criteria criteria = userLoginExample.createCriteria();
		criteria.andEqualTo("username", username);
		criteria.andEqualTo("password", password);
		//查找
		Users result = this.userMapper.selectOneByExample(userLoginExample);
		return result;
	}

	/**
	 * @上传头像--用户上传头像
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateUserInfo(Users user) {
		Example userLoginExample = new Example(Users.class);
		Criteria criteria = userLoginExample.createCriteria();
		criteria.andEqualTo("id", user.getId());
		//更新
		this.userMapper.updateByExampleSelective(user, userLoginExample);
	}
	
}
