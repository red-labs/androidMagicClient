package com.redmedellin.magicclient.wireless;

import android.net.wifi.ScanResult;

import java.util.List;

public interface WirelessDriver {

    //Check if 802.1x credentials are already installed
    public  boolean has8021xCreds(String ssid, String username, String password);

    //Install/add 802.1x credentials to Android
    public void install8021xCreds(String ssid, String username, String password, String timestamp);

    //Connect to the network specified by ssid.
    public void connect(String ssid);

    //Get current network's SSID
    public String getCurrentSsid();

    //Scan for available wifi networks
    public List<ScanResult> scanNetworks();

    //Get the details of the current network interface or adapter
    public String getAdapter();

}
