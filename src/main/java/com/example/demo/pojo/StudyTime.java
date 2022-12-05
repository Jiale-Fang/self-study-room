package com.example.demo.pojo;

public class StudyTime {
    String goal;
    int targetTime;
    int realTime;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(int targetTime) {
        this.targetTime = targetTime;
    }

    public int getRealTime() {
        return realTime;
    }

    public void setRealTime(int realTime) {
        this.realTime = realTime;
    }

    @Override
    public String toString() {
        return "StudyTime{" +
                "goal='" + goal + '\'' +
                ", targetTime=" + targetTime +
                ", realTime=" + realTime +
                '}';
    }

}
