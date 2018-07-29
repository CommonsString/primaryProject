package com.mm.entity;

import javax.persistence.*;

@Table(name = "users_like_videos")
public class UserLikeVideos {
    @Id
    private String id;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户喜欢视频ID
     */
    @Column(name = "video_id")
    private String videoId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户
     *
     * @return user_id - 用户
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户
     *
     * @param userId 用户
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户喜欢视频ID
     *
     * @return video_id - 用户喜欢视频ID
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * 设置用户喜欢视频ID
     *
     * @param videoId 用户喜欢视频ID
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}