package com.letsmeetapp.activities.calendar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.letsmeetapp.R;
import com.letsmeetapp.utilities.VisualUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This is the adapter for the calendarGridView. It returns one calendar_day view for each day in that particular month (getCount())
 * User: luka
 */
public class CalendarAdapter extends BaseAdapter {

    private Context mContext;
    private int dayViewDimension;   //value calculated as a screen_width/7 for the current orientation
    private ArrayList<Day> wholeMonth = new ArrayList<Day>();

    public CalendarAdapter(Context c, int month) {
        mContext = c;
        this.dayViewDimension = calculateDayViewDimension();
        repopulateMonth(month);
    }

    /**
     * It creates a new Day(calendar) object for each day in the month passed and adds it into the ArrayList<Day> wholeMonth
     * @param month is a initial month to be shown
     */
    private void repopulateMonth(int month){
        // Create a calendar object and set year and moth
        Calendar calendar = new GregorianCalendar(2013, month ,1);
        // Get the number of days in that month
        int daysInMonthNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
        for(int i=0; i < daysInMonthNumber; i++){
            Log.d("Luka","Adding a day "+i);
            calendar.set(Calendar.DAY_OF_MONTH, i+1);
            Day newDay = new Day(calendar.getTime());
            this.wholeMonth.add(newDay);
        }
    }

    /**
     * Returns a new new CalendarDayView for each item in ArrayList<Day> wholeMonth
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarDayView cdv;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            cdv = new CalendarDayView(mContext, wholeMonth.get(position), getDayViewDimension() );
        } else {
            cdv = (CalendarDayView) convertView;
            cdv.setDay(wholeMonth.get(position));
        }
        return cdv;
    }
    @Override
    public int getCount() {
        return wholeMonth.size();
    }
    @Override
    public Object getItem(int position) {
        return wholeMonth.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
    * Method returns the width of 7th part of a screen in order to pass it to a CalendarDayView to
    * set its width and height
    * @return 7th part of a calendar screen(in orientation)
    * */
    private int calculateDayViewDimension(){
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
}