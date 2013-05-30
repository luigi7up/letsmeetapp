package com.letsmeetapp.activities.calendar;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Represents a Day in a calendar. It's holding info about the date, is it selected, people who also selected it etc.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        if (!date.equals(day.getDate())) return false;

        Log.d("Luka", "Day"+date+" AND "+day.getDate() +" are equal: ");

        return true;
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public String toString(){
        return "with date :"+date.toString();
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
