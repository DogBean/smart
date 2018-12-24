package com.ebensz.shop.net.utils;

public class ApiConstants {
    public static final String TAG = "EAccount";

    private static String userAgent = null;


    public static String BASE_URL = "https://192.168.0.156:8080";

    public static String defaultUserAgent() {
        if (userAgent == null) {
            userAgent = System.getProperty("http.agent", null);
            if (userAgent == null) {
                userAgent = "Java" + System.getProperty("java.version");
            }
        }
        return userAgent;
    }

}