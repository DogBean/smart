package com.smart.linguoyong.smart.base;

/**
 * Created by cs on 16/12/2018.
 */

public class BaseResponseData<T> {

    /**
     * code : 200
     * msg : 返回数据成功
     * data :
     */

    private int code;
    private String msg;
    private T data;

    /**
     * 数据是否过期
     */
    private boolean upToDate;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isUpToDate() {
        return upToDate;
    }

    public void setUpToDate(boolean upToDate) {
        this.upToDate = upToDate;
    }
}
