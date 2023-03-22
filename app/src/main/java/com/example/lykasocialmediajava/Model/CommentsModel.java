package com.example.lykasocialmediajava.Model;

public class CommentsModel {

    private  String comID;
    private String userID;
    private  String comText;

    private  String postID;

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    public CommentsModel(String comID, String userID, String comText, String postID) {
        this.comID = comID;
        this.userID = userID;
        this.comText = comText;
        this.postID = postID;
    }

    public String getComID() {
        return comID;
    }

    public String getUserID() {
        return userID;
    }

    public String getComText() {
        return comText;
    }

    public void setComID(String comID) {
        this.comID = comID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setComText(String comText) {
        this.comText = comText;
    }
}
