/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jlsoft.utils;

/**
 * Hash 实用函数，获取文件或数据的 Hash 值 (MD5)
 * @author XiongJian
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static int bufSize = 1024 * 128;
    public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getHash(byte[] data, String hashType)
            throws NoSuchAlgorithmException, IOException {
        if (null == data || 0 == data.length) {
            return "";
        }
        MessageDigest hash = MessageDigest.getInstance(hashType);
        if (bufSize < data.length) {
            InputStream is = new ByteArrayInputStream(data);
            byte[] buffer = new byte[bufSize];
            int n = 0;
            while (-1 != (n = is.read(buffer))) {
                hash.update(buffer, 0, n);
            }
            is.close();
            is = null;
        } else {
            hash.update(data);
        }
        return toHexString(hash.digest());
    }

    public static String getMd5Hash(byte[] data) {
        String result = "";
        try {
            result = getHash(data, "MD5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getHash(InputStream is, String hashType)
            throws NoSuchAlgorithmException, IOException {
        if (null == is) {
            return "";
        }
        MessageDigest hash = MessageDigest.getInstance(hashType);
        byte[] buffer = new byte[bufSize];
        int n = 0;
        while (-1 != (n = is.read(buffer))) {
            hash.update(buffer, 0, n);
        }
        return toHexString(hash.digest());
    }

    public static String getMd5Hash(InputStream is) {
        String result = "";
        try {
            result = getHash(is, "MD5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getHash(String fileName, String hashType)
            throws NoSuchAlgorithmException, IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        InputStream is = new FileInputStream(fileName);
        MessageDigest hash = MessageDigest.getInstance(hashType);
        byte[] buffer = new byte[bufSize];
        int n = 0;
        while (-1 != (n = is.read(buffer))) {
            hash.update(buffer, 0, n);
        }
        is.close();
        is = null;
        return toHexString(hash.digest());
    }

    public static String getMd5Hash(String fileName) {
        String result = "";
        try {
            result = getHash(fileName, "MD5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}