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
 * Represents the activity holding a calendar view for a month, the button to confirm the selection etc.
 */
public class CalendarActivity extends Activity implements OnClickListener{
    private GridView calendarGridView;
    private CalendarAdapter calendarAdapter;
    private ArrayList<Day> allSelectedDays;
    private Calendar monthToShow;
    private Button prev,next;

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

        Button prev = (Button)findViewById(R.id.prevButton);
        Button next = (Button)findViewById(R.id.nextButton);

        //TODO Put into separate files
        next.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendarAdapter = new CalendarAdapter(CalendarActivity.this, Calendar.FEBRUARY+1);
                        calendarGridView.setAdapter(calendarAdapter);
                    }
                }


        );
        prev.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendarAdapter = new CalendarAdapter(CalendarActivity.this, Calendar.FEBRUARY+2);
                        calendarGridView.setAdapter(calendarAdapter);
                    }
                }


        );



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
}
