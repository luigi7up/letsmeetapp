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
import com.letsmeetapp.utilities.VisualUtility;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents the activity holding a calendar view for a month, the button to confirm the selection etc.
 */
public class CalendarActivity extends Activity implements OnClickListener{
    private GridView calendarGridView;
    private CalendarAdapter calendarAdapter;
    private ArrayList<Day> allSelectedDays = new ArrayList<Day>();
    private Calendar mCalendar;
    private Button prev,next;
    private TextView calendarHeaderMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view
        setContentView(R.layout.calendar_activity);

        //calendar instance
        mCalendar = Calendar.getInstance();

        //GEt the hold of the grid_element to assign it the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        calendarAdapter = new CalendarAdapter(this, mCalendar, allSelectedDays);
        calendarGridView.setAdapter(calendarAdapter);

        //Register handler fot onTouch event of calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnTouchListener(this));

        Button prev = (Button)findViewById(R.id.prevButton);
        Button next = (Button)findViewById(R.id.nextButton);

        //Set the current month
        calendarHeaderMonth = (TextView)findViewById(R.id.calendar_header_month);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(mCalendar.getTime());
        calendarHeaderMonth.setText(month_name);

        //TODO Put into separate Class
        prev.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH)-1);
                        mCalendar.add(Calendar.MONTH, -1);

                        //Reset the gridViewAdapter because now it contains days for another month
                        CalendarAdapter calendarAdapter = new CalendarAdapter(CalendarActivity.this, mCalendar, allSelectedDays);
                        calendarGridView.setAdapter(new CalendarAdapter(CalendarActivity.this, mCalendar, allSelectedDays));

                        //TODO optimize! don't create object. Make it memeber var
                        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                        String month_name = month_date.format(mCalendar.getTime());
                        calendarHeaderMonth.setText(month_name);

                    }
                }
        );
        next.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCalendar.add(Calendar.MONTH, 1);

                        CalendarAdapter calendarAdapter = new CalendarAdapter(CalendarActivity.this, mCalendar, allSelectedDays);
                        calendarGridView.setAdapter(new CalendarAdapter(CalendarActivity.this, mCalendar, allSelectedDays));

                        //TODO optimize! don't create object. Make it memeber var
                        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                        String month_name = month_date.format(mCalendar.getTime());
                        calendarHeaderMonth.setText(month_name);

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

}
