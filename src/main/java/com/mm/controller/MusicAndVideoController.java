package com.mm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.entity.Bgm;
import com.mm.service.MusicVideoService;
import com.mm.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	
	
	
	
	
}
