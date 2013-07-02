package com.letsmeetapp.activities.calendar;

import android.app.Activity;
import android.view.Menu;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.model.Day;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *  A base class for Activities that have a calendar inside (For CreateCalendarActivity or AvailabilityCalendarActivity)
 *  It offers some methods that are shared between all children classes.
 */
public class CalendarActivityBase extends Activity {

    protected GridView calendarGridView;
    protected BaseAdapter calendarAdapter;
    protected ArrayList<Day> allSelectedDays;     //a list of days that the creator initially selected
    protected Calendar startingDate;              //a Calendar instance that is used to determinate which month to present when Activity is started
    protected Button prevButton,nextButton, doneSelectingButton;
    protected TextView calendarHeaderMonth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //SETTER / GETTER
    public GridView getCalendarGridView() {
        return calendarGridView;
    }

    public void setCalendarGridView(GridView calendarGridView) {
        this.calendarGridView = calendarGridView;
    }

    public BaseAdapter getCalendarAdapter() {
        return calendarAdapter;
    }

    public void setCalendarAdapter(BaseAdapter calendarAdapter) {
        this.calendarAdapter = calendarAdapter;
    }

    public ArrayList<Day> getAllSelectedDays() {
        return allSelectedDays;
    }

    public void setAllSelectedDays(ArrayList<Day> allSelectedDays) {
        this.allSelectedDays = allSelectedDays;
    }

    public Calendar getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Calendar startingDate) {
        this.startingDate = startingDate;
    }

    public Button getPrevButton() {
        return prevButton;
    }

    public void setPrevButton(Button prevButton) {
        this.prevButton = prevButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public void setNextButton(Button nextButton) {
        this.nextButton = nextButton;
    }

    public Button getDoneSelectingButton() {
        return doneSelectingButton;
    }

    public void setDoneSelectingButton(Button doneSelectingButton) {
        this.doneSelectingButton = doneSelectingButton;
    }

    public TextView getCalendarHeaderMonth() {
        return calendarHeaderMonth;
    }

    public void setCalendarHeaderMonth(TextView calendarHeaderMonth) {
        this.calendarHeaderMonth = calendarHeaderMonth;
    }
}//end class



