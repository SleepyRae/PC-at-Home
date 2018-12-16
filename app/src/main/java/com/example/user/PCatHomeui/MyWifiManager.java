package com.example.user.PCatHomeui;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class MyWifiManager {

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private DhcpInfo dhcpInfo;

    public MyWifiManager(Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        dhcpInfo = wifiManager.getDhcpInfo();
    }

    //得到本机ip
    public String getLocalIp(){
        return FormatString(dhcpInfo.ipAddress);
    }

    //得到服务器ip（热点ip）
    public String getServerIp(){
        return FormatString(dhcpInfo.serverAddress);
    }

    //转换ip格式为*.*.*.*
    public String FormatString(int value){
        String strValue="";
        byte[] ary = intToByteArray(value);
        for(int i=ary.length-1;i>=0;i--){
            strValue+=(ary[i]&0xFF);
            if(i>0){
                strValue+=".";
            }
        }
        return strValue;
    }

    public byte[] intToByteArray(int value){
        byte[] b=new byte[4];
        for(int i=0;i<4;i++){
            int offset = (b.length-1-i)*8;
            b[i]=(byte) ((value>>>offset)&0xFF);
        }
        return b;
    }

}
