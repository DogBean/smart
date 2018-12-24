package com.lingzhi.smart.data.source.remote;


public final class Resp<T> {
    private final String code;
    private final String msg;
    private final T data;

    private Resp(String code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return Codes.isSuccess(code);
    }

    public boolean hasData() {
        return Codes.isSuccess(code) && data != null;
    }

    public static <T> Resp<T> code(String code) {
        return new Resp<>(code, null, null);
    }

    public static <T> Resp<T> code(String code, String msg) {
        return new Resp<>(code, msg, null);
    }

    public static <T> Resp<T> ok() {
        return new Resp<>(Codes.SUCCESS, null, null);
    }

    public static <T> Resp<T> ok(T data) {
        return new Resp<>(Codes.SUCCESS, null, data);
    }
}
