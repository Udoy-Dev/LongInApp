package com.example.longinapp;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyMethods {


    public static String MY_KEY = "";

    public static String encryptData(String text) throws Exception{

        String plainText = text;
        byte[] plainTextByte = plainText.getBytes("UTF-8");
        String password = "udoyDAS@01757580";
        byte[] passwordByte = password.getBytes("UTF-8");

        SecretKeySpec secretKeySpec = new SecretKeySpec(passwordByte,"AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] secretByte = cipher.doFinal(plainTextByte);
        String encodeString = Base64.encodeToString(secretByte,Base64.DEFAULT);
        return encodeString;

    }


}
