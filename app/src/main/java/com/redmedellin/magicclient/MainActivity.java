package com.redmedellin.magicclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.redmedellin.magicclient.account.AccountManager;
import com.redmedellin.magicclient.wireless.Wireless;

import org.json.JSONException;
import org.web3j.crypto.CipherException;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MainActivity extends Activity {

    AccountManager accountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView accountInfo = (TextView)this.findViewById(R.id.accountInfoBox);
        accountManager = new AccountManager(new Wireless(this));

        //Display new Ethereum account on activity creation
        try {
            accountManager.setAccountInfo(null, null, true);
            accountInfo.setText("Address:   " + accountManager.getAddress()
                                + "\n\nPrivate key, keep this hidden:  " + accountManager.getPrivkey());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public void existingAccountOnboard(View view) {
        Intent i = new Intent(getApplicationContext(),OnboardNewUserActivity.class);
        startActivity(i);
    }

    public void newAccountOnboard(View view) {
        // TODO
    }*/



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
