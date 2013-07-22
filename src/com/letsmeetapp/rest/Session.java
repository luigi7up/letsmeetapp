package com.letsmeetapp.rest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Singleton class holding the user's credentials for authenticating the REST requests...
 */
public class Session {

    private static Session instance;
    private String email;
    private String md5Pass;
    private boolean isLoggedIn;


    private Session(){}

    public static Session getInstance(){
        if(instance == null){
            instance =  new Session();
            return instance;
        }else return instance;
    }

    /*
    * Method will go into SharedPreferences and try to find the stored email and password hash.
    * If it finds it it sets the email and md5Pass into Session.email, Session.md5Pass and sets the flag isLoggedIn to true
    * */
    public void getSPCredentials(Context c){
        /*
        SharedPreferences sp    =   getSharedPreferences("login", 0);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("Unm",Value );
        Ed.putString("Psw",Value);
        Ed.commit();
        */
        SharedPreferences sp1 = c.getSharedPreferences("Login", 0);

        String email    =   sp1.getString("email", null);
        String md5pass  =   sp1.getString("md5Pass", null);

    }

    /*
    * Simply returns current users email/pass as a string ?e=xxxx@xxx.com&p=xxxxxxxxx that is appended to the REST request URL
    * */
    public String asURLauth(){
        return "?e="+this.getEmail()+"&p="+this.getMd5Pass();
    }




    //SET GET
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

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
