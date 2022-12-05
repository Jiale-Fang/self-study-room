package com.example.demo.util;

public class Time {
    public int second;

    public Time(int second){
        this.second = second;
    }

    public void oneSecondPassed() {
        second--;
    }
}
