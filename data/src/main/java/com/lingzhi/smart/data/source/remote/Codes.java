package com.lingzhi.smart.data.source.remote;

import java.util.HashMap;

public final class Codes {
    public static final String SUCCESS              = "000000";
    public static final String AUTH_WRONG           = "101001"; // 平台调用验证错误
    public static final String USER_PASSWD_WRONG    = "101002"; // 用户名密码错误
    public static final String PERMISSION_WRONG     = "101003"; // 没有操作权限
    public static final String FORMAT_WRONG         = "101004"; // 格式错误
    public static final String OVER_LIMIT           = "101005"; // 大小超过限制
    public static final String NOT_FOUND            = "101006"; // 资源没有找到
    public static final String SOLD_OUT             = "101007"; // 已售罄

    public static final String ARGUMENT_WRONG       = "102001"; // 参数错误
    public static final String CAPTCHA_WRONG        = "102002"; // 密码和验证码错误或过期
    public static final String TOKEN_WRONG          = "102003"; // Token错误或过期
    public static final String NUMBER_WRONG         = "102004"; // 号码错误
    public static final String NUMBER_ABSENT        = "102005"; // 号码不存在
    public static final String NUMBER_EXISTS        = "102006"; // 号码已存在
    public static final String APPID_WRONG          = "102007"; // 应用id错误
    public static final String APPID_ABSENT         = "102008"; // 应用id不存在
    public static final String APPID_EXISTS         = "102009"; // 应用id已存在
    public static final String DEVICE_WRONG         = "102010"; // 设备id错误
    public static final String DEVICE_ABSENT        = "102011"; // 设备不存在
    public static final String DEVICE_EXISTS        = "102012"; // 设备已存在
    public static final String ALREADY_BOUND        = "102013"; // 已绑定其他账号

    public static final String ACCOUNT_WRONG        = "103001"; // 账号错误
    public static final String ACCOUNT_ABSENT       = "103002"; // 账号不存在
    public static final String ACCOUNT_EXISTS       = "103003"; // 账号已存在
    public static final String ACCOUNT_LOGOUT       = "103004"; // 账号未登陆
    public static final String ACCOUNT_LOGIN        = "103005"; // 账号已登陆
    public static final String LOGIN_OTHER          = "103006"; // 账号已在其他设备登陆
    public static final String ACCOUNT_BOUND        = "103007"; // 账号已被绑定

    public static final String SMS_WRONG            = "104001"; // 短信发送失败

    public static final String INTERNAL_WRONG       = "111110"; // 内部错误
    public static final String UNKNOWN_WRONG        = "111111"; // 未知错误

    public static final String THIRD_TOKEN_EXPIRED  = "200001"; // 第三方Token不存在，错误或过期
    public static final String NUMBER_UNREGISTERED  = "300001"; // 手机号码不存在,加密通话注册使用

    public static boolean isSuccess(String errorCode) {
        return SUCCESS.equals(errorCode);
    }

    public static String errMessage(String errorCode) {
        return errors.get(errorCode);
    }

    private static final HashMap<String, String> errors = new HashMap<>();
    static {
        errors.put(AUTH_WRONG          , AUTH_WRONG + ":平台调用验证错误");
        errors.put(USER_PASSWD_WRONG   , USER_PASSWD_WRONG + ":用户名密码错误");
        errors.put(ARGUMENT_WRONG      , ARGUMENT_WRONG + ":参数错误");
        errors.put(CAPTCHA_WRONG       , CAPTCHA_WRONG + ":密码和验证码错误或过期");
        errors.put(TOKEN_WRONG         , TOKEN_WRONG + ":Token错误或过期");
        errors.put(PERMISSION_WRONG    , PERMISSION_WRONG + ":没有操作权限");
        errors.put(FORMAT_WRONG        , FORMAT_WRONG + ":格式不支持");
        errors.put(OVER_LIMIT          , OVER_LIMIT + ":大小超过限制");
        errors.put(NOT_FOUND           , OVER_LIMIT + ":资源没有找到");
        errors.put(SOLD_OUT            , SOLD_OUT + ":已售罄");
        errors.put(NUMBER_WRONG        , NUMBER_WRONG + ":号码错误");
        errors.put(NUMBER_ABSENT       , NUMBER_ABSENT + ":号码不存在");
        errors.put(NUMBER_EXISTS       , NUMBER_EXISTS + ":号码已存在");
        errors.put(APPID_WRONG         , APPID_WRONG + ":应用id错误");
        errors.put(APPID_ABSENT        , APPID_ABSENT + ":应用id不存在");
        errors.put(APPID_EXISTS        , APPID_EXISTS + ":应用id已存在");
        errors.put(DEVICE_WRONG        , DEVICE_WRONG + ":设备id错误");
        errors.put(DEVICE_ABSENT       , DEVICE_ABSENT + ":设备不存在");
        errors.put(DEVICE_EXISTS       , DEVICE_EXISTS + ":设备已存在");
        errors.put(ALREADY_BOUND       , ALREADY_BOUND + ":已经绑定其他账号");
        errors.put(ACCOUNT_WRONG       , ACCOUNT_WRONG + ":账号错误");
        errors.put(ACCOUNT_ABSENT      , ACCOUNT_ABSENT + ":账号不存在");
        errors.put(ACCOUNT_EXISTS      , ACCOUNT_EXISTS + ":账号已存在");
        errors.put(ACCOUNT_LOGOUT      , ACCOUNT_LOGOUT + ":账号未登陆");
        errors.put(ACCOUNT_LOGIN       , ACCOUNT_LOGIN + ":账号已登陆");
        errors.put(LOGIN_OTHER         , LOGIN_OTHER + ":账号已在其他设备登陆");
        errors.put(ACCOUNT_BOUND       , ACCOUNT_BOUND + ":账号已被绑定");
        errors.put(SMS_WRONG           , SMS_WRONG + ":短信发送失败");
        errors.put(INTERNAL_WRONG      , INTERNAL_WRONG + ":内部错误");
        errors.put(UNKNOWN_WRONG       , UNKNOWN_WRONG + ":未知错误");
        errors.put(NUMBER_UNREGISTERED , NUMBER_UNREGISTERED + ":未注册加密通话");
        errors.put(THIRD_TOKEN_EXPIRED , THIRD_TOKEN_EXPIRED + ":第三方Token不存在，错误或过期");
    }
}
