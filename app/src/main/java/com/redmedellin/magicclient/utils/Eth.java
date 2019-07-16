package com.redmedellin.magicclient.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.Ethereum;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Eth {

    public static JSONObject generateAccount() throws JSONException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException {
        JSONObject accountInfo = new JSONObject();

        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

        String sPrivatekeyInHex = privateKeyInDec.toString(16);

        //Create a wallet object using the current timestamp as the password and the keypair from above
        WalletFile aWallet = Wallet.createLight(Long.toString(System.currentTimeMillis()), ecKeyPair);
        String sAddress = aWallet.getAddress();

        accountInfo.put("address", "0x" + sAddress);
        accountInfo.put("privatekey", sPrivatekeyInHex);

        return accountInfo;
    }
    /*
    Sign a message given a private key
    TODO look into whether double-hashing is a problem
     */
    public static Sign.SignatureData sign(String message, String privkey) {

        Credentials credentials = Credentials.create(privkey); //Generate a temp credentials object so signMessage function accepts it
        String hash = Hash.sha3(Numeric.toHexStringNoPrefix(message.getBytes()));
        String completeMessage = "\\x19Ethereum Signed Message:\n" + hash.length() + hash; //Taken from https://github.com/web3j/web3j/issues/208
        byte[] data = completeMessage.getBytes();
        Sign.SignatureData signed = Sign.signMessage(data, credentials.getEcKeyPair());
        return signed;
    }

    /*
    Verify a signed message
    TODO look into whether double-hashing is a problem
     */
    boolean verifySig (String message, Sign.SignatureData signature, String address){

        //Hash the message and put it into raw byte form for the recovery function to use
        String hash = Hash.sha3(Numeric.toHexStringNoPrefix(message.getBytes()));
        String completeMessage = "\\x19Ethereum Signed Message:\n" + hash.length() + hash; //Taken from https://github.com/web3j/web3j/issues/208
        byte[] data = completeMessage.getBytes();

        //The recovery yields 4 possible keys, so iterate until the function yields a valid (non-null) key result
        for (int i = 0; i < 4; i++) {

            BigInteger publicKey = Sign.recoverFromSignature(i,
                    new ECDSASignature(new BigInteger(1, signature.getR()), new BigInteger(1, signature.getS())),
                    data);
            if (publicKey != null){
                String recoveredAddress = "0x" + Keys.getAddress(publicKey);
                //Check if the resulting address is equal to the supplied one
                return (recoveredAddress.equals(address));
            }
        }
        return false;
    }

    String publicToAddress (BigInteger publicKey){
        //TODO - method unreferenced in the rest of the codebase
        return "";
    }
}
