package com.example.demo.pojo;

import java.time.LocalDateTime;

public class Favorites {
    private int id;
    private int mid;
    private int uid;
    private LocalDateTime createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "id=" + id +
                ", mid=" + mid +
                ", uid=" + uid +
                ", createTime=" + createTime +
                '}';
    }
}
