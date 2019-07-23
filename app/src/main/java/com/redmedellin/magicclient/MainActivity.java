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

        //Initialise text boxes to display account info
        EditText addressInfo = (EditText) this.findViewById(R.id.addressInfoBox);
        EditText privKeyInfo = (EditText) this.findViewById(R.id.privKeyInfoBox);
        //Initialise wireless
        accountManager = new AccountManager(new Wireless(this));

        //Display new Ethereum account as a default in the text boxes
        try {
            accountManager.setAccountInfo(null, null, true);
            addressInfo.setText(accountManager.getAddress());
            privKeyInfo.setText(accountManager.getPrivkey());
            //Register text change listeners for address & private key text boxes
            addressInfo.addTextChangedListener(addressTextWatcher);
            privKeyInfo.addTextChangedListener(privKeyTextWatcher);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private TextWatcher addressTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            accountManager.setAddress(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher privKeyTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            accountManager.setPrivkey(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
