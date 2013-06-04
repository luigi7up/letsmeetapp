package com.letsmeetapp.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a created event. It holds all information downloaded from the server about an event.
 *
 */
public class Event {

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
