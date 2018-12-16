package com.example.user.PCatHomeui;

import android.app.Application;

public class MyApplication extends Application {
    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    private String ipAddr = null;
}
