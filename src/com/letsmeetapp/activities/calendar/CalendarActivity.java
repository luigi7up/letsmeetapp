package com.letsmeetapp.activities.calendar;

import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.letsmeetapp.R;

import java.util.*;

/**
 * Represents the activity holding a calendar view for a month.
 */
public class CalendarActivity extends Activity implements OnClickListener{
    private GridView calendarGridView;
    private CalendarAdapter calendarAdapter;
    private ArrayList<Day> allSelectedDays;
    private Calendar monthToShow;
    HashMap<Calendar, ArrayList<Day>> daysInEachMonth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view
        setContentView(R.layout.calendar_activity);

        //GEt the hold of the grid_element to assign it the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        calendarAdapter = new CalendarAdapter(this, Calendar.FEBRUARY);
        calendarGridView.setAdapter(calendarAdapter);

        //Register handler fot onTouch event of calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnTouchListener(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
	public void onClick(View v) {
		
		//Log.d("CalendarActivity", v.toString());
		
	}


    //GETTER / SETTERS


    public GridView getCalendarGridView() {
        return calendarGridView;
    }

    public void setCalendarGridView(GridView calendarGridView) {
        this.calendarGridView = calendarGridView;
    }

    public CalendarAdapter getCalendarAdapter() {
        return calendarAdapter;
    }

    public void setCalendarAdapter(CalendarAdapter calendarAdapter) {
        this.calendarAdapter = calendarAdapter;
    }

    public ArrayList<Day> getAllSelectedDays() {
        return allSelectedDays;
    }

    public void setAllSelectedDays(ArrayList<Day> allSelectedDays) {
        this.allSelectedDays = allSelectedDays;
    }

    public Calendar getMonthToShow() {
        return monthToShow;
    }

    public void setMonthToShow(Calendar monthToShow) {
        this.monthToShow = monthToShow;
    }

    public HashMap<Calendar, ArrayList<Day>> getDaysInEachMonth() {
        return daysInEachMonth;
    }

    public void setDaysInEachMonth(HashMap<Calendar, ArrayList<Day>> daysInEachMonth) {
        this.daysInEachMonth = daysInEachMonth;
    }
}
