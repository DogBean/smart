package com.lingzhi.smart.data.request;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/25
 */
public class AccountRequest {
    private String number;
    private String captcha;

    public AccountRequest(String number, String captcha) {
        this.number = number;
        this.captcha = captcha;
    }
}
