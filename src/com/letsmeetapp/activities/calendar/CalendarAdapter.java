package com.letsmeetapp.activities.calendar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.BaseAdapter;
import com.letsmeetapp.activities.calendar.creating.CalendarActivity;
import com.letsmeetapp.model.Day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This is the adapter for the calendarGridView. It returns one calendar_day view for each day in that particular month (getCount())
 * User: luka
 */
public class CalendarAdapter extends BaseAdapter {

    private static final String TAG = CalendarAdapter.class.getName();

    private Context mContext;
    private ArrayList<Day> allSelectedDays;     //returning to calendar with previously selected days
    private ArrayList<CalendarDayView> wholeMonthDayViews = new  ArrayList<CalendarDayView>();  //Holds the list of CalendarDayView
    private Calendar startingDate;
    private int dayViewDimension;   //value calculated as a screen_width/7 for the current orientation

    public CalendarAdapter(Context c, Calendar startingDate, ArrayList<Day> allSelectedDays) {

        this.mContext = c;
        this.startingDate = startingDate;
        this.allSelectedDays = allSelectedDays;
        this.dayViewDimension = calculateDayViewDimension();

        Log.d(TAG, "Creating new CalendarAdapter for month"+startingDate.get(Calendar.MONTH));

        generateDaysInMonth();
    }

    /**
     * It creates a new Day(calendar) object for each day in the month passed and adds it into the ArrayList<Day> wholeMonth
     */
    public void generateDaysInMonth(){
        // Get the number of days in that month
        int daysInMonthNumber = startingDate.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar firstDayInMonth = new GregorianCalendar();
        firstDayInMonth.set(startingDate.get(Calendar.YEAR), startingDate.get(Calendar.MONTH), 1);  //1 is Sunday, 2 is Monday etc...

        Log.d(TAG, "First day of month : "+firstDayInMonth.get(Calendar.DAY_OF_WEEK));
        int inactiveDaysToInject = firstDayInMonth.get(Calendar.DAY_OF_WEEK) - 2;

        //Inject first few dead days
        for(int i = 0; i<inactiveDaysToInject; i++){
            GregorianCalendar newDate = new GregorianCalendar();
            Day deadDay = new Day(newDate);
            CalendarDayView newDayCalendarDayView = new CalendarDayView(mContext, deadDay, getDayViewDimension(),true);
            this.wholeMonthDayViews.add(newDayCalendarDayView);
        }




       for(int i=0; i < daysInMonthNumber; i++){

           GregorianCalendar newDate = new GregorianCalendar();
           newDate.set(newDate.MONTH, startingDate.get(startingDate.MONTH));    //Set the MONTH value of the newDate to the startingDay's MONTH
           newDate.set(newDate.DAY_OF_MONTH, i+1);              //Set the DAY of newDate to 1,2,3,...31

            Log.d(TAG, "adapter, day added: "+newDate.getTime());

            Day newDay = new Day(newDate);

            if(allSelectedDays.contains(newDate)){
                newDay.setSelected(true);
            }

            CalendarDayView newDayCalendarDayView = new CalendarDayView(mContext, newDay, getDayViewDimension());
            this.wholeMonthDayViews.add(newDayCalendarDayView);

        }
        Log.d(TAG, "generateDaysInMonth" + wholeMonthDayViews);
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