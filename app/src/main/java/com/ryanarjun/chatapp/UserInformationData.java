package com.ryanarjun.chatapp;

public class UserInformationData {

    public String username;
    public String location;

    public UserInformationData(){

    }


    public UserInformationData(String username, String location){
        this.username = username;
        this.location = location;
    }

    public void setUsername(String name){
         this.username = name;
    }

    public String getUsername(){
        return username;
    }

}