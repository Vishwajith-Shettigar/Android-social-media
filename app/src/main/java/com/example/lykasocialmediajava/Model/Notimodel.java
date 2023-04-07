package com.example.lykasocialmediajava.Model;

public class Notimodel {
    String fromuid,touid,fromusername,fromimage,text,notiID;
    boolean seen;

    public Notimodel(String notiID,String fromuid, String touid, String fromusername, String fromimage, String text) {
        this.fromuid = fromuid;
        this.touid = touid;
        this.fromusername = fromusername;
        this.fromimage = fromimage;
        this.text = text;
        this.seen=false;
        this.notiID=notiID;


    }

    public String getNotiID() {
        return notiID;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getFromuid() {
        return fromuid;
    }

    public String getTouid() {
        return touid;
    }

    public String getFromusername() {
        return fromusername;
    }

    public String getFromimage() {
        return fromimage;
    }

    public String getText() {
        return text;
    }

    public void setFromuid(String fromuid) {
        this.fromuid = fromuid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    public void setFromimage(String fromimage) {
        this.fromimage = fromimage;
    }

    public void setText(String text) {
        this.text = text;
    }
}
