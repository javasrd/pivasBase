package com.zc.schedule.common.util;

import java.io.UnsupportedEncodingException;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CryptoException;

/**
 * AES加解密类
 *
 * @author Justin
 * @version v1.0
 */
public class AES128Util {
    /**
     * 加密
     *
     * @param plaintext 待加密的内容
     * @param textKey   密钥
     * @return 加密后的密文
     */
    public static String encrypt(String plaintext, String textKey, String charsetName)
            throws UnsupportedEncodingException {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        byte[] plaintBytes = plaintext.getBytes(charsetName == null ? "ISO8859-1" : charsetName);
        byte[] keyBytes = textKey.getBytes(charsetName == null ? "ISO8859-1" : charsetName);
        if (keyBytes.length * 8 != 128) {
            throw new RuntimeException("key must be 128 bit !");
        }
        return aesCipherService.encrypt(plaintBytes, keyBytes).toHex();
    }

    /**
     * 解密
     *
     * @param ciphertext 待解密的内容
     * @param textKey    密钥
     * @return String 解密后的明文
     */
    public static String decrypt(String ciphertext, String textKey, String charsetName)
            throws CryptoException, UnsupportedEncodingException {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        byte[] cipherBytes = Hex.decode(ciphertext);
        byte[] keyBytes = textKey.getBytes(charsetName == null ? "ISO8859-1" : charsetName);
        if (keyBytes.length * 8 != 128) {
            throw new RuntimeException("key must be 128 bit !");
        }
        return new String(aesCipherService.decrypt(cipherBytes, keyBytes).getBytes(), charsetName);
    }

    public static void main(String[] args) {
        try {
            System.out.println(AES128Util.encrypt("zc@20171129", "0123456789ABCDEF", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
