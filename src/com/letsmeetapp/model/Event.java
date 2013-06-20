package com.letsmeetapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a created event. It holds all information downloaded from the server about an event.
 *
 */
public class Event implements Parcelable{

    private String id_event;
    private int creator_id;
    private String name;
    private String description;
    private ArrayList<Day> days;    //Days selected by creator
    private ArrayList<String> invited_users;
    private Calendar created;                   //Days selected by creator


   // private String creatorUsername;
    //private String creatorEmail;

    public Event() {
    }
    public Event(String id_event, String name, String description,int creator_id,
                 ArrayList<String> invited_users, ArrayList<Day> days,
                 Calendar created ) {

        this.id_event                = id_event;
        this.creator_id              = creator_id;
        this.name                    = name;
        this.description             = description;
        this.days                    = days;
        this.invited_users           = invited_users;
        this.created                 = created;

    }


    //PARCELABLE methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_event);
        dest.writeInt(creator_id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeValue(days);              //ArrayList<Day>   implements Parcelable
        dest.writeValue(invited_users);    //ArrayList<String>
        dest.writeValue(created);          //Calendar
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            //Read serialized values
            String id_event                 = in.readString();
            int creator_id                  = in.readInt();
            String name         = in.readString();
            String description    = in.readString();
            ArrayList<Day> days             = (ArrayList<Day>)in.readValue(getClass().getClassLoader());
            ArrayList<String> invited_users    = (ArrayList<String>)in.readValue(getClass().getClassLoader());
            Calendar created           = (Calendar)in.readValue(getClass().getClassLoader());


            //insert deserialized values
            Event event = new Event();

            event.setId_event(id_event);
            event.setCreator_id(creator_id);
            event.setName(name);
            event.setDescription(description);
            event.setDays(days);
            event.setInvited_users(invited_users);
            event.setCreated(created);

            return event;
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };


    /**
     * Instead of just calling gson.toJson(newEvent); we use this method that manually serializes an event into JSON object
     * It is used in CreateEventActivity
     */
    public String asJSON(){
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("creator_id", this.getCreator_id());
        jsonObject.addProperty("name", this.getName());
        jsonObject.addProperty("description", this.getDescription());
        //jsonObject.addProperty("created", this.getCreated().toString());



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        ArrayList<String> daysAsStrings = new ArrayList<String>();
        for(Day d:days){
            String calendarString = sdf.format(d.getCurrentDate().getTime());
            daysAsStrings.add(calendarString);
        }
        jsonObject.addProperty("days", gson.toJson(daysAsStrings));

        jsonObject.addProperty("invited_users", gson.toJson(this.getInvited_users()));

        /*
        {
            "id_event" : 18,
                "creator_id" : 1,
                "name" : "First event",
                "description" : "Describing the first event here",
                "created" : "2013-06-14T19:25:12.000Z",
                "days" : ["2013-07-13T22:00:00.000Z", "2013-07-14T22:00:00.000Z", "2013-07-15T22:00:00.000Z"],
            "invited_users" : ["luigi", "mickey"]
        }
        */


        return gson.toJson("");


    }




    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public ArrayList<String> getInvited_users() {
        return invited_users;
    }

    public void setInvited_users(ArrayList<String> invited_users) {
        this.invited_users = invited_users;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
