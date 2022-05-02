package com.example.art.util;

import com.google.common.hash.Hashing;

public class HashUtil {
    public static String md5(String data) {
        return Hashing.md5().hashBytes(data.getBytes()).toString();
    }

    public static String sha256(String data) {
        return Hashing.sha256().hashBytes(data.getBytes()).toString();
    }
    public static void main(String[] args) {
    }
}
