package com.example.demo.pojo;

import java.io.Serializable;

public class Seat implements Serializable {
    private boolean isSitDown;
    private int seatId;
    private int userId;

    private String gender;

    public Seat() {
    }

    public boolean isSitDown() {
        return isSitDown;
    }

    public void setSitDown(boolean sitDown) {
        isSitDown = sitDown;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "isSitDown=" + isSitDown +
                ", seatId=" + seatId +
                ", userId=" + userId +
                ", gender='" + gender + '\'' +
                '}';
    }
}
