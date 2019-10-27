package com.hr.util;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;




/**
 *名称：AES加密
 *创建人：xw
 *创建时间：2017/3/29 下午 2:34
 *详细说明：AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AESUtil {

    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private static String sKey = "c02cb954-d2f8-42";//密钥
    //    private static String sKey = "c02cb854-d2f8-41f0-b130-9b1c62d4";
    private static String ivParameter = "47f6-a125-a5282f";//密钥偏移量

    /**
     * 加密
     *
     * @param sSrc 要加密的内容
     * @return 加密后的内容
     */
    public static String encrypt(String sSrc) {
        return encrypt(sSrc, sKey, ivParameter);
    }

    /**
     * 解密
     *
     * @param sSrc 要解密的内容
     * @return 解密后的内容
     * @throws Exception
     */
    public static String decrypt(String sSrc) {
        return decrypt(sSrc, sKey, ivParameter);
    }

    /**
     * 加密
     *
     * @param encData   要加密的内容
     * @param secretKey 加密密钥
     * @param vector    密钥偏移量
     * @return 加密后的内容
     */
    public static String encrypt(String encData, String secretKey, String vector) {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = secretKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
            return Base64.encodeBase64String(encrypted);
           // return new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");// 此处使用BASE64做转码。
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param sSrc 要解密的内容
     * @param key  解密使用的密钥
     * @param ivs  密钥偏移量
     * @return 解密后的内容
     * @throws Exception
     */
    public static String decrypt(String sSrc, String key, String ivs) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes());
            //byte[] encrypted1 = Base64.decode(sSrc.getBytes(), Base64.DEFAULT);
            //byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
//            return new String(Base64.encode(original, Base64.DEFAULT), "UTF-8");// 此处使用BASE64做转码。
        } catch (Exception ex) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }
        return strBuf.toString();
    }
}
