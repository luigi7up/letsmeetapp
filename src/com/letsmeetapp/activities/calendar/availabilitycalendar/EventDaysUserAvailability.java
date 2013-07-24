package com.letsmeetapp.activities.calendar.availabilitycalendar;

import android.util.Log;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.model.UserAvailability;
import com.letsmeetapp.rest.Session;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used inside AvailabilityCalendarActivity to keep data about all Days in the current event user is viewing
 * and all the availability decision user has done over it...
 * Since the Event object comes with a ArrayList of days and then and object called UserAvailability with arrayList of strings
 * like [y,n,n,y...] with size corresponding to days.size() we need to swithch to Day:availability structure
 */
public class EventDaysUserAvailability {

    private static final String TAG = AvailabilityCalendarActivity.class.getName();

    private HashMap<Day, String> availabilityForDays;
    private ArrayList<Day> days;            //Days extracted from passed event object
    private boolean hasChanged;

    public EventDaysUserAvailability(Event event){
        Log.d(TAG, "Creating EventDaysUserAvailability");
        this.days = event.getDays();

        //asign all ["y","n","m"] to each day respectively
        for(UserAvailability ua: event.getInvited_users()){
            if(ua.getUser_email().equalsIgnoreCase(Session.getInstance().getEmail())){
                for(int i = 0; i<event.getDays().size();i++){
                    availabilityForDays.put(event.getDays().get(i), ua.getAvailability().get(i));
                }
            }
        }

    }

    /*
    * All days of event and current users availability are in HashMap<Day, String> availabilityForAday. this method
    * searches that hash map for the day passed and sets the availability to the new sring passed and changes the this.changed = true
    * so tha the Activity knows that new REST update has to be issued...
    * @d Day for which to change the availability
    * @availability new availability string (y,n)
    * */
    public void changeAvailabilityForDay(Day d, String availability){
        //availabilityForDays.get(d) = "";
        availabilityForDays.put(d, availability);
    }

    public String getAvailabilityForDay(Day d){
        return availabilityForDays.get(d);
    }


}
