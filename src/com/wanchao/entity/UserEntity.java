package com.wanchao.entity;

public class UserEntity {
    private String userName;
    private Integer userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getUserId(){
        return userId;
    }
    public UserEntity(Integer userId,String userName){
        this.userId=userId;
        this.userName=userName;
    }
    public UserEntity(){
        System.out.println("无参构造函数");
    }
    public void addUser(String userName){
        System.out.println(">>>addUser()<<<userName:"+userName);
    }
}
