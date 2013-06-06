package com.letsmeetapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a created event. It holds all information downloaded from the server about an event.
 *
 */
public class Event implements Parcelable{

    private String id;
    private String name;
    private String creatorId;
    private String creatorEmail;
    private ArrayList<String> invitedPeopleEmails;
    private ArrayList<Day> initialEventDays;    //Days selected by creator
    private Calendar creationDate;                   //Days selected by creator

    public Event() {
    }
    public Event(String id, String name, String creatorId,
                 ArrayList<String> invitedPeopleEmails, ArrayList<Day> initialEventDays,
                 Calendar creationDate ) {
        this.id                     = id;
        this.name                   = name;
        this.creatorId              = creatorId;
        this.invitedPeopleEmails    = invitedPeopleEmails;
        this.initialEventDays       = initialEventDays;
        this.creationDate           = creationDate;
    }


    //PARCELABLE methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(creatorId);
        dest.writeString(creatorEmail);
        dest.writeValue(invitedPeopleEmails);    //ArrayList<String>
        dest.writeValue(initialEventDays);      //ArrayList<Day>   implements Parcelable
        dest.writeValue(creationDate);          //Calendar
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            //Read serialized values
            String id           = in.readString();
            String name         = in.readString();
            String creatorId    = in.readString();
            String creatorEmail    = in.readString();
            ArrayList<String> invitedPeopleEmails    = (ArrayList<String>)in.readValue(getClass().getClassLoader());
            ArrayList<Day> initialEventDays          = (ArrayList<Day>)in.readValue(getClass().getClassLoader());
            Calendar creationDate           = (Calendar)in.readValue(getClass().getClassLoader());
            //insert deserialized values
            Event event = new Event();
            event.setId(id);
            event.setName(name);
            event.setCreatorEmail(creatorEmail);
            event.setCreatorId(creatorId);
            event.setInvitedPeopleEmails(invitedPeopleEmails);
            event.setInitialEventDays(initialEventDays);
            event.setCreationDate(creationDate);

            return event;
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };








    //GETTER SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public ArrayList<String> getInvitedPeopleEmails() {
        return invitedPeopleEmails;
    }

    public void setInvitedPeopleEmails(ArrayList<String> invitedPeopleEmails) {
        this.invitedPeopleEmails = invitedPeopleEmails;
    }

    public ArrayList<Day> getInitialEventDays() {
        return initialEventDays;
    }

    public void setInitialEventDays(ArrayList<Day> initialEventDays) {
        this.initialEventDays = initialEventDays;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }
}
