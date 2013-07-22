package com.letsmeetapp.activities.calendar.availabilitycalendar;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import com.letsmeetapp.activities.calendar.CalendarAdapter;
import com.letsmeetapp.activities.calendar.CalendarDayView;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.model.UserAvailability;
import com.letsmeetapp.rest.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Its an adapter used by AvailabilityCalendarActivity.calendarGridView. It returns CalendarDayViews for a
 * month that's beeing viewed. IF a month has 30 days and 3 dead days at the beginning it will return 33 days
 * where first 3 will be of type dead. The adapter receives an Event object and returns different CalendarDayViews
 * fo days that exist in object Event (meaning the initial days creator has selected when creating the event)
 */
public class AvailabilityCalendarAdapter extends CalendarAdapter {

    private static final String TAG = AvailabilityCalendarAdapter.class.getName();


    private Event                       event;                         //used when seeing calendar for a Created event
    private ArrayList<CalendarDayView>  generatedDayViews;
    private ArrayList<Day>              eventDays;
    private Calendar                    currentMonth;


    private int dayViewDimension;

    public AvailabilityCalendarAdapter(Context c, Calendar currentMonth, Event event){
        super(c);
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

            Day newDay = new Day(newDate);
            CalendarDayView newDayCalendarDayView = new CalendarDayView(this.getmContext(), newDay, dayViewDimension);

            if(isInEvent(newDay, event) ==  true){
                newDayCalendarDayView.getDay().setInEvent(true);
                newDayCalendarDayView.setStyle(CalendarDayView.Style.SELECTED);
                newDayCalendarDayView.setBehaviour(CalendarDayView.Behaviour.CLICKABLE);

                String dayAvailability = getCurrentUsersAvailabilityForADay(newDayCalendarDayView.getDay(), event, Session.getInstance().getEmail());
                newDayCalendarDayView.getDay().setCurrentUserAvailability(dayAvailability);

                //newDayCalendarDayView.getDay().setCurrentUserAvailability(dayAvailability);
                Log.d(TAG, "Day in event availability "+newDayCalendarDayView.getDay().getDateAsString()+" available: "+dayAvailability);


            }else{
                newDayCalendarDayView.getDay().setInEvent(false);
                newDayCalendarDayView.setStyle(CalendarDayView.Style.NOT_SELECTED);
                newDayCalendarDayView.setBehaviour(CalendarDayView.Behaviour.NOT_CLICKABLE);
            }

            this.generatedDayViews.add(newDayCalendarDayView);

        }
        Log.d(TAG, "generatedDayViews");

    }




    /*
    * Checks if the provided Day day is inside of event.getDays() provided
    * It simply compares the day (its dateAsString value) with each one in the Event object
    * @return indeicates whether the Day is in the event
    * */
    private boolean isInEvent(Day day, Event event){
        for(Day d:event.getDays()){
            if(day.getDateAsString().equalsIgnoreCase(d.getDateAsString())) {
                Log.d(TAG, "(TRUE) Comparing day "+day.getDateAsString()+" day "+d.getDateAsString()) ;
                return true;
            }else {
                Log.d(TAG, "(FALSE) Comparing day "+day.getDateAsString()+" day "+d.getDateAsString()) ;
            }
        }
        return false;

    }


    /*
    * Methods goes through day availability for a user inside the EVENT object that was created from parsing GET /events
    * */
     private String getCurrentUsersAvailabilityForADay(Day day, Event event, String userEmail){
        //Get the index of a day in the event.days array to be able to extract the availability for it in events.invited_usesrs.availability
        int dayIndexInEvent = 0;
        int counter =0;
        for(Day d:event.getDays()){
            if(d.equals(day)){
                dayIndexInEvent = counter;
            }
            counter++;
        }
         for(UserAvailability ua:event.getInvited_users()){
             if(ua.getUser_email()!=null && ua.getUser_email().equalsIgnoreCase(userEmail)){
                 return ua.getAvailability().get(dayIndexInEvent);
             }
         }

         Log.e(TAG, "Couldn't find the availability for "+userEmail+" in the day "+day+" for event "+event.getId_event());
         return "?";

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
