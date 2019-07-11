package com.redmedellin.magicclient.wireless;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;


import java.util.List;

public class Wireless extends Activity implements WirelessDriver  {

    WifiConfiguration config;
    WifiEnterpriseConfig enterpriseConfig;
    WifiManager wifiManager;


    public Wireless(){
        config = new WifiConfiguration();
        enterpriseConfig = new WifiEnterpriseConfig();
        //Instantiate a wifimanager object. This requires to make the current class an activity so we can access context
        this.wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public boolean has8021xCreds(String ssid, String username, String password) {

        //Only connects to a network whose profile has already been installed
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration config : list) {
            if (config.SSID != null &&
                    config.SSID.equals("\"" + ssid + "\"") && //if SSID matches
                    config.enterpriseConfig != null &&
                    config.enterpriseConfig.getIdentity().equals(username)){ //and the enterprise identity matches

                return true;
            }
        }
        //if no match is found within the set of installed profiles
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

        //Only connects to a network whose profile has already been installed
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration config : list) {
            if (config.SSID != null && config.SSID.equals("\"" + ssid + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(config.networkId, true);
                wifiManager.reconnect();
                System.out.println("Success!");
                break;
            }
        }
    }

    @Override
    public String getCurrentSsid() {
        return wifiManager.getConnectionInfo().getSSID();
    }

    @Override
    public List<ScanResult> scanNetworks() {
        return wifiManager.getScanResults();
    }

    @Override
    public String getAdapter() {
        //TODO
        return "";
    }

    public boolean getWifiStatus(){
        return wifiManager.isWifiEnabled();
    }

    public String getMACAddress(){
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    public String getBSSID(){
        return wifiManager.getConnectionInfo().getBSSID();
    }

    public String getChannel(){
        //TODO
        return "";
    }
}
