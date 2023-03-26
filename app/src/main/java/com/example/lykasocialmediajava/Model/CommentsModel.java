package com.example.lykasocialmediajava.Model;

public class CommentsModel {

    private  String comID;
    private String userID;
    private  String comText;

    private  String postID;

    private  String postUserID;


    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostUserID(String postUserID) {
        this.postUserID = postUserID;
    }

    public String getPostUserID() {
        return postUserID;
    }

    public CommentsModel(String comID, String userID, String comText, String postID, String postUserID) {
        this.comID = comID;
        this.userID = userID;
        this.comText = comText;
        this.postID = postID;
        this.postUserID=postUserID;

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
