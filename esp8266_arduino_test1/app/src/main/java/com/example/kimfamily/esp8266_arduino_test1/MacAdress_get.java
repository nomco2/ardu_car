package com.example.kimfamily.esp8266_arduino_test1;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by KimFamily on 2017-10-29.
 */

public class MacAdress_get {

    public String getMACAddress(Context mContext, String interfaceName) {



        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : interfaces) {
                    if (interfaceName != null) {
                        if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                    }
                    byte[] mac = intf.getHardwareAddress();
                    if (mac == null) return "";
                    StringBuilder buf = new StringBuilder();
                    for (int idx = 0; idx < mac.length; idx++)
                        buf.append(String.format("%02X:", mac[idx]));
                    if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                    return buf.toString();
                }
            }else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                WifiManager mng = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = mng.getConnectionInfo();
                String wifiMac = info.getMacAddress();
                return wifiMac;
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }


}
