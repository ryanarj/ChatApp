package com.ryanarjun.chatapp;

/**
 * Created by ryanarjun on 2/19/18.
 */

public class Message {
    private String message;
    private String username;
    private String time;
    private String userID;

    public Message(){

    }
    public Message(String message, String username){
        this.message = message;
        this.username = username;
        this.time = time;
        this.userID = userID;
    }

    public String getMessage(){
        return message;
    }
    public String getName(){
        return username;
    }
    public String getTime(){
        return time;
    }
    public String getUserID() {return userID;}

    public void setMessage(String message){
        this.message = message;
    }
    public void setName(String name){
        this.username = name;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setUserID(String userID){
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "chat{message='" + message + "', name='" + username + "',  userID='" + userID + "'}";
    }
}
