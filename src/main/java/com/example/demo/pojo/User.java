package com.example.demo.pojo;

import java.util.Date;

public class User {
    private int id;
    private String account;
    private String password;
    private int age;
    private String gender;
    private String phone;
    private Date birthday;
    private String avatar;
    private String username;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User() {
    }

    public User(int id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public User(String account, String password, String username) {
        this.account=account;
        this.password=password;
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public User(int id, String account, String password, int age, String gender, String phone, String username) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
    }

    public User(String account, String password, int age, String gender, String phone, String username) {
        this.account = account;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
