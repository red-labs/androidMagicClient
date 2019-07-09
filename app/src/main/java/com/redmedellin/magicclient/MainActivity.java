package com.redmedellin.magicclient;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtWifiInfo;
    Button btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtWifiInfo = (TextView) findViewById(R.id.idTxt);
        btnInfo = (Button) findViewById(R.id.idBtn);

    }

    public void getWifiInformation(View view) {

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String macAddress = wifiInfo.getMacAddress();
        String bssid = wifiInfo.getBSSID();
        String info = "IP: " + Formatter.formatIpAddress(ip) +
                "\n" + "Mac: " + macAddress +
                "\n" + "BSSID: " + bssid;
        txtWifiInfo.setText(info);


    }
}
