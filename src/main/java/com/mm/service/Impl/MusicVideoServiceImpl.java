package com.mm.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mm.entity.Bgm;
import com.mm.mapper.BgmMapper;
import com.mm.service.MusicVideoService;

@Service
public class MusicVideoServiceImpl implements MusicVideoService{

	@Autowired
	private BgmMapper bgmMapper;
	
	
	/**
	 * @返回背景音乐列表
	 */
	@Override
	public List<Bgm> findBgmForList() {
		//返回所有Bgm
		return this.bgmMapper.selectAll();
	}

	
}
