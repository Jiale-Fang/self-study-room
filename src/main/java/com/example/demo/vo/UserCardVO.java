package com.example.demo.vo;

import java.time.LocalDateTime;
import java.sql.Date;

public class UserCardVO {
    private Integer id;

    private String username;

    private String gender;

    private Integer cumulativeStudyTime;

    private Date createTime;

    private Integer likes;

    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getCumulativeStudyTime() {
        return cumulativeStudyTime;
    }

    public void setCumulativeStudyTime(Integer cumulativeStudyTime) {
        this.cumulativeStudyTime = cumulativeStudyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserCardVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", cumulativeStudyTime=" + cumulativeStudyTime +
                ", createTime=" + createTime +
                ", likes=" + likes +
                '}';
    }
}
