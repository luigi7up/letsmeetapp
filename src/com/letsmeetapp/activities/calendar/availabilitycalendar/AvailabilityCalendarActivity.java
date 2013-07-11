package com.letsmeetapp.activities.calendar.availabilitycalendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.CalendarActivity;
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
public class AvailabilityCalendarActivity extends CalendarActivity {

    private static final String TAG = AvailabilityCalendarActivity.class.getName();

    private HashMap<Day, String> currentUserAvailability;     //Contains availability for each day user has clicked
    protected Event event;                         //used when seeing calendar for a Created event

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"onCreate AvailabilityCalendarActivity");

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.calendar_activity);

        //get all previously selected dates (when exiting and coming back...)
        allSelectedDays = (ArrayList<Day>)getIntent().getExtras().get("allSelectedDays");

        //get all previously selected dates (when exiting and coming back...)
        event = (Event)getIntent().getExtras().get("event");


        //Define the central month to show. When viewing details of an event, event object with creationDate is used
        // monthShowing = (Calendar)(((Event) getIntent().getExtras().get("event"))).getCreated();
        if(monthShowing == null) monthShowing = Calendar.getInstance();

        //GEt the hold of the grid to assign it to the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        //if(event == null) calendarAdapter = new AvailabilityCalendarAdapter(this, monthShowing, this.allSelectedDays,this.event);
        //else calendarAdapter = new CalendarAdapter(this, monthShowing, this.allSelectedDays, this.event);

        CalendarAdapter ca = new AvailabilityCalendarAdapter(this, monthShowing, this.allSelectedDays,this.event);

        calendarGridView.setAdapter(ca);

        //Register handler fot onTouch event over calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnClickListener(AvailabilityCalendarActivity.this));

        //Get the hold of buttons in the layout and set tags
        prevButton = (Button)findViewById(R.id.prevButton);
        prevButton.setTag("prevButton");
        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setTag("nextButton");
        prevButton.setOnClickListener(new CalendarChangeMonthOnClickListener(AvailabilityCalendarActivity.this));
        nextButton.setOnClickListener(new CalendarChangeMonthOnClickListener(AvailabilityCalendarActivity.this));

        //get hold of the month name in the header
        calendarHeaderMonth = (TextView)findViewById(R.id.calendar_header_month);
        //Inject the month name
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(monthShowing.getTime());
        calendarHeaderMonth.setText(month_name);

        doneSelectingButton = (Button)findViewById(R.id.done_selecting_dates);

        //Done button returns allSelectedDays to calling activity
        doneSelectingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                Log.d("Luka", "getAllSelectedDays: "+AvailabilityCalendarActivity.this.getAllSelectedDays().toString());
                returnIntent.putParcelableArrayListExtra("allSelectedDays", AvailabilityCalendarActivity.this.allSelectedDays);
                returnIntent.putExtra("event", AvailabilityCalendarActivity.this.event);

                Log.d(TAG, "I just set returnIntetn event to"+AvailabilityCalendarActivity.this.event);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }//onCreate

    /*
    * Creates its new adapter. It is used to generate new grids in cases when prev month next month is called from OnClickListener
    * */

     @Override
     public void resetCalendarAdapter(){
        CalendarAdapter newCalendarAdapter = new AvailabilityCalendarAdapter(this, this.getMonthShowing(),this.getAllSelectedDays(),this.getEvent());
        this.calendarGridView.setAdapter(newCalendarAdapter);
    }



    //GETTER / SETTER

    public HashMap<Day, String> getCurrentUserAvailability() {
        return currentUserAvailability;
    }

    public void setCurrentUserAvailability(HashMap<Day, String> currentUserAvailability) {
        this.currentUserAvailability = currentUserAvailability;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
