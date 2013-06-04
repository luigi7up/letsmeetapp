package com.letsmeetapp.utilities;

import android.util.Log;
import android.view.View;
import com.letsmeetapp.model.Day;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Holds utility methods for the application used by many Activities and their view
 */
public class VisualUtility {


    /**
     * Converts the passed ArrayList of dates into a Locale string explaining that provided dates belong to a certain period
     * For example:
     *  if we pass it 4 Day objects where 2 are in May and 2 in June it will return MAY, JUNE
     *  if we pass it 4 Day objects where 2 are in May, 1 in June and 1 in AUG it will return MAY - AUG
     *  ...
     */
    static public String periodForSelectedDates(ArrayList<Day> days){

        //Extract all MONTHS for period
        TreeSet<String> monthsNames = new TreeSet<String>();
        //Inject the month name
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        String month_name = "";
        for(Day d:days){
            try{
                month_name = simpleDateFormat.format(d.getCurrentDate().getTime());
                //month_name = simpleDateFormat.format(d.getCurrentDate().getTime());
                monthsNames.add(month_name);
            } catch(Exception e){
                Log.e("Luka","simpleDateFormat(MMM) failed for "+d.getCurrentDate().getTime());
                break;
            }
        }

        if(monthsNames.size() == 1){
            return monthsNames.first();
        }else{
            return monthsNames.last()+" - "+monthsNames.first();
        }

    }






    static public float dpFromPxForScreen(int px, View currentView){

        return px / currentView.getContext().getResources().getDisplayMetrics().density;
    }
    static public float pxFromDpForScreen(int dp, View currentView)
    {
        return dp * currentView.getContext().getResources().getDisplayMetrics().density;
    }
}
