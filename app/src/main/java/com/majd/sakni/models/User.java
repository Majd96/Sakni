package com.majd.sakni.models;

public class User {

    private String userId;
    private String name;
    private String phoneNo;
    private String picURL;

    public User(){

    }


    public User(String name,String phoneNo,String picURL){

        this.name=name;
        this.phoneNo=phoneNo;
        this.picURL=picURL;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPicURL() {
        return picURL;
    }
}
