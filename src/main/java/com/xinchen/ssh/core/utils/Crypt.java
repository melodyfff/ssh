package com.xinchen.ssh.core.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 简单加密/解密方法包装
 */
public class Crypt {
    private static final String PASSWORD_CRYPT_KEY = "xinchen_password";
    private static final String DATA_CRYPT_KEY = "xinchen_data";
    private final static String DES = "DES";

    // 日志对象
    private final static transient Log logger = LogFactory.getLog(Crypt.class);

    /**
     * 加密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return byte[] 返回加密后的数据
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return byte[] 返回解密后的原始数据
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);
    }

    /**
     * 密码解密
     *
     * @param data 字符串
     * @return String 加密字符串
     */
    public final static String decrypt(String data) {
        try {
            return new String(decrypt(hex2byte(data.getBytes(Charset.forName("UTF-8"))),
                    PASSWORD_CRYPT_KEY.getBytes(Charset.forName("UTF-8"))),StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @return String 加密密码
     */
    public final static String encrypt(String password) {
        try {
            return byte2hex(encrypt(password.getBytes(Charset.forName("UTF-8")),
                    PASSWORD_CRYPT_KEY.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密码解密
     *
     * @param data 字符串
     * @param key key
     * @return String 加密字符串
     */

    public final static String decrypt(String data, String key) {
        try {
            return new String(
                    decrypt(hex2byte(data.getBytes(Charset.forName("UTF-8"))), key.getBytes(Charset.forName("UTF-8"))),StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @param key key
     * @return String 加密密码
     */
    public final static String encrypt(String password, String key) {
        try {
            return byte2hex(encrypt(password.getBytes(Charset.forName("UTF-8")), key.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密码解密
     *
     * @param data 待加密数据
     * @return String 加密字符串
     */
    public final static String decryptData(String data) {
        try {
            return new String(decrypt(hex2byte(data.getBytes(Charset.forName("UTF-8"))),
                    DATA_CRYPT_KEY.getBytes(Charset.forName("UTF-8"))),StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @return String 加密密码
     */
    public final static String encryptData(String password) {
        try {
            return byte2hex(encrypt(password.getBytes(Charset.forName("UTF-8")),
                    DATA_CRYPT_KEY.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 二行制转字符串
     *
     * @param b 字节数组
     * @return String 结果
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder("");
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }

        }
        return hs.toString().toUpperCase();
    }

    /**
     *
     * 16位转换字节
     *
     * @param  b 字节数组
     * @return byte[] 数组
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("length is not even number!");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2,StandardCharsets.UTF_8);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }


}
