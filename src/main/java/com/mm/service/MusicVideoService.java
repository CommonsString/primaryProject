package com.mm.service;

import java.util.List;

import com.mm.entity.Bgm;

public interface MusicVideoService {

	/**
	 * @返回背景音乐列表
	 */
	List<Bgm> findBgmForList();
	
	

}
