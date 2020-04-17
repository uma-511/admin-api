package com.lgmn.adminapi.vo;

import lombok.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: TJM
 * @Date: 2020/4/15 16:21
 */
@Data
public class ScanVo {
    private String hash;
    private String number;
    private String name;
    private String specs;
    private String width;
    private String color;

    private String labelNum;
    private Integer quantity;

    private String refundNum;
    private String deliveryNum;

    public void genHash(){
        String key = number+name+specs+width+color;
        String hashValue = hashKeyForDisk(key);
        this.hash = hashValue;
    }

    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}