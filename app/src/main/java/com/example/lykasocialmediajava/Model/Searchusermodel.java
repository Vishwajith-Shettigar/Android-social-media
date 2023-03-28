package com.example.lykasocialmediajava.Model;

public class Searchusermodel {
    String userID,username,name, userImage;

    public Searchusermodel(String userID, String username, String name,String userimage) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.userImage=userimage;

    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
