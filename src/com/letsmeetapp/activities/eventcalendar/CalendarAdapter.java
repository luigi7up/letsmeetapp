package com.letsmeetapp.activities.eventcalendar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.BaseAdapter;
import com.letsmeetapp.model.Day;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This is the adapter for the calendarGridView. It returns one calendar_day view for each day in that particular month (getCount())
 * User: luka
 */
public class CalendarAdapter extends BaseAdapter {

    private CalendarActivity mContext;
    private int dayViewDimension;   //value calculated as a screen_width/7 for the current orientation
    private ArrayList<CalendarDayView> wholeMonthDayViews = new  ArrayList<CalendarDayView>();
    private Calendar today;
    private ArrayList<Day> allSelectedDays;

    public CalendarAdapter(CalendarActivity c, Calendar today, ArrayList<Day> allSelectedDays) {

        this.mContext = c;
        this.today = today;
        this.allSelectedDays = allSelectedDays;
        this.dayViewDimension = calculateDayViewDimension();

        Log.d("Luka", "Creating new CalendarAdapter for month"+today.get(Calendar.MONTH));

        generateDaysInMonth();
    }

    /**
     * It creates a new Day(eventcalendar) object for each day in the month passed and adds it into the ArrayList<Day> wholeMonth
     */
    public void generateDaysInMonth(){
        // Get the number of days in that month
        int daysInMonthNumber = today.getActualMaximum(Calendar.DAY_OF_MONTH);

        //add all Days into wholeMonth ArrayList that is returned in the method getCount() and getView()
        for(int i=0; i < daysInMonthNumber; i++){
            today.set(Calendar.DAY_OF_MONTH, i+1);
            Day newDay = new Day(today);

            if(allSelectedDays.contains(newDay)){
                newDay.setSelected(true);
                Log.d("Luka", "allSelectedDays.contains: "+newDay);
            }

            CalendarDayView newDayCalendarDayView = new CalendarDayView(mContext, newDay, getDayViewDimension());
            this.wholeMonthDayViews.add(newDayCalendarDayView);

        }
        Log.d("Luka", "generateDaysInMonth" + wholeMonthDayViews);
    }

    /**
     * Returns a new new CalendarDayView for each item in ArrayList<Day> wholeMonth
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        CalendarDayView cdv = wholeMonthDayViews.get(position);

        return cdv;
        /*
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            cdv = new CalendarDayView(mContext, dayForPosition, getDayViewDimension() );
        } else {
            cdv = (CalendarDayView) convertView;
            cdv.setDay(dayForPosition);
        }
        return cdv;
        */
    }
    @Override
    public int getCount() {
        return wholeMonthDayViews.size();
    }
    @Override
    public Object getItem(int position) {
        return wholeMonthDayViews.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
    * Method returns the width of 7th part of a screen in order to pass it to a CalendarDayView to
    * set its width and height
    * @return 7th part of a eventcalendar screen(in orientation)
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

        //get the column width on this device to set the eventcalendar day height.
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

    public Calendar getToday() {
        return today;
    }

    public void setToday(Calendar today) {
        this.today = today;
    }

    public CalendarActivity getmContext() {
        return mContext;
    }

    public void setmContext(CalendarActivity mContext) {
        this.mContext = mContext;
    }
}