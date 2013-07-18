package com.letsmeetapp.rest;

/**
 * Created with IntelliJ IDEA.
 * User: luka
 * Date: 15.07.13.
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class Session {

    private static Session instance;
    private String email;
    private String md5Pass;


    private Session(){}

    public static Session getInstance(){
        if(instance == null){
            return new Session();
        }else return instance;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMd5Pass() {
        return md5Pass;
    }
    public void setMd5Pass(String md5Pass) {
        this.md5Pass = md5Pass;
    }
}
