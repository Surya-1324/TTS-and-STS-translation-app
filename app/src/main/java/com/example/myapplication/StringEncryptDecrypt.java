package com.example.myapplication;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class StringEncryptDecrypt {
    private static final String TAG = "StringEncryptDecrypt";
    private static final String PASSWORD = "qwertyuiop";
    private static final String SALT = "9842371619";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private static final String ALGORITHM = "AES";

    public static String encrypt(String[] strings) {
        try {

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");


            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);


            StringBuilder encryptedStrings = new StringBuilder();
            for (String str : strings) {
                byte[] encryptedBytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
                String encryptedStr = Base64.getEncoder().encodeToString(encryptedBytes);
                encryptedStrings.append(encryptedStr).append("\n");
            }
            return encryptedStrings.toString();
        } catch (Exception e) {
            Log.e(TAG, "Encryption failed: " + e.getMessage());
            return null;
        }
    }
    public static String decrypt(String encryptedData) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            StringBuilder decryptedStrings = new StringBuilder();
            String[] encryptedStrings = encryptedData.split("\n");
            for (String encryptedStr : encryptedStrings) {
                byte[] encryptedBytes = Base64.getDecoder().decode(encryptedStr);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                String decryptedStr = new String(decryptedBytes, StandardCharsets.UTF_8);
                decryptedStrings.append(decryptedStr).append("\n");
            }
            return decryptedStrings.toString();
        } catch (Exception e) {
            Log.e(TAG, "Decryption failed: " + e.getMessage());
            return null;
        }
    }
}
