package com.example.demo.pojo;

import java.time.LocalDateTime;

public class Task {
    private int tid;
    private int uid;
    private int roomId;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;

    private boolean isFinished;

    private String goal;

    public Task() {
    }

    public Task(int tid, int uid, int roomId, LocalDateTime createTime, LocalDateTime expireTime, boolean isFinished) {
        this.tid = tid;
        this.uid = uid;
        this.roomId = roomId;
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.isFinished = isFinished;
    }
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }


    @Override
    public String toString() {
        return "Task{" +
                "tid=" + tid +
                ", uid=" + uid +
                ", roomId=" + roomId +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", isFinished=" + isFinished +
                ", goal='" + goal + '\'' +
                '}';
    }
}
