package com.letsmeetapp.rest;

/**
 * Simple representation of a response from a server. It contains returned data in form of a String and a response code
 * This class is normally used as a response that RESTLoader sends to caller...
 */
public class RESTResponse {

    public static final int FORBIDDEN = 403;        //should be set when host was unreachable and RESTResponse() was generated in the catch of RESTLoader

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
