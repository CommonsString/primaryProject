package com.mm.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mm.entity.Users;
import com.mm.service.UserService;
import com.mm.utils.JSONResult;
import com.mm.utils.rebuild.UserRebuild;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * 登录/注册
 * @author commonsstring@gmail.com
 */
@Api(value = "用户相关业务", tags = {"用户相关业务,头像上传,预加载信息"})
@RestController
@RequestMapping("/api/v1/userother")
public class UserOtherController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	/**
	 * @throws IOException 
	 * @throws IOException 
     *  功能描述：用户头像上传
	 * @throws  
     */
	@ApiOperation(value = "用户头像上传", notes = "用户头像上传接口")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, 
	dataType = "String", paramType = "query") //query直接根参数自动完成映射赋值
	@PostMapping("/uploadFace")
	public JSONResult logout(String userId, @RequestParam("file") MultipartFile[] files) throws IOException{
		if(StringUtils.isBlank(userId)){
			return JSONResult.errorMsg("上传出错,请重试！");
		}
		//定义工作空间
		String workSpace = "C:/mm_video_workspace";
		//用户上传会后, 保存在数据库的相对路径
		String uploadPathDB = "/" + userId + "/face";
		//流
		FileOutputStream out = null;
		InputStream in = null;
		try {
			if(files != null && files.length > 0){
				//文件名称
				String fileName = files[0].getOriginalFilename();
				//文件名不为空
				if(StringUtils.isNotBlank(fileName)){
					//创建存储真实目录
					String fileRealPath = workSpace + uploadPathDB + "/" + fileName;
					//数据库保存路径, 相对路径
					uploadPathDB += ("/" + fileName);
					//创建目录
					File outFile = new File(fileRealPath);
					//创建目录
					if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()){
						outFile.getParentFile().mkdirs();
					}
					//头像写入
					out = new FileOutputStream(outFile);
					in = files[0].getInputStream();
					//写出
					IOUtils.copy(in, out);
				}
			}else{
				return JSONResult.errorMsg("选择文件错误,请重试！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.errorMsg("上传出错,请重试！");
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}else if(in != null){
				in.close();
			}
		}
		//保存图片相对路径
		Users user = new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		//更新
		this.userService.updateUserInfo(user);
		return JSONResult.ok(uploadPathDB);
	}
	
	
	/**
     *  功能描述：用户头加载信息
     */
	@ApiOperation(value = "用户加载信息", notes = "用户加载信息接口")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, 
	dataType = "String", paramType = "query") //query直接根参数自动完成映射赋值
	@PostMapping("/onLoadInfo")
	public JSONResult onLoadInfo(String userId){	
		//参数检查
		if(StringUtils.isBlank(userId)){
			return JSONResult.errorMsg("上传出错,请重试！");
		}
		Users user = this.userService.findUserInfo(userId);
		UserRebuild rebuild = new UserRebuild();
		//复制属性的值
		BeanUtils.copyProperties(user, rebuild);
		return JSONResult.ok(rebuild);
	}
}
