package com.example.demo.entity;

import java.io.Serializable;

/**
 * Data package for server client interaction
 */
public class Message implements Serializable {

    /**
     * Message type
     */
    private int type;

    /**
     * The sender's user id
     */
    private Integer senderId;

    /**
     * Message content
     */
    private String content;

    private Object object;

    public Message(Integer type, Integer senderId, String content) {
        this.type = type;
        this.senderId = senderId;
        this.content = content;
    }

    public Message(Integer type, Object object) {
        this.type = type;
        this.object = object;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public int getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", object=" + object +
                '}';
    }
}
