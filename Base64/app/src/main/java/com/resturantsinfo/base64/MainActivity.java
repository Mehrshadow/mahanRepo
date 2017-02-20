package com.resturantsinfo.base64;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    String a = "";
    String b = "";
    String c = "";
    String d = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //             a = sha1("123456","4d5b3ce03ce34c0a911182d4c228aa6c");
        b =encryptPassword("123456");
        try {
            c = Decrypt("ZTIFGTe+P0DtGdnn0XRXyg==","4d5b3ce03ce34c0a911182d4c228aa6c");
            d = Encrypt("123456","4d5b3ce03ce34c0a911182d4c228aa6c");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("TAG",a);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG",a);
        Log.d("TAG",b);
        Log.d("TAGG",c);
        Log.d("TAGG",d);
        Toast.makeText(getApplicationContext(),c,Toast.LENGTH_LONG).show();

    }

    public static String encryptPassword(String clearText) {
        try {
            byte[] keyBytes = new byte[16];
            String PASSWORD_SECRET = "4d5b3ce03ce34c0a911182d4c228aa6c";
            SecretKeySpec keySpec = new SecretKeySpec(
                    PASSWORD_SECRET.getBytes("UTF-8"), "AES");
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypedPwd = Base64.encodeToString(cipher.doFinal(clearText
                    .getBytes("UTF-8")), Base64.DEFAULT);
            return encrypedPwd;
        } catch (Exception e) {
        }
        return clearText;
    }



        public static String Decrypt(String text, String key) throws Exception {
            Cipher cipher = Cipher.getInstance
                    ("AES/CBC/PKCS5Padding"); //this parameters should not be changed
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("UTF-8");
            int len = b.length;
            if (len > keyBytes.length)
                len = keyBytes.length;
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] results = new byte[text.length()];

            try {
//                results = cipher.doFinal(decoder.decodeBuffer(text));
                results = cipher.doFinal(Base64.decode(text,0));
            } catch (Exception e) {
                Log.i("Erron in Decryption", e.toString());
            }
            Log.i("Data", new String(results, "UTF-8"));
            return new String(results, "UTF-8"); // it returns the result as a String
        }

    public static String Encrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.encodeToString(results,0); // it returns the result as a String
    }


    private String sha1(String s, String keyString) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "AES");
        Mac mac = Mac.getInstance("AES");
        mac.init(key);

        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return new String( Base64.encodeToString(bytes,0));

    }
}
