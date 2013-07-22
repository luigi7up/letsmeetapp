package com.letsmeetapp;

/**
 * Holds all application level constants as static final variables. It returns different values depending on the value
 * of APP_DEV_STATE. If APP_DEV_STATE = "DEV" and you call for Constants.VAR_NAME it returns the value assigned in the static
 * block....
 * Cannot be instantiated!
 */
public class Constants {

    //Change this between DEBUG & PROD
    public static final String APP_DEV_STATE = "DEV";

    //Declare all constants
    public static final String REST_BASE_URL;



    //Initialize all declared constants giving them different values if they're for DEV or PROD mode
    static{

        if(APP_DEV_STATE.equals("DEV")){

            REST_BASE_URL = "http://10.6.22.66:1983/";        //my comp in Altran

            //REST_BASE_URL = "http://192.168.1.64:1983/";     //my comp at home
            //REST_BASE_URL = "http://letsmeetapp.herokuapp.com/";


        }else{  //for PROD
            REST_BASE_URL = "http://www.letsmeetapp.com/rest/";
        }
    }







    //Unable the instantiation!
    private Constants(){
        throw new AssertionError();
    }

}
