package com.letsmeetapp.activities.calendar;

import android.R;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.BaseAdapter;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Base class for all adapters of all types of calendars...
 */
public abstract class CalendarAdapter extends BaseAdapter {

    private static final String TAG = CalendarAdapter.class.getName();
    private Context mContext;
    protected ArrayList<Day> allSelectedDays;     //returning to calendar with previously selected days
    protected ArrayList<CalendarDayView> wholeMonthDayViews = new  ArrayList<CalendarDayView>();  //Holds the list of CalendarDayView
    protected Calendar startingDate;
    protected int dayViewDimension;   //value calculated as a screen_width/7 for the current orientation

    public CalendarAdapter(){}
    public CalendarAdapter(Context c){
        mContext = c;
    }

    public abstract void generateDayViews(Calendar aDayInMonth);

    /**
    * Method returns the width of 7th part of a screen in order to pass it to a CalendarDayView to
    * set its width and height
    * @return 7th part of a calendar screen(in orientation)
    * */
    protected int calculateDayViewDimension(){
        // get display metrics
        final DisplayMetrics metrics = new DisplayMetrics();

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int orientation_width_dp = display.getWidth();  // deprecated
        int orientation_height_dp = display.getHeight();  // deprecated

        Log.d("Luka","Calculating dayViewDimension"+orientation_width_dp/7);

        return  orientation_width_dp/7;

        //get the column width on this device to set the calendar day height.
        //NOTE android.R.id.content gives you the root element of a view, without having to know its actual name/type/ID.
        //this.orientation_width_dp = VisualUtility.dpFromPxForScreen(width, display.findViewById(android.R.id.content));
        //this.orientation_height_dp = VisualUtility.dpFromPxForScreen(height, this.findViewById(android.R.id.content));

    }


    //GETTER SETTER
    public int getDayViewDimension() {
        return dayViewDimension;
    }
    public void setDayViewDimension(int dayViewDimension) {
        this.dayViewDimension = dayViewDimension;
    }

    public Calendar getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Calendar startingDate) {
        this.startingDate = startingDate;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(CalendarActivity mContext) {
        this.mContext = mContext;
    }
}