package com.letsmeetapp.rest.parsers.events;

import android.util.Log;
import com.google.gson.*;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.model.UserAvailability;
import com.letsmeetapp.rest.RESTResponse;
import com.letsmeetapp.rest.parsers.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
*   Parses the JSON returned after calling GET /events
 */
public class GetEventsParser implements Parser{

    private static final String TAG = GetEventsParser.class.getName();
    private RESTResponse mResource;
    private ArrayList<Event> allEvents;

    public GetEventsParser(){}

    @Override
    public void parse(RESTResponse response){

        //TODO test this and add necessary exception catchers!
        allEvents = new ArrayList<Event>();
        JsonParser parser   = new JsonParser();
        JsonArray jsonArray = parser.parse(response.getData()).getAsJsonArray();


        for(int i = 0;i<jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            String id_event         = jsonObject.get("id_event").getAsString();
            String name             = jsonObject.get("name").getAsString();
            String description      = jsonObject.get("description").getAsString();
            String creator_email    = jsonObject.get("creator_email").getAsString();
            String creator_nickname = jsonObject.get("creator_nickname").getAsString();
            String created          = jsonObject.get("created").getAsString();

            //DAYS
            JsonArray daysArray     = jsonObject.get("days").getAsJsonArray();
            //"days": [ "2013-06-02T09:04:27.000Z", "2013-06-01T09:04:27.000Z" ]
            ArrayList<Day>days = new ArrayList <Day>();
            for(int x = 0;x<daysArray.size(); x++){
                days.add(new Day(stringToCalendar(daysArray.get(x).getAsString())));
            }

            /* USERS  and their availability
            //JSON object format
                "invited_users": {
                    "email_invitation": "user6invitedTo38@aaa.com",
                    "user_email": "user6real@abc.com",
                    "availability": [ "n", "n", null, null, "n", "y",null]
                }
                */
            JsonArray invitedUsersArray = jsonObject.get("invited_users").getAsJsonArray();
            ArrayList<UserAvailability>allUsersAvailability = new ArrayList <UserAvailability>();
            for(int x = 0;x<invitedUsersArray.size(); x++){

                JsonObject e = invitedUsersArray.get(x).getAsJsonObject();
                UserAvailability userAvailability = new UserAvailability();

                if(!e.get("email_invitation").isJsonNull()) userAvailability.setEmail_invitation(e.get("email_invitation").getAsString());
                if(!e.get("user_email").isJsonNull()) userAvailability.setUser_email(e.get("user_email").getAsString());

                //availability is an array
                JsonArray userAvailabilityArray = e.get("availability").getAsJsonArray();
                ArrayList<String> dayAvailability = new ArrayList<String>();
                for (int y =0; y<userAvailabilityArray.size(); y++) {
                    dayAvailability.add(userAvailabilityArray.get(y).getAsString());
                }
                userAvailability.setAvailability(dayAvailability);

                allUsersAvailability.add(userAvailability);

            }

            Event event = new Event();
            event.setId_event(id_event);
            event.setName(name);
            event.setDescription(description);
            event.setCreator_email(creator_email);
            event.setCreator_nickname(creator_nickname);
            event.setCreated(stringToCalendar(created));
            event.setDays(days);
            event.setInvited_users(allUsersAvailability);

            allEvents.add(event);

        }

    }

    @Override
    public ArrayList<Event> getParsedResult() {
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
            Log.e(TAG, "Provided string could not be parsed to Calendar: " + input);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return cal;
    }

}
