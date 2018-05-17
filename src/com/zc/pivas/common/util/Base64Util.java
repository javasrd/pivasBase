package com.zc.pivas.common.util;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * Base64加密工具
 *
 * @author  cacabin
 * @version  1.0
 */
public class Base64Util {

    public static byte[] encode(byte[] encryptData) {
        if (encryptData == null) {
            return null;
        }

        byte[] encodeBase64 = null;
        encodeBase64 = Base64.encodeBase64(encryptData);
        return encodeBase64;

    }

    public static byte[] decode(byte[] encryptData) {
        if (null == encryptData) {
            return null;
        }

        byte[] encodeBase64 = null;
        encodeBase64 = Base64.decodeBase64(encryptData);
        return encodeBase64;
    }
}