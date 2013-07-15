package com.letsmeetapp.rest;

import android.util.Log;
import com.google.gson.*;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.rest.parsers.Parser;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * It is used as the parser of EVENTS resource that the web service return. It implements the Parsable interface
 * with one method : parse()
 *
 */
public class RESTEventsParser {

    private static final String TAG = RESTEventsParser.class.getName();
    private RESTResponse mResource;

    public RESTEventsParser(){
    }

    //@Override
    public ArrayList<Event> parse(RESTResponse response) {

        //TODO test this and add necessary exception catchers!
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

            //"days": [ "2013-06-02T09:04:27.000Z", "2013-06-01T09:04:27.000Z" ]
            ArrayList<Day>days = new ArrayList <Day>();
            for(int x = 0;x<daysArray.size(); x++){
                days.add(new Day(stringToCalendar(daysArray.get(x).getAsString())));
            }
            //Sort the days now...
            Collections.sort(days);

            //"invited_users":[ {"email@asd.com":[ "y", "n" , "m"...]}, {"email@asd.com":[ y, y , y...]} ]
            JsonArray invitedUsersArray = jsonObject.get("invited_users").getAsJsonArray();
            ArrayList<String>invitedUsers = new ArrayList <String>();
            for(int x = 0;x<invitedUsersArray.size(); x++){

                //e = "luis.perez2@gmail.com": ["y","y","y","y"]
                JsonObject e = invitedUsersArray.get(x).getAsJsonObject();
                for (Map.Entry<String,JsonElement> entry : e.entrySet()){       //entrySet() returns a map of key/values. We extract the key name that represents the email

                    String email = entry.getKey();      //email
                    JsonArray userDaysAvailability = entry.getValue().getAsJsonArray();     //values [y, n, y, m];

                    for(int q = 0; q<userDaysAvailability.size(); q++){
                        days.get(q).getUserAvailability().put(email, userDaysAvailability.get(q).getAsString());
                    }
                    invitedUsers.add(email);
                }
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            cal.setTime(sdf.parse(input));// all done
        } catch (ParseException e) {
            Log.e(TAG, "Provided string could not be parsed to Calendar: "+input);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return cal;
    }


}
