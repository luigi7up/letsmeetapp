package com.letsmeetapp.activities.calendar.availabilitycalendar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.*;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.rest.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Represents the activity holding a calendar view for a month, the button to confirm the selection etc.
 */
public class AvailabilityCalendarActivity extends CalendarActivity {

    private static final String TAG = AvailabilityCalendarActivity.class.getName();


    private Event event;                                      //used when seeing calendar for a Created event
    private CalendarDayView touchedDayView;                   //Reference to a touched view that a OnClick handler of popup can access
    private EventDaysUserAvailability eventDaysUserAvailability;    //Initialized with all days for event and logged user's availability

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.calendar_activity);

        //get all previously selected dates (when exiting and coming back...)
        //allSelectedDays = (ArrayList<Day>)getIntent().getExtras().get("allSelectedDays");

        //get all previously selected dates (when exiting and coming back...)
        event = (Event)getIntent().getExtras().get("event");
        //Initialize the object that holds availability for each day!
        eventDaysUserAvailability = new EventDaysUserAvailability(event);

        //Open the calendar showing the month of first day in the event
        if(monthShowing == null) monthShowing = event.getDays().get(0).getCurrentDate();

        //GEt the hold of the grid to assign it to the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);
        //if(event == null) calendarAdapter = new AvailabilityCalendarAdapter(this, monthShowing, this.allSelectedDays,this.event);
        //else calendarAdapter = new CalendarAdapter(this, monthShowing, this.allSelectedDays, this.event);

        CalendarAdapter ca = new AvailabilityCalendarAdapter(this, monthShowing,this.event);

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
                //returnIntent.putParcelableArrayListExtra("allSelectedDays", AvailabilityCalendarActivity.this.allSelectedDays);
                returnIntent.putExtra("event", AvailabilityCalendarActivity.this.event);

                Log.d(TAG, "I just set returnIntetn event to"+AvailabilityCalendarActivity.this.event);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }//onCreate

    @Override
    protected void dayViewTouched(CalendarDayView touchedDayView) {

        if(touchedDayView.isDead()) return;   //skip it

        if(touchedDayView.getDay().isInEvent() == true){

            this.touchedDayView = touchedDayView;
            //this.touchedDayView.toggleSelected();

            //touchedDayView.setBackgroundColor(android.R.color.holo_red_dark);
            //touchedDayView.setStyle(CalendarDayView.Style.NOT_SELECTED);
            showAvailabilityDialog();
        }
    }

    /*
    * Takes the context in which to show the dialog and a CalendarDayView on which it should perform the action of switching its Day availability to OK or NO
    * */
    private void showAvailabilityDialog(){
        Log.d(TAG, "Popup for day !"+this.touchedDayView.getDay().getDateAsString());

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.availability_popup);
        TextView titleTV = (TextView)dialog.findViewById(R.id.availability_popup_title);
        titleTV.setText("Are you available on "+this.touchedDayView.getDay().getDateAsString());
        dialog.show();
        //Get the reference to OK and NO buttons
        Button ok_button = (Button)dialog.findViewById(R.id.availability_btn_yes);
        Button no_button = (Button)dialog.findViewById(R.id.availability_btn_no);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked OK on " + AvailabilityCalendarActivity.this.touchedDayView.getDay().getDateAsString());
    /*
                            AvailabilityCalendarActivity.this.event.getEventDayByDateString(AvailabilityCalendarActivity.this.touchedDayView.getDay().getDateAsString()).setCurrentUserAvailability("y");
                //dayView1.getDayAvailabilityTextView().invalidate();
                AvailabilityCalendarActivity.this.touchedDayView.getDayAvailabilityTextView().setText("y");
                AvailabilityCalendarActivity.this.touchedDayView.setBackgroundColor(android.R.color.holo_gr                */
                AvailabilityCalendarActivity.this.touchedDayView.setAvailabilityText("y");


                //Log.d(TAG, "and it's set to " + calendarActivityContext.event.getEventDayByDateString(dayView1.getDay().getDateAsString()).getCurrentUserAvailability());
                dialog.dismiss();

            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked NO  "+AvailabilityCalendarActivity.this.touchedDayView.getDay().getDateAsString());

               /*
                AvailabilityCalendarActivity.this.event.getEventDayByDateString(AvailabilityCalendarActivity.this.touchedDayView.getDay().getDateAsString()).setCurrentUserAvailability("n");
                //Log.d(TAG, "and it's set to " + calendarActivityContext.event.getEventDayByDateString(dayView1.getDay().getDateAsString()).getCurrentUserAvailability());
                AvailabilityCalendarActivity.this.touchedDayView.getDayAvailabilityTextView().setText("n");
                AvailabilityCalendarActivity.this.touchedDayView.setBackgroundColor(android.R.color.holo_red_dark);
                */

                AvailabilityCalendarActivity.this.touchedDayView.setAvailabilityText("n");
                dialog.dismiss();
            }
        });




    }



    /*
    * Checks if a calendar can change month
    * */
    @Override
    public void resetCalendarAdapter(String direction){
        //TODO if user is trying to go to a month lower than the minimum event date or higher then the maximum do nosthing and show dialog
        Calendar minEventDay    = event.getDays().get(0).getCurrentDate();
        int minEventDaysYear    = minEventDay.get(Calendar.YEAR);
        int minEventDaysMonth   = minEventDay.get(Calendar.MONTH);

        Calendar maxEventDay    = event.getDays().get(event.getDays().size()-1).getCurrentDate();
        int maxEventDaysYear    = maxEventDay.get(Calendar.YEAR);
        int maxEventDaysMonth   = maxEventDay.get(Calendar.MONTH);

        int currentMonth    = getMonthShowing().get(Calendar.MONTH);
        int currentYear     = getMonthShowing().get(Calendar.YEAR);

        //User wants to go to the next month
        if(direction.equals("next")){
            if(currentYear <= maxEventDaysYear && currentMonth < maxEventDaysMonth) {
                this.getMonthShowing().add(Calendar.MONTH, +1);
            } else {
                //TODO show dialog...
                Toast.makeText(this.getApplicationContext(), "No days defined in following months", Toast.LENGTH_SHORT).show();
                return;      //don't continue
            }
        }else if(direction.equals("prev")){     //User wants to go to the prev
            if(currentYear >= minEventDaysYear && currentMonth > minEventDaysMonth) {
                getMonthShowing().add(Calendar.MONTH, -1);
            } else {
                //TODO show dia
                Toast.makeText(this.getApplicationContext(), "No days defined in previous months", Toast.LENGTH_SHORT).show();
                return;      //don't continue
            }
        }

        CalendarAdapter newCalendarAdapter = new AvailabilityCalendarAdapter(this, this.getMonthShowing(),this.getEvent());
        this.calendarGridView.setAdapter(newCalendarAdapter);

    }



    //GETTER / SETTER


    public EventDaysUserAvailability getEventDaysUserAvailability() {
        return eventDaysUserAvailability;
    }

    public void setEventDaysUserAvailability(EventDaysUserAvailability eventDaysUserAvailability) {
        this.eventDaysUserAvailability = eventDaysUserAvailability;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
