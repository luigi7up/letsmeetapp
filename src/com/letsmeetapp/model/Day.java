package com.letsmeetapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Represents a Day in a calendar. It's holding info about the date, is it selected, people who also selected it etc.
 * Implements Parcelable in order to be sent from one Activity to another
 */
public class Day implements Serializable, Comparable<Day>,Parcelable {

    private double IdDayOfEvent = -1;
    private HashMap<String, String> userAvailability = new HashMap<String, String>();    //it's a "email@asd.com":"y", "ccc@asd.com":"n" combination...

    private Calendar currentDate;
    private SimpleDateFormat ddMMyyyyFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private String dateAsString;
    private boolean isSelected;            //is it selected by the current user

    public Day(Calendar currentDate){
        super();
        this.currentDate = currentDate;
        //Extract from calendar object dd-MM-yyyy for comparison
        dateAsString = ddMMyyyyFormatter.format(currentDate.getTime());

    }

    //Returns just the number from the Date
    public int getDateDayNumber() {
        return currentDate.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        //Compare only the days. Ignore hours...
        Log.d("Luka", "Comparing 2 days: "+this.getDateAsString()+" and "+day.getDateAsString());

        if(this.getDateAsString().equalsIgnoreCase(day.getDateAsString())) return true;
        else return false;


    }


    @Override
    public int hashCode() {
        return currentDate.hashCode();
    }

    @Override
    public int compareTo(Day day) {
        //To Day objects are compared by comparing their dates...
        return this.currentDate.compareTo(day.getCurrentDate());
    }

    @Override
    public String toString(){
        return "Day.currentDate: "+ddMMyyyyFormatter.format(currentDate.getTime());
    }



    //To send an ArrayList<Day> from one activity to another your object needs to implement Parcelable...
    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(currentDate);
        //dest.writeValue(ddMMyyyyFormatter);
        dest.writeString(dateAsString);
        dest.writeValue(isSelected);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        public Day createFromParcel(Parcel in) {
            Calendar currentDate         = (Calendar)in.readValue(getClass().getClassLoader());
            //SimpleDateFormat ddMMyyyyFormatter   = (SimpleDateFormat)in.readValue(getClass().getClassLoader());
            String dateAsString         =  in.readString();
            boolean isSelected          =  (Boolean)in.readValue(getClass().getClassLoader());

            Day day = new Day(currentDate);
            day.setSelected(isSelected);
            day.setDateAsString(dateAsString);
            return day;
        }

        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    //Private constructor that is called pasing it Parcel object
    private Day(Parcel in) {
        //Demarshall it...
        currentDate         = (Calendar)in.readValue(getClass().getClassLoader());
        //ddMMyyyyFormatter   = (SimpleDateFormat)in.readValue(getClass().getClassLoader());
        dateAsString        =  in.readString();
        isSelected          =  (Boolean)in.readValue(getClass().getClassLoader());
    }


    //SETTER GETTER


    public Calendar getCurrentDate() {
        return currentDate;
    }

    public double getIdDayOfEvent() {
        return IdDayOfEvent;
    }

    public void setIdDayOfEvent(double idDayOfEvent) {
        IdDayOfEvent = idDayOfEvent;
    }

    public HashMap<String, String> getUserAvailability() {
        return userAvailability;
    }

    public void setUserAvailability(HashMap<String, String> userAvailability) {
        this.userAvailability = userAvailability;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }



    public void setDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDateAsString() {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }

    public SimpleDateFormat getDdMMyyyyFormatter() {
        return ddMMyyyyFormatter;
    }

    public void setDdMMyyyyFormatter(SimpleDateFormat ddMMyyyyFormatter) {
        this.ddMMyyyyFormatter = ddMMyyyyFormatter;
    }


}
