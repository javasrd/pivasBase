package com.zc.base.sys.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA256Util {
    public static final String ALGORITHM = "SHA-256";
    private static Logger log = LoggerFactory.getLogger(SHA256Util.class);


    public static String SHA256Encrypt(String orignal) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        if (md != null) {
            byte[] origBytes = orignal.getBytes();
            md.update(origBytes);
            byte[] digestRes = md.digest();
            String digestStr = getDigestStr(digestRes);
            return digestStr;
        }

        return null;
    }

    private static String getDigestStr(byte[] origBytes) {
        String tempStr = null;
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < origBytes.length; i++) {
            tempStr = Integer.toHexString(origBytes[i] & 0xFF);
            if (tempStr.length() == 1) {
                stb.append("0");
            }
            stb.append(tempStr);
        }

        return stb.toString();
    }
}
