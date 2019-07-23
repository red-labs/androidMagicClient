package com.redmedellin.magicclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.redmedellin.magicclient.account.AccountManager;
import com.redmedellin.magicclient.utils.Eth;
import com.redmedellin.magicclient.wireless.Wireless;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.CipherException;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class OnboardNewUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_new_user);
    }

    public void returnToMainActivity(View view) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    public void createNewAccount(View view) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, JSONException {
        AccountManager accountManager = new AccountManager(new Wireless(this));
        accountManager.setAccountInfo(null, null, true);

    }
}
