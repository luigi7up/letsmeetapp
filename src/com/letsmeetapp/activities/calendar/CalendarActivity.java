package com.letsmeetapp.activities.calendar;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.availabilitycalendar.AvailabilityCalendarAdapter;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *  A base class for Activities that have a calendar grid inside (For CreatingCalendarActivity or AvailabilityCalendarActivity)
 *  It offers some methods that are shared between all children classes.
 */
public abstract class CalendarActivity extends FragmentActivity {

    protected GridView calendarGridView;
    //protected ArrayList<Day> allSelectedDays;     //a list of days that the creator initially selected
    protected Calendar monthShowing;              //a Calendar instance that is used to determinate which month to present when Activity is started
    protected Button prevButton,nextButton, doneSelectingButton;
    protected TextView calendarHeaderMonth;

    /*
    * Each class extending CalendarActivity will offer its implementation of this method. That way we can have a
    * shared CalendarActivityOnClickListener simply passing the logic to the Activity itself...
    * */
    protected abstract void dayViewTouched(CalendarDayView touchedDayView);   //Touches received in CalendarActivityOnClickListener will be passed back to concerete CalendarActivityX implementation to decide what it means

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /**
     * All Calendar Activities must implment this method that can be called from CalendarChangeMonthOnClickLister to avoid.
     * When clicking previous or next month new adapter for this object has to be created. Its better to invert the contro so that
     * the object itself creates its adapter
     */
    public abstract void resetCalendarAdapter(String direction);



    //SETTER / GETTER
    public GridView getCalendarGridView() {
        return calendarGridView;
    }

    public void setCalendarGridView(GridView calendarGridView) {
        this.calendarGridView = calendarGridView;
    }

    public Calendar getMonthShowing() {
        return this.monthShowing;
    }

    public void setMonthShowing(Calendar monthShowing) {
        this.monthShowing = monthShowing;
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



