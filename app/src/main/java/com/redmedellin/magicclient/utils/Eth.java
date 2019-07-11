package com.redmedellin.magicclient.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

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

    public static Sign.SignatureData sign(String message, String privkey) {

        Credentials credentials = Credentials.create(privkey); //Generate a temp credentials object so signMessage function accepts it
        String hash = Hash.sha3(Numeric.toHexStringNoPrefix(message.getBytes())); 
        String completeMessage = "\\x19Ethereum Signed Message:\n" + hash.length() + hash; //Taken from https://github.com/web3j/web3j/issues/208
        byte[] data = completeMessage.getBytes();
        Sign.SignatureData signed = Sign.signMessage(data, credentials.getEcKeyPair());
        return signed;
    }
}
