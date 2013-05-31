package com.letsmeetapp;

import java.util.ArrayList;

/**
 * Holds all application level constants. Cannot be instantiated!
 */
public class Constants {



    //Change this between DEBUG & PROD
    public static final String APP_DEV_STATE = "DEV";



    //Declare all constants
    public static final String REST_BASE_URL;



    //Initialize all declared constants giving them different values if they're for DEV or PROD mode
    static{

        if(APP_DEV_STATE == "DEV"){

            REST_BASE_URL = "http://localhost:8080/letsmeetapp/rest/";
        }else{  //for PROD

            REST_BASE_URL = "http://www.letsmeetapp.com/rest/";
        }


    }







    //Unable the instantiation!
    private Constants(){
        throw new AssertionError();
    }

}
