package com.letsmeetapp;

import android.app.Activity;

/**
 * BaseActivity simply extends the Activity to provide some  functions that are useful throughout the application
 */
public class BaseActivity extends Activity {

    /**
     * Inspired by JavaScript framework internationalization... It serves as a wrapper for the
     * String mystring = getResources().getString(R.string.mystring); call. So instead of writing this long line
     * we can write _(R.string.mystring) to get the string from the strings.xml
     * @return String that was found in the strings.xml for R.strings.XXX and for the current locale
     */
    public String _(int res){
        return getResources().getString(res);
    }

}
