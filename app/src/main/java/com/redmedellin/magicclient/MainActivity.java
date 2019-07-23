package com.redmedellin.magicclient;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.redmedellin.magicclient.account.AccountManager;
import com.redmedellin.magicclient.wireless.Wireless;

public class MainActivity extends Activity {

    AccountManager accountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText accountInfo = (EditText) this.findViewById(R.id.privKeyInfoBox);
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

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
