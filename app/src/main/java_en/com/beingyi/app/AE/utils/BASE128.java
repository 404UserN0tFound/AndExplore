package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

import com.beingyi.app.AE.application.MyApplication;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


public class BASE128 {
    private static final String BASETYPE ="AES/ECB/PKCS5Padding";

    public static String Encrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try{
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(BASETYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
        return new BASE64Encoder().encode(encrypt);
    }

    public static String Decrypt(String keyStr, String encryptData) {
        byte[] decrypt = null;
        try{
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(BASETYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(new BASE64Decoder().decodeBuffer(encryptData));

        }catch(Exception e){
            e.printStackTrace();
        }
        return new String(decrypt);
    }

    private static Key generateKey(String key)throws Exception{
        try{
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AesUtil.decode("uACDUSHsfPQWQtyzwI2KVw=="));
            return keySpec;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }

    }


    public static String key(){
        return Conf.getBase128Key(MyApplication.getContext());
    }


    //加密
    public static String encode(String str){

        return Encrypt(key(),str.replace("\n","x0xnby0")).replace("\n", "x0xnby1").replace("\r", "x0xnby2");
    }

    //解密
    public static String decode(String str){
        return Decrypt(key(),str.replace("x0xnby2","\r").replace("x0xnby1","\n")).replace("x0xnby0","\n");
    }




}