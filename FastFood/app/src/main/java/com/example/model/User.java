package com.example.model;

public class User {
    //用户id
    private String user_id;
    //用户密码
    private String user_password;
    //用户姓名
    private String user_name;
    //用户住址
    private String user_address;
    //用户余额
    private  Double user_money;
    //用户电话
    private  Long user_tel;
    //账号状态
    private  int user_statue;
    //保存头像路径
    private  String user_picPath;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public Double getUser_money() {
        return user_money;
    }

    public void setUser_money(Double user_money) {
        this.user_money = user_money;
    }

    public Long getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(Long user_tel) {
        this.user_tel = user_tel;
    }

    public int getUser_statue() {
        return user_statue;
    }

    public void setUser_statue(int user_statue) {
        this.user_statue = user_statue;
    }

    public String getUser_picPath() {
        return user_picPath;
    }

    public void setUser_picPath(String user_picPath) {
        this.user_picPath = user_picPath;
    }



}
