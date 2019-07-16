package com.redmedellin.magicclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.redmedellin.magicclient.account.AccountManager;
import com.redmedellin.magicclient.wireless.Wireless;

public class MainActivity extends AppCompatActivity {

    AccountManager accountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO UI stuff
    }

}
