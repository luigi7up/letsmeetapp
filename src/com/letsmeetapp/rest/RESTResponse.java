package com.letsmeetapp.rest;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 5/06/13
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */
public class RESTResponse {
    private String mData;
    private int    mCode;

    public RESTResponse() {
    }

    public RESTResponse(String data, int code) {
        mData = data;
        mCode = code;
    }

    public String getData() {
        return mData;
    }

    public int getCode() {
        return mCode;
    }
}
