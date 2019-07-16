package com.redmedellin.magicclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.redmedellin.magicclient.account.AccountManager;
import com.redmedellin.magicclient.wireless.Wireless;

public class MainActivity extends AppCompatActivity {

    AccountManager accountManager;
    Wireless wireless;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialise data model
        wireless = new Wireless(this);
        accountManager = new AccountManager(wireless);

        setContentView(R.layout.activity_main);
        //TODO UI stuff
    }

    //TODO ask Dom about critical info and what it is
    /*
    * Main bits of functionality to implement
    *
    * -> Set user info
    * -> Install WPA2-E profile
    * -> Connect user
    *
     */

    /**
     * SET USER INFO PSEUDOCODE:
     * if(isUserNew){
     *     JSONObject newAcct = Eth.generateAccount();
     *     this.address = newAcct.address;
     *     this.privkey = newAcct.privKey;
     *
     * } else {
     *
     *     this.privkey = getPrivKeyFromUser();
     *     this.address = getAddressFromUser();
     * }
     */

    /**
     * INSTALL WPA2-E PROFILE PSEUDOCODE
     * accountManager.setup8021xCreds("magic", this.address, this.privkey);
     */

}
