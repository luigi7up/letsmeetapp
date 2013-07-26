package com.letsmeetapp.activities.calendar.availabilitycalendar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.letsmeetapp.Constants;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.allevents.AllEventsListActivity;
import com.letsmeetapp.activities.calendar.*;
import com.letsmeetapp.customviews.CustomProgressSpinner;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.rest.HTTPVerb;
import com.letsmeetapp.rest.RESTLoader;
import com.letsmeetapp.rest.RESTResponse;
import com.letsmeetapp.rest.Session;
import com.letsmeetapp.utilities.NetUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Represents the activity holding a calendar view for a month, the button to confirm the selection etc.
 */
public class AvailabilityCalendarActivity extends CalendarActivity
        implements LoaderManager.LoaderCallbacks<RESTResponse>{

    private static final String TAG = AvailabilityCalendarActivity.class.getName();


    private Event event;                                      //used when seeing calendar for a Created event
    private CalendarDayView touchedDayView;                   //Reference to a touched view that a OnClick handler of popup can access
    private EventDaysUserAvailability eventDaysUserAvailability;    //Initialized with all days for event and logged user's availability

    private LoaderManager loaderManager;        //needed for the RESTLoader that will send POST /events
    private RESTResponse mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.calendar_activity);

        //get all previously selected dates (when exiting and coming back...)
        //allSelectedDays = (ArrayList<Day>)getIntent().getExtras().get("allSelectedDays");

        //get all previously selected dates (when exiting and coming back...)
        event = (Event)getIntent().getExtras().get("event");

        //This object takes the event as parameter and server as a holder of mapping for Day:availability
        eventDaysUserAvailability = new EventDaysUserAvailability(event);

        //Open the calendar showing the month of first day in the event
        if(monthShowing == null) monthShowing = event.getDays().get(0).getCurrentDate();

        //GEt the hold of the grid to assign it to the adapter
        calendarGridView = (GridView)findViewById(R.id.calendar_grid_view);

        //Create adapter passing the default month to show and Event object
        CalendarAdapter ca = new AvailabilityCalendarAdapter(this, monthShowing,this.event);

        //Assign the adapter to the grid
        calendarGridView.setAdapter(ca);

        //Register handler fot onTouch event over calendarGridView
        calendarGridView.setOnTouchListener(new CalendarActivityOnClickListener(AvailabilityCalendarActivity.this));

        //Get the hold of buttons in the layout and set tags
        prevButton = (Button)findViewById(R.id.prevButton);
        prevButton.setTag("prevButton");
        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setTag("nextButton");
        //Set OnClick listeners
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

                //Get the loaderManager and initialize a loader 1 (POST /events)which is defined in onCreateLoader
                if(loaderManager  == null ){
                    loaderManager = getSupportLoaderManager();
                    loaderManager.initLoader(1,null,AvailabilityCalendarActivity.this);
                }
                else loaderManager.restartLoader(1, null, AvailabilityCalendarActivity.this);


                /*
                Intent returnIntent = new Intent();
                returnIntent.putExtra("event", AvailabilityCalendarActivity.this.event);
                setResult(RESULT_OK, returnIntent);
                finish();

                */
            }
        });

    }//onCreate

    @Override
    protected void dayViewTouched(CalendarDayView touchedDayView) {

        if(touchedDayView.isDead()) return;   //skip it

        if(touchedDayView.getDay().isInEvent() == true){
            this.touchedDayView = touchedDayView;       //assign it to a member var to be accesible from the popup
            showAvailabilityDialog();
        }
    }

    /*
    * Takes the context in which to show the dialog and a CalendarDayView on which it should perform the action of switching its Day availability to OK or NO
    * */
    private void showAvailabilityDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.availability_popup);
        TextView titleTV = (TextView)dialog.findViewById(R.id.availability_popup_title);
        titleTV.setText("Are you available on "+this.touchedDayView.getDay().getDateAsString()+"?");
        dialog.show();
        //Get the reference to OK and NO buttons
        Button ok_button = (Button)dialog.findViewById(R.id.availability_btn_yes);
        Button no_button = (Button)dialog.findViewById(R.id.availability_btn_no);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked OK on " + AvailabilityCalendarActivity.this.touchedDayView.getDay().getDateAsString());
                //save user selection in the EventDaysUserAvailability object
                AvailabilityCalendarActivity.this.eventDaysUserAvailability.changeAvailabilityForDay(touchedDayView.getDay(), "y");
                touchedDayView.setAvailabilityText("y");    //change visually too
                //Log.d(TAG, "and it's set to " + calendarActivityContext.event.getEventDayByDateString(dayView1.getDay().getDateAsString()).getCurrentUserAvailability());
                dialog.dismiss();

            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked NO  "+AvailabilityCalendarActivity.this.touchedDayView.getDay().getDateAsString());
                //save user selection in the EventDaysUserAvailability object
                AvailabilityCalendarActivity.this.eventDaysUserAvailability.changeAvailabilityForDay(touchedDayView.getDay(), "n");
                touchedDayView.setAvailabilityText("n");    //change visually too
                dialog.dismiss();
            }
        });
    }

    /*
    * Form CalendarOnMonthListener this method is called to repopulate the calendar grid with next
    * or prev month days
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


    @Override
    public Loader<RESTResponse> onCreateLoader(int id, Bundle bundle) {
        //Create the loader...
        if(id == 1){
            Log.d(TAG, "Creating RESTLoader for the LoaderManager");

            if(NetUtils.isOnline(AvailabilityCalendarActivity.this))    {
                //shows and returns
                //progressDialog = CustomProgressSpinner.show(CreateEventActivity.this, "", "");

                Bundle myParams = new Bundle();
                String body = eventDaysUserAvailability.toJson(); //TODO implemnt toJSON!!!

                myParams.putCharSequence("body", body);
                return new RESTLoader(this, HTTPVerb.PUT, Uri.parse(Constants.REST_BASE_URL + "events/"+event.getId_event()+"/availability"+ Session.getInstance().asURLauth()), myParams);

            }else{
                Toast.makeText(AvailabilityCalendarActivity.this.getApplicationContext(), "No internet :(", Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<RESTResponse> loader, RESTResponse data) {
        switch (loader.getId()) {
            case 1:         //ID of the loader...
                mResponse = data;                                // The asynchronous load is complete and the data is now available for use.
                Log.d(TAG, "RESTLoader :: Updating availability finished");

                if(mResponse.getCode() == 200){
                    //Availability updated
                    Log.d(TAG, "Code 200 returned");
                    //No parser because we look only the code
                    Toast.makeText(AvailabilityCalendarActivity.this.getApplicationContext(), "Your availability is updated", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(AvailabilityCalendarActivity.this, AllEventsListActivity.class);
                    //startActivity(intent);
                    finish();   //destroy this activity from the. User cant go back to it
                }else if(mResponse.getCode()== 401){
                    //User has to provide email/pass
                    Log.d(TAG, "Code 401 returned");
                    Toast.makeText(AvailabilityCalendarActivity.this.getApplicationContext(), "User not recognized!", Toast.LENGTH_LONG).show();
                }else if(mResponse.getCode()== 0){
                    Log.w(TAG, "Code 0 returned."+" - not reaching the server");
                    Toast.makeText(AvailabilityCalendarActivity.this.getApplicationContext(), "Not reaching the server :(!", Toast.LENGTH_LONG).show();

                }else{
                    Log.e(TAG, "Unexpected code " +mResponse.getCode()+" returned ?!");
                    Log.e(TAG, "Data: " +mResponse.getData());

                    Toast.makeText(AvailabilityCalendarActivity.this.getApplicationContext(), "Error occured :(!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<RESTResponse> restResponseLoader) {
        //To change body of implemented methods use File | Settings | File Templates.
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
