package com.example.demo.pojo;
import java.time.LocalDateTime;

public class Comment {
    private int id;
    private int uid;
    private int momentId;
    private String content;
    private LocalDateTime createTime;

    public Comment() {
    }


    public Comment(int uid, int momentId, String content) {
        this.uid = uid;
        this.momentId = momentId;
        this.content = content;
    }

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

    public int getMomentId() {
        return momentId;
    }

    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
