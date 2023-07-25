package com.dz.ftsp.utils;

import com.dz.ftsp.exception.FtspException;
import org.apache.commons.codec.binary.Hex;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具类
 */
public class Md5Utils {
    public static String md5(String s){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("md5");
            return Hex.encodeHexString(md5.digest(s.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new FtspException("计算MD5错误",e);
        }
    }
}
