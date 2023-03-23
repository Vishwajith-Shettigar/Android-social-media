package com.example.lykasocialmediajava.Model;

public class LikesModel {

    String postID;
    String userID;

    public LikesModel(String postID, String userID) {
        this.postID = postID;
        this.userID = userID;
    }


    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostID() {
        return postID;
    }

    public String getUserID() {
        return userID;
    }
}
