package com.letsmeetapp.rest;

import android.util.Log;
import com.letsmeetapp.model.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 5/06/13
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class RESTEventsParser implements Parsable{

    private static final String TAG = RESTEventsParser.class.getName();
    private RESTResponse mResource;
    private JSONArray eventsJsArray = null;

    public RESTEventsParser(){
    }

    @Override
    public ArrayList<Event> parse(RESTResponse resource) {

        ArrayList<Event> allEvents = new ArrayList<Event>();

        // getting JSONObject from the resource
        JSONObject jsObject = new JSONObject();
        try{
            jsObject = new JSONObject(resource.getData());
        }catch (JSONException e){
            Log.e(TAG, "Error parsing JSON",e);
        }

        try {
            // Getting Array of Contacts
            eventsJsArray = jsObject.getJSONArray("events");

            // looping through All Contacts
            for(int i = 0; i < eventsJsArray.length(); i++){
                JSONObject c = eventsJsArray.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString("id");
                String creatorId = c.getString("creatorId");
                String creatorName = c.getString("creatorName");
                String name = c.getString("name");
                String description = c.getString("description");
                String creationDate = c.getString("creationDate");

                Event event = new Event();
                event.setId(id);
                event.setCreatorId(creatorId);
                event.setCreatorEmail(creatorName);
                event.setName(name);

                allEvents.add(event);
                /*
                //initialDates is an array of dates....
                JSONArray initialDates = c.getJSONArray("events");
                ArrayList<String> initialDatesStrings = new  ArrayList<String>();
                for(int j = 0; j<initialDates.length(); j++){
                    initialDatesStrings.add(initialDates)

                }

                // Phone number is agin JSON Object
                JSONObject phone = c.getJSONObject(TAG_PHONE);
                String mobile = phone.getString(TAG_PHONE_MOBILE);
                String home = phone.getString(TAG_PHONE_HOME);
                String office = phone.getString(TAG_PHONE_OFFICE);
                */
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            //Always return allEvents even if empty...
            return allEvents;
        }

    }
}
