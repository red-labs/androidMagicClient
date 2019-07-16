package com.redmedellin.magicclient.wireless;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

public class Wireless implements WirelessDriver  {

    WifiConfiguration config;
    WifiEnterpriseConfig enterpriseConfig;
    WifiManager wifiManager;


    public Wireless(Context context){
        config = new WifiConfiguration();
        enterpriseConfig = new WifiEnterpriseConfig();
        //Instantiate a wifimanager object. This requires to make the current class an activity so we can access context
        this.wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public boolean has8021xCreds(String ssid, String username, String password) {

        //Only connects to a network whose profile has already been installed
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration config : list) {
            if (config.SSID != null &&
                    config.SSID.equals("\"" + ssid + "\"")
                    && //if SSID matches
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
    /**
     * Connect to a wifi with SSID + pass
     *
     * @param context {@link Context}
     * @param ssid {@link String}
     * @return boolean
     */
    public boolean connect(Context context, @NonNull String ssid) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream in = context.getAssets().open( "server.der");
            X509Certificate cert = (X509Certificate)certFactory.generateCertificate(in);

            WifiConfiguration conf = new WifiConfiguration();
            conf.SSID = "\"" + ssid + "\"";

            conf.status = WifiConfiguration.Status.ENABLED;
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
            conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            conf.enterpriseConfig.setCaCertificate(cert);
            conf.enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.TTLS);
            conf.enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.PAP);
            conf.enterpriseConfig.setIdentity("Ethereum Address");
            long timestamp = System.currentTimeMillis() / 1000L;
            String encodedMessage = "auth_" + timestamp;  // this needs to be signed by ethereuem
            conf.enterpriseConfig.setPassword(timestamp + "-"+encodedMessage);

            wifiManager.addNetwork(conf);

            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();
                    Log.d("connectToWifi", "Success -- with " + ssid);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d("connectToWifi", "Scan not found");
        }
        return false;
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
