package com.letsmeetapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a created event. It holds all information downloaded from the server about an event.
 *
 */
public class Event implements Parcelable{

    private String id_event;
    private String creator_email;
    private String creator_nickname;
    private String name;
    private String description;
    private ArrayList<Day> days;    //Days selected by creator
    private ArrayList<UserAvailability> invited_users;
    private Calendar created;                   //Days selected by creator


   // private String creatorUsername;
    //private String creatorEmail;

    public Event() {
    }

    public Event(String id_event, String name, String description,String creator_email,String creator_nickname,
                 ArrayList<UserAvailability> invited_users, ArrayList<Day> days,
                 Calendar created ) {

        this.id_event                = id_event;
        this.name                    = name;
        this.description             = description;
        this.creator_email           = creator_email;
        this.creator_nickname        = creator_nickname;
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(creator_email);
        dest.writeString(creator_nickname);
        dest.writeValue(days);              //ArrayList<Day>   implements Parcelable
        dest.writeValue(invited_users);    //ArrayList<String>
        dest.writeValue(created);          //Calendar
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            //Read serialized values
            String id_event                 = in.readString();
            String name         = in.readString();
            String description    = in.readString();
            String creator_email                    = in.readString();
            String creator_nickname                 = in.readString();
            ArrayList<Day> days                     = (ArrayList<Day>)in.readValue(getClass().getClassLoader());
            ArrayList<UserAvailability> invited_users    = (ArrayList<UserAvailability>)in.readValue(getClass().getClassLoader());
            Calendar created           = (Calendar)in.readValue(getClass().getClassLoader());


            //insert deserialized values
            Event event = new Event();

            event.setId_event(id_event);
            event.setName(name);
            event.setDescription(description);
            event.setCreator_email(creator_email);
            event.setCreator_nickname(creator_nickname);
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
     * Instead of just calling gson.toJson(newEvent); we use this method that manually serializes an event into JSON object to be sent to the Web Service
     * It is originally used in CreateEventActivity
     */
    public String asJSON(){
        //Gson gson = new Gson();
       //Gson gson = new Gson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id_event", this.getId_event());
        jsonObject.addProperty("name", this.getName());
        jsonObject.addProperty("description", this.getDescription());
        jsonObject.addProperty("creator_email", this.getCreator_email());
        //jsonObject.addProperty("created", this.getCreated().toString());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        ArrayList<String> daysAsStrings = new ArrayList<String>();
        for(Day d:days){
            String calendarString = sdf.format(d.getCurrentDate().getTime());
            daysAsStrings.add(calendarString);
        }
        JsonElement daysAsArray = gson.toJsonTree(daysAsStrings, new TypeToken<ArrayList<String>>() {}.getType());
        jsonObject.add("days",daysAsArray);


        JsonElement invitedUsersAsArray = gson.toJsonTree(this.getInvited_users(), new TypeToken<ArrayList<String>>() {}.getType());
        jsonObject.add("invited_users",invitedUsersAsArray);

        /*
        {
             "id_event": 38,
            "name": "First event ",
            "description": "Descibing it. 7 days, 3 ppl with creator included",
            "creator_email": "user1real@abc.com",
            "creator_nickname": "User1Nick",
        }
        */
        return jsonObject.toString();


    }

    /*
    * Event contains an ArrayList<Day> days; and this method returns a Day in this ArrayList if its dateToString is equal
    * to the passed one.
    * */
    public Day getEventDayByDateString(String dateAsString){
        for(Day d:days){
            if(d.getDateAsString().equalsIgnoreCase(dateAsString)) return d;
        }
        return null;
    }

   //SETTER / GETTER
    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public String getCreator_email() {
        return creator_email;
    }

    public void setCreator_email(String creator_email) {
        this.creator_email = creator_email;
    }

    public String getCreator_nickname() {
        return creator_nickname;
    }

    public void setCreator_nickname(String creator_nickname) {
        this.creator_nickname = creator_nickname;
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

    public ArrayList<UserAvailability> getInvited_users() {
        return invited_users;
    }

    public void setInvited_users(ArrayList<UserAvailability> invited_users) {
        this.invited_users = invited_users;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
