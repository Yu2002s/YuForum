package com.drny.forum.util;

import com.drny.forum.controller.Password;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtil {

    public static String encrypt(String content) {
        return encrypt(content, Password.KEY, Password.IV);
    }

    public static String decrypt(String content) {
        return decrypt(content, Password.KEY, Password.IV);
    }

    /**
     *加密操作
     * @param content  待加密内容
     * @param sKey 密码
     * @param ivParameter 偏移量
     */
    public static String encrypt(String content, String sKey, String ivParameter) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e){
            return null;
        }
    }

    /**
     *解密操作
     * @param content 待解密内
     * @param sKey 密码
     * @param ivParameter 偏移量
     */
    public static String decrypt(String content, String sKey, String ivParameter) {
        try {
            byte[] raw = sKey.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] encrypted1 = Base64.getDecoder().decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return null;
        }
    }
}
