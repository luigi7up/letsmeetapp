package com.letsmeetapp.activities.calendar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.letsmeetapp.R;

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
    private Button prevButton,nextButton;
    private TextView calendarHeaderMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.calendar_activity);

        //calendar instance. Returns the instance with getDate() returning now
        mCalendar = Calendar.getInstance();

        //GEt the hold of the grid_element to assign it the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        calendarAdapter = new CalendarAdapter(this, mCalendar, allSelectedDays);
        calendarGridView.setAdapter(calendarAdapter);

        //Register handler fot onTouch event over calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnClickListener(this));

        //Get the hold of buttons in the layout and set tags
        prevButton = (Button)findViewById(R.id.prevButton);
        prevButton.setTag("prevButton");
        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setTag("nextButton");

        //get hold of the month name in the header
        calendarHeaderMonth = (TextView)findViewById(R.id.calendar_header_month);

        //Inject the month name
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(mCalendar.getTime());
        calendarHeaderMonth.setText(month_name);

        prevButton.setOnClickListener(new CalendarChangeMonthOnClickListener(CalendarActivity.this));
        nextButton.setOnClickListener(new CalendarChangeMonthOnClickListener(CalendarActivity.this));

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

    public Calendar getmCalendar() {
        return mCalendar;
    }

    public void setmCalendar(Calendar mCalendar) {
        this.mCalendar = mCalendar;
    }

    public TextView getCalendarHeaderMonth() {
        return calendarHeaderMonth;
    }

    public void setCalendarHeaderMonth(TextView calendarHeaderMonth) {
        this.calendarHeaderMonth = calendarHeaderMonth;
    }
}
