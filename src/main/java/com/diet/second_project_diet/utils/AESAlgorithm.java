package com.diet.second_project_diet.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class AESAlgorithm {
  public static String Decrypt(String text) throws Exception{
    String key = "pwd!@#$";
      
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      byte[] keyBytes= new byte[16];
      byte[] b= key.getBytes("UTF-8");
      int len= b.length;
      if (len > keyBytes.length) len = keyBytes.length;
      System.arraycopy(b, 0, keyBytes, 0, len);
      SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
      IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
      cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
      
      byte [] results = cipher.doFinal(Base64.decodeBase64(text));
      return new String(results,"UTF-8");
   }

    public static String Encrypt(String text) throws Exception{
      String key = "pwd!@#$";
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes= new byte[16];
        byte[] b= key.getBytes("UTF-8");
        int len= b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);

        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.encodeBase64String(results);
    }
}