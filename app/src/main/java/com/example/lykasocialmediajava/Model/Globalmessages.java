package com.example.lykasocialmediajava.Model;

public class Globalmessages {
    String message;
    String senderUID;
    long timestamp;
    String currenttime;
    String dockey;

    public Globalmessages(String message, String senderUID, long timestamp, String currenttime, String dockey) {
        this.message = message;
        this.senderUID = senderUID;
        this.timestamp = timestamp;
        this.currenttime = currenttime;
        this.dockey = dockey;
    }

    public Globalmessages() {
    }

    public void setDockey(String dockey) {
        this.dockey = dockey;
    }

    public String getDockey() {
        return dockey;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
}
