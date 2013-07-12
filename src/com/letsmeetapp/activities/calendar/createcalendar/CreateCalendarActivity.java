package com.letsmeetapp.activities.calendar.createcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.*;
import com.letsmeetapp.activities.calendar.availabilitycalendar.AvailabilityCalendarAdapter;
import com.letsmeetapp.model.Day;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Activity holding a calendar for the creation of event. It shows a grid view with CreateCalendarAdapter
 */
public class CreateCalendarActivity extends CalendarActivity {

    private static final String TAG = CreateCalendarActivity.class.getName();

    private ArrayList<Day>allSelectedDays;  //days selected for the event

    private HashMap<Day, String> currentUserAvailability;     //Contains availability for each day user has clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"onCreate AvailabilityCalendarActivity");

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.calendar_activity);

        //get all previously selected dates (when exiting and coming back...)
        allSelectedDays = (ArrayList<Day>)getIntent().getExtras().get("allSelectedDays");

        //get all previously selected dates (when exiting and coming back...)
        //event = (Event)getIntent().getExtras().get("event");


        //Define the central month to show. When viewing details of an event, event object with creationDate is used
        // monthShowing = (Calendar)(((Event) getIntent().getExtras().get("event"))).getCreated();
        if(monthShowing == null) monthShowing = Calendar.getInstance();

        //GEt the hold of the grid to assign it to the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        //if(event == null) calendarAdapter = new CreateCalendarAdapter(this, monthShowing, this.allSelectedDays);
        //else calendarAdapter = new CalendarAdapter(this, monthShowing, this.allSelectedDays, this.event);

        CalendarAdapter ca = new CreateCalendarAdapter(this, monthShowing, this.allSelectedDays);
        calendarGridView.setAdapter(ca);

        //Register handler fot onTouch event over calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnClickListener(CreateCalendarActivity.this));

        //Get the hold of buttons in the layout and set tags
        prevButton = (Button)findViewById(R.id.prevButton);
        prevButton.setTag("prevButton");
        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setTag("nextButton");
        prevButton.setOnClickListener(new CalendarChangeMonthOnClickListener(CreateCalendarActivity.this));
        nextButton.setOnClickListener(new CalendarChangeMonthOnClickListener(CreateCalendarActivity.this));

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
                Log.d("Luka", "getAllSelectedDays: "+CreateCalendarActivity.this.getAllSelectedDays().toString());
                returnIntent.putParcelableArrayListExtra("allSelectedDays", CreateCalendarActivity.this.allSelectedDays);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }//onCreate

    @Override
    protected void dayViewTouched(CalendarDayView touchedDayView) {
        if(touchedDayView.isDead()) return;     //no action on deadViews...
        touchedDayView.toggleSelected();        //Select/Deselect

        if(this.getAllSelectedDays().contains(touchedDayView.getDay())) {
            this.getAllSelectedDays().remove(touchedDayView.getDay());
        }else{
            this.getAllSelectedDays().add(touchedDayView.getDay());
        }

    }

    @Override
    public void resetCalendarAdapter(String direction){
        //TODO if user is trying to go to a month lower than the minimum event date or higher then the maximum do nosthing and show dialog
        if(direction.equals("next")){
            this.getMonthShowing().add(Calendar.MONTH, +1);
        }else if(direction.equals("prev")){
            this.getMonthShowing().add(Calendar.MONTH, -1);
        }

        CalendarAdapter newCalendarAdapter = new CreateCalendarAdapter(this, this.getMonthShowing(),this.getAllSelectedDays());
        this.calendarGridView.setAdapter(newCalendarAdapter);
    }


    //GETTER / SETTER

    public HashMap<Day, String> getCurrentUserAvailability() {
        return currentUserAvailability;
    }
    public void setCurrentUserAvailability(HashMap<Day, String> currentUserAvailability) {
        this.currentUserAvailability = currentUserAvailability;
    }

    public ArrayList<Day> getAllSelectedDays() {
        return allSelectedDays;
    }

    public void setAllSelectedDays(ArrayList<Day> allSelectedDays) {
        this.allSelectedDays = allSelectedDays;
    }
}
