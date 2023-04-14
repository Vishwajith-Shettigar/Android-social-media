package com.example.lykasocialmediajava.Model;

public class userApiInfo {

    String userID,username,name,imageurl;

    public userApiInfo(String userID, String username, String name, String imageurl) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.imageurl = imageurl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
