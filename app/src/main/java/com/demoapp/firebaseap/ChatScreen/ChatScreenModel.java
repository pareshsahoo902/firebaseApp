package com.demoapp.firebaseap.ChatScreen;

public class ChatScreenModel {

    private int Id;
    private String message , sender , time;

    public ChatScreenModel(int id, String message, String sender, String time) {
        Id = id;
        this.message = message;
        this.sender = sender;
        this.time = time;
    }

    public ChatScreenModel() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
