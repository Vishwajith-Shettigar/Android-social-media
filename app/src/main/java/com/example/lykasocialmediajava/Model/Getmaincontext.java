package com.example.lykasocialmediajava.Model;

import android.content.Context;

public class Getmaincontext {
    static Context context;

    public static void setContext(Context context) {
        Getmaincontext.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
