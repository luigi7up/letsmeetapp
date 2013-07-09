package com.letsmeetapp.activities.calendar.availabilitycalendar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import com.letsmeetapp.activities.calendar.CalendarDayView;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Its an adapter used by AvailabilityCalendarActivity.calendarGridView. It returns CalendarDayViews for a
 * month that's beeing viewed. IF a month has 30 days and 3 dead days at the beginning it will return 33 days
 * where first 3 will be of type dead. The adapter receives an Event object and returns different CalendarDayViews
 * fo days that exist in object Event (meaning the initial days creator has selected when creating the event)
 */
public class AvailabilityCalendarAdapter extends BaseAdapter {

    private static final String TAG = AvailabilityCalendarAdapter.class.getName();

    private Context                     mContext;
    protected Event                     event;                         //used when seeing calendar for a Created event
    private ArrayList<CalendarDayView>  generatedDayViews;
    private ArrayList<Day>              eventDays;
    private Calendar                    currentMonth;


    private int dayViewDimension;

    public AvailabilityCalendarAdapter(Context c, Calendar currentMonth, ArrayList<Day> eventDays, Event event){
        this.mContext           = c;
        this.eventDays          = eventDays;
        this.event              = event;
        this.dayViewDimension   = calculateDayViewDimension();
        this.currentMonth       = currentMonth;
        generateDayViews(currentMonth);     //Maybe calling it from out of instance with a setter?

        Log.d(TAG,"Creating AvailabilityCalendarAdapter");
    }

    /*
    * This method has to create a new DayView for each day of a month that was passed in + some "dead views" if the day 1
    * of the month falls for example on Tuesday. It marks events that exist in passed Event object as event days.
    * @currentMonth is a Calendar instance set to one day (whichever) of a month we want to generate views for.
    * */
    public void generateDayViews(Calendar aDayInMonth){

        //Initialize the array to avoid nullPointer
        generatedDayViews = new ArrayList<CalendarDayView>();

        // Get the number of days in the month of aDayInMonth
        int numOfDays = aDayInMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Get a Calendar instance for a first day in month
        Calendar firstDayInMonth = new GregorianCalendar();
        firstDayInMonth.set(aDayInMonth.get(Calendar.YEAR), aDayInMonth.get(Calendar.MONTH), 1);  //1 is Sunday, 2 is Monday etc...

        Log.d(TAG, "First day falls on : " + firstDayInMonth.get(Calendar.DAY_OF_WEEK));
        int deadDaysToInject = firstDayInMonth.get(Calendar.DAY_OF_WEEK) - 2;


        //Inject first few dead days
        for(int i = 0; i<deadDaysToInject; i++){
            GregorianCalendar newDate = new GregorianCalendar();
            Day deadDay = new Day(newDate);
            CalendarDayView newDayCalendarDayView = new CalendarDayView(mContext, deadDay, dayViewDimension,true);
            this.generatedDayViews.add(newDayCalendarDayView);
        }

        //Add CalendarDayViews for the rest of the days in month
        for(int i=0; i < numOfDays; i++){
            GregorianCalendar newDate = new GregorianCalendar();
            newDate.set(newDate.MONTH, aDayInMonth.get(aDayInMonth.MONTH));    //Set the MONTH value of the newDate to the startingDay's MONTH
            newDate.set(newDate.DAY_OF_MONTH, i+1);                            //Set the DAY of newDate to 1,2,3,...31

            Log.d(TAG, "adapter, day added: "+newDate.getTime());

            Day newDay = new Day(newDate);
            CalendarDayView newDayCalendarDayView = new CalendarDayView(mContext, newDay, dayViewDimension);
            this.generatedDayViews.add(newDayCalendarDayView);
            /*
            * When user exits and comes back to CreateCalendar we have to mark as selected the views that contain days
            * selected previously.
            * */
            if(eventDays.contains(newDate)){
                newDayCalendarDayView.setSelected(true);
            }
        }
    }


    /*
    * Checks if the provided Day day is inside of event.getDays() provided
    * It simply compares the day (its dateAsString value) with each one in the Event object
    * @return indeicates whether the Day is in the event
    * */
    private boolean isInEvent(Day day, Event event){
        for(Day d:event.getDays()){
            if(day.getDateAsString().equalsIgnoreCase(d.getDateAsString())) return true;

        }
        return false;

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
}
