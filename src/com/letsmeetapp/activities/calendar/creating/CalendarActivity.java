package com.letsmeetapp.activities.calendar.creating;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.CalendarActivityBase;
import com.letsmeetapp.activities.calendar.CalendarActivityOnClickListener;
import com.letsmeetapp.activities.calendar.CalendarAdapter;
import com.letsmeetapp.activities.calendar.CalendarChangeMonthOnClickListener;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Represents the activity holding a calendar view for a month, the button to confirm the selection etc.
 */
public class CalendarActivity extends CalendarActivityBase {

    private static final String TAG = CalendarActivity.class.getName();

    private HashMap<Day, String> currentUserAvailability;     //Contains availability for each day user has clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.calendar_activity);

        //get all previously selected dates (when exiting and coming back...)
        allSelectedDays = (ArrayList<Day>)getIntent().getExtras().get("allSelectedDays");

        //get all previously selected dates (when exiting and coming back...)
        event = (Event)getIntent().getExtras().get("event");


        //Define the central month to show. When viewing details of an event, event object with creationDate is used
        startingDate = (Calendar)(((Event) getIntent().getExtras().get("event"))).getCreated();
        if(startingDate == null) startingDate = Calendar.getInstance();


        //GEt the hold of the grid to assign it to the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        if(event == null) calendarAdapter = new CalendarAdapter(this, startingDate, this.allSelectedDays);
        else calendarAdapter = new CalendarAdapter(this, startingDate, this.allSelectedDays, this.event);


        calendarGridView.setAdapter(calendarAdapter);

        //Register handler fot onTouch event over calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnClickListener(CalendarActivity.this));

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
                returnIntent.putExtra("event", CalendarActivity.this.event);

                Log.d(TAG, "I just set returnIntetn event to"+CalendarActivity.this.event);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }//onCreate


    //GETTER / SETTER

    public HashMap<Day, String> getCurrentUserAvailability() {
        return currentUserAvailability;
    }

    public void setCurrentUserAvailability(HashMap<Day, String> currentUserAvailability) {
        this.currentUserAvailability = currentUserAvailability;
    }
}
