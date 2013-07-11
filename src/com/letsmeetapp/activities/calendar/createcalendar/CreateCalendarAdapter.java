package com.letsmeetapp.activities.calendar.createcalendar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import com.letsmeetapp.activities.calendar.CalendarActivity;
import com.letsmeetapp.activities.calendar.CalendarAdapter;
import com.letsmeetapp.activities.calendar.CalendarDayView;
import com.letsmeetapp.model.Day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 8/07/13
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
public class CreateCalendarAdapter extends CalendarAdapter {

    private static final String TAG = CreateCalendarAdapter.class.getName();

    private ArrayList<CalendarDayView>generatedDayViews;
    private ArrayList<Day> eventDays;
    private Calendar currentMonth;

    private int dayViewDimension;

    public CreateCalendarAdapter(Context c, Calendar currentMonth, ArrayList<Day> eventDays){
        super(c);

        this.eventDays  = eventDays;
        this.dayViewDimension = calculateDayViewDimension();
        this.currentMonth = currentMonth;
        generateDayViews(currentMonth);     //Maybe calling it from out of instance with a setter?

        Log.d(TAG,"Creating AvailabilityCalendarAdapter");
    }

    /*
    * This method has to create a new DayView for each day of a month that was passed in + some "dead views" if the day 1
    * of the month falls for example on Tuesday.
    * @currentMonth is a Calendar instance set to one day (whichever) of a month we want to generate views for.
    * */
    public void generateDayViews(Calendar aDayInMonth){
        //TODO move the implementation of the method to the CalendarAdapter class and in child classses call necessary methods on already generated views...

        //Initialize the array to avoid nullPointer
        generatedDayViews = new ArrayList<CalendarDayView>();

        // Get the number of days in the month of aDayInMonth
        int numOfDays = aDayInMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Get a Calendar instance for a first day in month
        Calendar firstDayInMonth = new GregorianCalendar();
        firstDayInMonth.set(aDayInMonth.get(Calendar.YEAR), aDayInMonth.get(Calendar.MONTH), 1);  //1 is Sunday, 2 is Monday etc...

        //TODO THERE'S BUG WITH 1st of DECEMBER 2013 !!!
        Log.d(TAG, "First day falls on : " + firstDayInMonth.get(Calendar.DAY_OF_WEEK));
        int deadDaysToInject = firstDayInMonth.get(Calendar.DAY_OF_WEEK) - 2;


        //Inject first few dead days
        for(int i = 0; i<deadDaysToInject; i++){
            GregorianCalendar newDate = new GregorianCalendar();
            Day deadDay = new Day(newDate);
            CalendarDayView newDayCalendarDayView = new CalendarDayView(this.getmContext(), deadDay, dayViewDimension,true);
            this.generatedDayViews.add(newDayCalendarDayView);
        }

        //Add CalendarDayViews for the rest of the days in month
        for(int i=0; i < numOfDays; i++){
            GregorianCalendar newDate = new GregorianCalendar();
            newDate.set(newDate.MONTH, aDayInMonth.get(aDayInMonth.MONTH));    //Set the MONTH value of the newDate to the startingDay's MONTH
            newDate.set(newDate.DAY_OF_MONTH, i+1);                            //Set the DAY of newDate to 1,2,3,...31

            Log.d(TAG, "adapter, day added: "+newDate.getTime());

            Day newDay = new Day(newDate);                                      //create a new Day providing the data as a constructor arg
            CalendarDayView newDayCalendarDayView = new CalendarDayView(this.getmContext(), newDay, dayViewDimension);
            this.generatedDayViews.add(newDayCalendarDayView);
            /*
            * When user exits and comes back to CreateCalendar we have to mark as selected the views that contain days
            * selected previously.
            * */
            if(eventDays.contains(newDay)){
                newDayCalendarDayView.setDaySelected(true);
            }
        }
    }


    @Override
    public int getCount() {
        return generatedDayViews.size();
    }

    @Override
    public Object getItem(int position) {
        return generatedDayViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarDayView cdv = generatedDayViews.get(position);
        return cdv;
    }



    /*  SETTER/GETTER*/

    @Override
    public Context getmContext() {
        return super.getmContext();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setmContext(CalendarActivity mContext) {
        super.setmContext(mContext);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
