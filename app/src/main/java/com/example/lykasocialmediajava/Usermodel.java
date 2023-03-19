package com.example.lykasocialmediajava;

public class Usermodel {
    public static  String userID;
  public static  String username;
    public static  String name;
    public static  String imageurl=null;
    public static  String desc=null;

    public static void setUserID(String userID) {
        Usermodel.userID = userID;
    }

    public static void setUsername(String username) {
        Usermodel.username = username;
    }

    public static void setName(String name) {
        Usermodel.name = name;
    }

    public static void setImageurl(String imageurl) {
        Usermodel.imageurl = imageurl;
    }

    public static void setDesc(String desc) {
        Usermodel.desc = desc;
    }

    public static String getUserID() {
        return userID;
    }

    public static String getUsername() {
        return username;
    }

    public static String getName() {
        return name;
    }

    public static String getImageurl() {
        return imageurl;
    }

    public static String getDesc() {
        return desc;
    }
}
