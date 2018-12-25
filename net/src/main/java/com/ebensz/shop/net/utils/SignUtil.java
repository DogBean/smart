package com.ebensz.shop.net.utils;

import java.util.Arrays;

public class SignUtil {
    private static final String VERSION = "www.tvbook.cc/textbooks/GetUpdateBooks";

    public static String getSign(String token, String nonc){
        String arr[] = {token,nonc,VERSION};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for(String s:arr){
            content.append(s);
        }
        return Digest.MD5(content.toString().getBytes());
    }

}
