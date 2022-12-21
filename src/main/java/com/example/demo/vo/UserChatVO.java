package com.example.demo.vo;

import java.io.Serializable;

public class UserChatVO implements Serializable {
    private int sender;
    private String username;
    private String avatar;
    private int receiver;
    private String content;

    public UserChatVO() {
    }

    public UserChatVO(int sender, String username, String avatar, String content) {
        this.username = username;
        this.avatar = avatar;
        this.sender = sender;
        this.content = content;
    }

    public UserChatVO(int sender, int receiver, String username, String avatar, String content) {
        this.username = username;
        this.avatar = avatar;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
