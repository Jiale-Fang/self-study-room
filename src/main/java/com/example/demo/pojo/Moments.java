package com.example.demo.pojo;

import java.time.LocalDateTime;

public class Moments {
    private int id;
    private int uid;
    private String title;
    private int likes;
    private LocalDateTime createTime;
    private String cover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Moments{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", likes=" + likes +
                ", createTime=" + createTime +
                ", cover='" + cover + '\'' +
                '}';
    }
}
