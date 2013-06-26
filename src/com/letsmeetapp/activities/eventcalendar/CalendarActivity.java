package com.letsmeetapp.activities.eventcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents the activity holding a eventcalendar view for a month, the button to confirm the selection etc.
 */
public class CalendarActivity extends Activity {

    private static final String TAG = CalendarActivity.class.getName();

    private GridView calendarGridView;
    private CalendarAdapter calendarAdapter;
    private ArrayList<Day> allSelectedDays;
    private Calendar startingDate;
    private Button prevButton,nextButton, doneSelectingButton;
    private TextView calendarHeaderMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.calendar_activity);

        //get all previously selected dates (when exiting and coming back...)
        this.allSelectedDays = (ArrayList<Day>)getIntent().getExtras().get("allSelectedDays");
        //Define the central month to show. When viewing details of an event, event object with creationDate is used
        startingDate = (Calendar)(((Event) getIntent().getExtras().get("event"))).getCreated();
        if(startingDate == null) startingDate = Calendar.getInstance();


        //GEt the hold of the grid to assign it to the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        calendarAdapter = new CalendarAdapter(this, startingDate, this.allSelectedDays);
        calendarGridView.setAdapter(calendarAdapter);

        //Register handler fot onTouch event over calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnClickListener(this));

        //Get the hold of buttons in the layout and set tags
        prevButton = (Button)findViewById(R.id.prevButton);
        prevButton.setTag("prevButton");
        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setTag("nextButton");
        prevButton.setOnClickListener(new CalendarChangeMonthOnClickListener(CalendarActivity.this));
        nextButton.setOnClickListener(new CalendarChangeMonthOnClickListener(CalendarActivity.this));

        //get hold of the month name in the header
        calendarHeaderMonth = (TextView)findViewById(R.id.calendar_header_month);
        //Inject the month name
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(startingDate.getTime());
        calendarHeaderMonth.setText(month_name);

        doneSelectingButton = (Button)findViewById(R.id.done_selecting_dates);

        //Done button returns allSelectedDays to calling activity
        doneSelectingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                Log.d("Luka", "getAllSelectedDays: "+CalendarActivity.this.getAllSelectedDays().toString());
                returnIntent.putParcelableArrayListExtra("allSelectedDays", CalendarActivity.this.allSelectedDays);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }//onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    public Calendar getStartingDate() {
        return startingDate;
    }

    public void setToday(Calendar startingDate) {
        this.startingDate = startingDate;
    }

    public TextView getCalendarHeaderMonth() {
        return calendarHeaderMonth;
    }

    public void setCalendarHeaderMonth(TextView calendarHeaderMonth) {
        this.calendarHeaderMonth = calendarHeaderMonth;
    }
}
