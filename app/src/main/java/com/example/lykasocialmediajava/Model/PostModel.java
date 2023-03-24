package com.example.lykasocialmediajava.Model;

public class PostModel {


    String uid;
    String username;
    String userimage;
    String pid;
    String pimage;
    String ptext;
    String ttime;

    boolean hideLike;
    boolean hideComt;

    public PostModel(String uid, String username, String userimage, String pid, String pimage, String ptext, String ttime,boolean hideLike,boolean hideComt) {
        this.uid = uid;
        this.username = username;
        this.userimage = userimage;
        this.pid = pid;
        this.pimage = pimage;
        this.ptext = ptext;
        this.ttime = ttime;
        this.hideLike=hideLike;
        this.hideComt=hideComt;

    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getUserimage() {
        return userimage;
    }

    public String getPid() {
        return pid;
    }

    public String getPimage() {
        return pimage;
    }

    public String getPtext() {
        return ptext;
    }

    public String getTtime() {
        return ttime;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public void setPtext(String ptext) {
        this.ptext = ptext;
    }

    public void setTtime(String ttime) {
        this.ttime = ttime;
    }


    public void setHideComt(boolean hideComt) {
        this.hideComt = hideComt;
    }

    public void setHideLike(boolean hideLike) {
        this.hideLike = hideLike;
    }

    public  boolean gethideLike()
    {
        return hideLike;
    }

    public  boolean gethideComt()
    {
        return  hideComt;
    }
}
