package com.redmedellin.magicclient.account;

import com.redmedellin.magicclient.wireless.Wireless;
import com.redmedellin.magicclient.utils.Eth;

public class AccountManager {

    private boolean onboarded;
    private Object address; //TODO change type
    private Object privkey; //TODO change type
    private Wireless wireless;

    public  AccountManager(){
        onboarded = false;
        address = null;
        privkey = null;
        wireless = new Wireless();
    }

    /*
     Set an Ethereum account information. If a new account needs to be created,
     call generateAccount method from utils.Eth class. Otherwise, set the
     address and private key fields to param values.

     @param privkey the private key of an existing account
     @param address the Ethereum address of an existing account
     @param isNewAccount whether a new account needs to be created
     */
    public void setAccountInfo(Object address, Object privkey, boolean isNewAccount){

        if(isNewAccount){
            Object account = Eth.generateAccount(); //TODO change type
            this.address = account.address; //TODO verify the web3j appropriate field name and access
            this.privkey = account.privkey; //TODO verify the web3j appropriate field name and access
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
        boolean hasCreds = this.wireless.has8021xCreds();

        //If there are no 802.1x credentials, create one. Otherwise, all set
        if (!hasCreds){
            //Create a string timestamp
            Long tsLong = System.currentTimeMillis()/1000;
            timestamp = tsLong.toString();
            //Install 802.1x credentials
            this.wireless.install8021xCreds(
                    ssid,
                    this.address,
                    Eth.sign("auth_"  + timestamp, this.privkey),
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

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPrivkey() {
        return privkey;
    }

    public void setPrivkey(Object privkey) {
        this.privkey = privkey;
    }

    public Wireless getWireless() {
        return wireless;
    }

    public void setWireless(Wireless wireless) {
        this.wireless = wireless;
    }

}
