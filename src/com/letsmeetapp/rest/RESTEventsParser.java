package com.letsmeetapp.rest;

import android.util.Log;
import com.google.gson.*;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * It is used as the parser of EVENTS resource that the web service return. It implements the Parsable interface
 * with one method : parse()
 *
 */
public class RESTEventsParser implements Parsable{

    private static final String TAG = RESTEventsParser.class.getName();
    private RESTResponse mResource;

    public RESTEventsParser(){
    }

    @Override
    public ArrayList<Event> parse(RESTResponse response) {

        ArrayList<Event> allEvents = new ArrayList<Event>();
        JsonParser parser   = new JsonParser();
        JsonArray jsonArray = parser.parse(response.getData()).getAsJsonArray();


        for(int i = 0;i<jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            String id_event = jsonObject.get("id_event").getAsString();
            int id_creator = jsonObject.get("id_creator").getAsInt();
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            String created = jsonObject.get("created").getAsString();

            JsonArray daysArray = jsonObject.get("days").getAsJsonArray();

            ArrayList<Day>days = new ArrayList <Day>();
            for(int x = 0;x<daysArray.size(); x++){
                days.add(new Day(stringToCalendar(daysArray.get(x).getAsString())));
            }

            JsonArray invitedUsersArray = jsonObject.get("invited_users").getAsJsonArray();
            ArrayList<String>invitedUsers = new ArrayList <String>();
            for(int x = 0;x<invitedUsersArray.size(); x++){
                invitedUsers.add(invitedUsersArray.get(x).getAsString());
            }

            Event event = new Event();
            event.setId_event(id_event);
            event.setId_creator(id_creator);
            event.setName(name);
            event.setDescription(description);
            event.setCreated(stringToCalendar(created));
            event.setDays(days);
            event.setInvited_users(invitedUsers);

            allEvents.add(event);

        }

        return allEvents;


    }
    /**
     * Parses a date string that node gets from mysql to Calendar object
     */
    private Calendar stringToCalendar(String input){
        Calendar cal = Calendar.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            cal.setTime(sdf.parse(input));// all done
        } catch (ParseException e) {
            Log.e(TAG, "Provided string could not be parsed to Calendar: "+input);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return cal;
    }


}
