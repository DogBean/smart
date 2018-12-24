package com.ebensz.shop.net.utils;

import android.os.Build;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;

public class Utils {
    private static final String VERSION = "www.tvbook.cc/textbooks/GetUpdateBooks";
    public static String getSignature(String token, String nonc){
        String[] args = {token,VERSION,nonc};
        Arrays.sort(args);

        StringBuilder content = new StringBuilder();
        for(String s:args){
            content.append(s);
        }

        return MD5.getMD5(content.toString().getBytes());
    }
}
