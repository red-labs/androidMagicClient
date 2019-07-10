package com.redmedellin.magicclient.wireless;

public class Wireless implements WirelessDriver{

    @Override
    public boolean has8021xCreds(String ssid, String username, String password) {
        return false;
    }

    @Override
    public void install8021xCreds(String ssid, String username, String password, String timestamp) {

    }

    @Override
    public void connect(String ssid) {

    }

    @Override
    public String getCurrentSsid() {
        return null;
    }

    @Override
    public void scanNetworks() {

    }

    @Override
    public String getAdapter() {
        return null;
    }
}
