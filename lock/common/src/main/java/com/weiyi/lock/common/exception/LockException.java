package com.weiyi.lock.common.exception;

public class LockException extends RuntimeException
{
    private String msg;

    private int code;

    public LockException(int code,String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public LockException(int code)
    {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LockException{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
