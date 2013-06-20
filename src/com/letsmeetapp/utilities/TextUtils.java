package com.letsmeetapp.utilities;

import android.content.Context;
import com.letsmeetapp.R;

/**
 * Contains various methods that help edit text.
 */
public class TextUtils {
    /**
     *
     * @param input is the text we want to manipulate
     * @param maxChars it's the number of letters to which we want to cut the text
     * @return returns the shortened string
     */
    public static String shorten(String input, int maxChars){
        int length = input.length();

        //Return the same string if it's the same size or shorter tham the maxChars
        if(length == maxChars  || length < maxChars ) return input;

        String result = input.substring(0,maxChars)+"...";
        return result;
    }

    /**
     *
     * @param input is the text we want to manipulate
     * @param length it's the number of letters to which we want to cut the text
     * @param suffix it's the ending of the string such as ...
     * @return returns the shortened string ending with the suffix provided
     */
    public static String shorten(String input, int length, String suffix){
        String result = input+"...";
        return result;
    }

    /**
     * MEthod returns number followed by a word day or days depending if the input is 1 or more
     * @param c It's the context of the activity where this method was called from. Context is needed to use getResources in the  _() method
     * @param input number of days
     * @return It returns, for example 1 day or 5 days ....
     */
    public static String numOfDays(Context c, int input){

        String result;
        if(input == 1){
            result = input+" "+_(c, R.string.day);

        }else{
            result = input+" "+_(c, R.string.days);

        }
        return  result;

    }


    public static String numOfPeople (Context c, int input){
        String result;
        if(input == 1){
            result = input+" "+_(c, R.string.person);

        }else{
            result = input+" "+_(c, R.string.people);

        }
        return  result;
    }


    /**
     * Returnt the translation for the string representde by int R.string.XXXX
     * @param c it's the context of activity that is trying to translate it. It's needed because getResource is a method inherited from Context
     * @param res is the int representing R.string.<string id>
     * @return String translation in locale
     */
    public static String _(Context c, int res){
        return c.getResources().getString(res);
    }
}
