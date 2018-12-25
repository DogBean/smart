package com.lingzhi.smart.data.bean;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/25
 */
public class LoginResult {


    /**
     * token : 18359245253
     * userId : 8359245253
     * start : 1545740883823
     * end : 1545840883823
     */

    private String token;
    private long userId;
    private long start;
    private long end;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
