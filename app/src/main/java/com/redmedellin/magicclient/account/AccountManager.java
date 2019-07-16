package com.redmedellin.magicclient.account;

import com.redmedellin.magicclient.wireless.Wireless;
import com.redmedellin.magicclient.utils.Eth;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.CipherException;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AccountManager {

    private boolean onboarded;
    private String address;
    private String privkey;
    private Wireless wireless;

    //Pass wireless object from main activity to ensure there's only one instance running
    public  AccountManager(Wireless wireless){
        onboarded = false;
        address = null;
        privkey = null;
        this.wireless = wireless;
    }

    /*
     Set an Ethereum account information. If a new account needs to be created,
     call generateAccount method from utils.Eth class. Otherwise, set the
     address and private key fields to param values.

     @param privkey the private key of an existing account
     @param address the Ethereum address of an existing account
     @param isNewAccount whether a new account needs to be created
     */
    public void setAccountInfo(String address, String privkey, boolean isNewAccount) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, JSONException {

        if(isNewAccount){
            JSONObject account = Eth.generateAccount();
            this.address = account.getString("address");
            this.privkey = account.getString("privatekey");
        }
        else {
            this.address = address;
            this.privkey = privkey;
        }

        this.onboarded = true;
    }

    /*
     Install the 802.1x credentials if not already present.
     */
    public void setup8021xCreds(String ssid){

        String timestamp;
        //TODO change username & password
        boolean hasCreds = this.wireless.has8021xCreds(ssid, "username", "password");

        //If there are no 802.1x credentials, create one. Otherwise, all set
        if (!hasCreds){
            //Create a string timestamp
            Long tsLong = System.currentTimeMillis()/1000;
            timestamp = tsLong.toString();
            //Install 802.1x credentials
            this.wireless.install8021xCreds(
                    ssid,
                    this.address,
                    Eth.sign("auth_"  + timestamp, this.privkey), //TODO figure out which part of the signature is needed (v, r, or s)
                    timestamp
            );

        }

    }


    //GENERIC GETTERS AND SETTERS

    public boolean isOnboarded() {
        return onboarded;
    }

    public void setOnboarded(boolean onboarded) {
        this.onboarded = onboarded;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getPrivkey() {
        return privkey;
    }

    public void setPrivkey(String privkey) {
        this.privkey = privkey;
    }

    public Wireless getWireless() {
        return wireless;
    }

    public void setWireless(Wireless wireless) {
        this.wireless = wireless;
    }

}
