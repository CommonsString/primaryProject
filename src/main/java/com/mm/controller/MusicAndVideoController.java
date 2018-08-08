package com.mm.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mm.entity.Bgm;
import com.mm.entity.Users;
import com.mm.service.MusicVideoService;
import com.mm.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 视频/背景音乐
 * @author commonsstring@gmail.com
 */
@Api(value = "背景音乐和视频", tags = {"背景音乐和视频"})
@RestController
@RequestMapping("/api/v1/mvsome")
public class MusicAndVideoController {
	
	@Autowired
	private MusicVideoService mvService;
	
	
	/**
     *  功能描述：返回背景音乐列表, 前端单选上传
     */
	@ApiOperation(value = "bgm列表", notes = "bgm选择上传")
	@PostMapping("/getBgm")
	public JSONResult getBgmList(){
		//获取全部List
		List<Bgm> result = this.mvService.findBgmForList();
		return JSONResult.ok(result);
	}
	
	
	@ApiOperation(value = "视频上传", notes = "视频上传接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户Id", required = true, 
				dataType = "String", paramType = "query"),  //query直接根参数自动完成映射赋值
		@ApiImplicitParam(name = "bgmId", value = "背景音乐ID", required = false, 
		dataType = "String", paramType = "query"), 
		@ApiImplicitParam(name = "videoSeconds", value = "视频秒数", required = true, 
		dataType = "Double", paramType = "query"),
		@ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, 
		dataType = "Double", paramType = "query"),
		@ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, 
		dataType = "Double", paramType = "query"),
		@ApiImplicitParam(name = "desc", value = "视频描述", required = false, 
		dataType = "String", paramType = "query") 
	})
	@PostMapping(value = "/uploadVideo", headers="content-type=multipart/form-data")
	public JSONResult upload(String userId, String bgmId, double videoSeconds, 
			int videoWidth, int videoHeight, String desc,
			@ApiParam(value="短视频", required=true)
			MultipartFile files) throws IOException{
		//参数检查
		if(StringUtils.isBlank(userId)){
			return JSONResult.errorMsg("上传出错,请重试！");
		}
		//定义工作空间
		String workSpace = "C:/mm_video_workspace";
		//用户上传会后, 保存在数据库的相对路径
		String uploadPathDB = "/" + userId + "/video";
		//流
		FileOutputStream out = null;
		InputStream in = null;
		try {
			if(files != null){
				//文件名称
				String fileName = files.getOriginalFilename();
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
					in = files.getInputStream();
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
		//保存视频相对路径
		Users user = new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		//更新
//		this.userService.updateUserInfo(user);
		return JSONResult.ok();
	}
	
	
}
