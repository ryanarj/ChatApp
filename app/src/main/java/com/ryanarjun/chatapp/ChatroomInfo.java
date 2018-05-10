package com.ryanarjun.chatapp;

/**
 * Created by ryanarjun on 3/22/18.
 */

public class ChatroomInfo {


    String chatroom;

    public ChatroomInfo(){

    }

    public ChatroomInfo(String chatroom) {
        this.chatroom = chatroom;
    }

    public String getName() {
        return chatroom;
    }


    public void setName(String chatroom) {
        this.chatroom = chatroom;
    }
}
