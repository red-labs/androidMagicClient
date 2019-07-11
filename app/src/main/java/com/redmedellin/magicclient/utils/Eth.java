package com.redmedellin.magicclient.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Eth {
    public static JSONObject generateAccount() throws JSONException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        JSONObject accountInfo = new JSONObject();

        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

        String sPrivatekeyInHex = privateKeyInDec.toString(16);

        WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
        String sAddress = aWallet.getAddress();

        accountInfo.put("address", "0x" + sAddress);
        accountInfo.put("privatekey", sPrivatekeyInHex);

        return accountInfo;
    }

    public static String sign(String s, Object privkey) {
        //TODO code to sign a message
        return "";
    }
}
