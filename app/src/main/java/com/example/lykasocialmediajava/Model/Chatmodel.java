package com.example.lykasocialmediajava.Model;

public class Chatmodel {
    String fromUID,toUID,convID;

    public Chatmodel(String fromUID, String toUID, String convID) {
        this.fromUID = fromUID;
        this.toUID = toUID;
        this.convID = convID;
    }

    public String getFromUID() {
        return fromUID;
    }

    public void setFromUID(String fromUID) {
        this.fromUID = fromUID;
    }

    public String getToUID() {
        return toUID;
    }

    public void setToUID(String toUID) {
        this.toUID = toUID;
    }

    public String getConvID() {
        return convID;
    }

    public void setConvID(String convID) {
        this.convID = convID;
    }
}
