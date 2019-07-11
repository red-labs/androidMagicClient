package com.redmedellin.magicclient.wireless;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;

public class Wireless implements WirelessDriver{

    WifiConfiguration config;
    WifiEnterpriseConfig enterpriseConfig;

    public Wireless(){
        config = new WifiConfiguration();
        enterpriseConfig = new WifiEnterpriseConfig();
    }

    @Override
    public boolean has8021xCreds(String ssid, String username, String password) {
        return false;
    }

    @Override
    public void install8021xCreds(String ssid, String username, String password, String timestamp) {

        //Add the SSID info to the basic wifi config object, and enable it
        config.SSID = "\"" + ssid + "\"";
        config.priority = 1;
        config.status = WifiConfiguration.Status.ENABLED;

        //Tell the config object we'll be using EAP and 802.1x
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);

        //Set up the enterprise config parameters
        enterpriseConfig.setIdentity(username); //is the address parameter in linux_nmcli
        String pw = password + "-" + timestamp; //as per original Python client format
        enterpriseConfig.setPassword(pw);

        enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.TTLS); //TTLS option from Python cli
        enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.PAP);

        //Add the enterprise configs to the basic WifiConfiguration object
        config.enterpriseConfig = this.enterpriseConfig;

    }

    @Override
    public void connect(String ssid) {

        config.SSID = ssid;
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
