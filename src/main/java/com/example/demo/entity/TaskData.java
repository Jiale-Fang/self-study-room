package com.example.demo.entity;

public class TaskData {

    public int taskId;
    public String goal;
    public int focusTime;
    public int restTime;
    public String discussion;
    public String light;
    public String immersion;
    public static int second = 60;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(int focusTime) {
        this.focusTime = focusTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getImmersion() {
        return immersion;
    }

    public void setImmersion(String immersion) {
        this.immersion = immersion;
    }

    public static int getSecond() {
        return second;
    }

    public static void setSecond(int second) {
        TaskData.second = second;
    }

}
