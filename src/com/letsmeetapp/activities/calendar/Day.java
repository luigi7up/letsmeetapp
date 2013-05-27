package com.letsmeetapp.activities.calendar;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Represents a Day in a calendar. It's holding info about the date, is it selected, people who also selected it etc.
 * User: luka
 * Date: 25.05.13.
 * Time: 14:01
 */
public class Day {

    private Date date;
    private boolean isSelected;
    private int percentageOfAccordance;
    private HashMap<String, Boolean> membersDecisions;


    public Day(Date date){
        super();
        this.date = date;
        Log.d("Luka", "Creating the day " + date);
    }

    //Returns just the number from the Date
    public int getDateDayNumber() {
        return date.getDate();

    }

    //Toggles the value selected from true to false
    public void toggleSelected(){
        if(this.isSelected()) {
            this.setSelected(false);
        }
        else {
            this.setSelected(true);
        }
    }

    //SETTER GETTER
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPercentageOfAccordance() {
        return percentageOfAccordance;
    }

    public void setPercentageOfAccordance(int percentageOfAccordance) {
        this.percentageOfAccordance = percentageOfAccordance;
    }

    public HashMap<String, Boolean> getMembersDecisions() {
        return membersDecisions;
    }

    public void setMembersDecisions(HashMap<String, Boolean> membersDecisions) {
        this.membersDecisions = membersDecisions;
    }



}
