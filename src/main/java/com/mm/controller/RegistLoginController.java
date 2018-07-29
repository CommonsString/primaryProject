package com.mm.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.entity.Users;
import com.mm.service.UserService;
import com.mm.utils.JSONResult;
import com.mm.utils.MD5Utils;
import com.mm.utils.rebuild.UserRebuild;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * 登录/注册
 * @author commonsstring@gmail.com
 */
@Api(value = "用户信息", tags = {"注册和登录管理"})
@RestController
@RequestMapping("/api/v1/users")
public class RegistLoginController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	
	/**
     *  功能描述：用户注册
     */
	@ApiOperation(value = "用户注册", notes = "用户注册接口")
	@PostMapping("/regist")
	public JSONResult regist(@RequestBody Users user){
		//判断用户名和密码不为空
		if(StringUtils.isBlank(user.getUsername()) 
				|| StringUtils.isBlank(user.getPassword())){
			return JSONResult.errorMsg("用户名和密码不能为空, 请重试！");
		}
		//判断用户名是否存在
		boolean isExist = this.userService.findUserIsExist(user.getUsername());
		if(!isExist){
			//注册逻辑
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			//设置粉丝数和其他信息
			user.setFollowCounts(0);
			user.setFansCounts(0);
			user.setReceiveLikeCounts(0);
			//保存用户
			this.userService.saveUser(user);
		}else{
			return JSONResult.errorMsg("账号已经被注册过, 请换一个试试！");
		}
		UserRebuild rebuild = setRedisSessionToken(user);
		//密码置空
		rebuild.setPassword("");
		return JSONResult.ok(rebuild);
	}
	
	/**
	 * 功能描述：设置sessionToken
	 */
	public UserRebuild setRedisSessionToken(Users user){
		//注册-生成唯一ID
		String uniqueToken = UUID.randomUUID().toString();
		//过期时间30分钟
		redis.set(USER_SESSION_KEY + ":" + user.getId(), uniqueToken, 1000 * 60 * 30);
		user.setPassword("");
		UserRebuild rebuild = new UserRebuild();
		//复制属性值
		BeanUtils.copyProperties(user, rebuild);
		//设置token
		rebuild.setUserToken(uniqueToken);
		return rebuild;
	}
	
	/**
     *  功能描述：用户登录
	 * @throws InterruptedException 
     */
	@ApiOperation(value = "用户登录", notes = "用户登录接口")
	@PostMapping("/login")
	public JSONResult login(@RequestBody Users user) throws InterruptedException{
		//判断用户名和密码非空
		if(StringUtils.isBlank(user.getUsername())
				|| StringUtils.isBlank(user.getPassword())){
			return JSONResult.errorMsg("用户名或密码不能为空, 请重试!");
		}
		//匹配用户名和密码
		Users loginUser = this.userService.findLoginUser(user.getUsername(), 
				MD5Utils.getMD5Str(user.getPassword()));
		//用户未匹配
		if(loginUser == null){
			return JSONResult.errorMsg("用户名或密码错误, 请重试！");
		}
		loginUser.setPassword("");
		//返回rebuild
		UserRebuild rebuild = setRedisSessionToken(loginUser);
		return JSONResult.ok(rebuild);
	}
	
	
	
	/**
     *  功能描述：用户注销
	 * @throws InterruptedException 
     */
	@ApiOperation(value = "用户注销", notes = "用户注销接口")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, 
	dataType = "String", paramType = "query") //query直接根参数自动完成映射赋值
	@PostMapping("/logout")
	public JSONResult logout(String userId) throws InterruptedException{
		//删除ID
		redis.del(USER_SESSION_KEY + ":" + userId);
		return JSONResult.ok();
	}
	
	
}
