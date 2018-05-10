package com.ryanarjun.chatapp;


/*Constructor for the chatmessager*/
public class ChatMessage {

    String name;
    String message;
    String time;


    String from;

    public ChatMessage(){

    }

    public ChatMessage(String name, String message, String time) {
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public ChatMessage(String from){
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public String getName() {
        return name;
    }

    public String getMessage(){
        return message;
    }

    public String getTime() { return time; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}