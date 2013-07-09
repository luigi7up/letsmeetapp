package com.letsmeetapp.activities.createevent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.letsmeetapp.Constants;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.createcalendar.CreateCalendarActivity;
import com.letsmeetapp.activities.eventinvite.InvitePeopleActivity;
import com.letsmeetapp.customviews.CustomProgressSpinner;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.rest.HTTPVerb;
import com.letsmeetapp.rest.RESTEventsParser;
import com.letsmeetapp.rest.RESTLoader;
import com.letsmeetapp.rest.RESTResponse;
import com.letsmeetapp.utilities.NetUtils;
import com.letsmeetapp.utilities.VisualUtility;

import java.util.ArrayList;

import static com.letsmeetapp.utilities.TextUtils._;

/**
 * Represents a screen where user can create a new event by defining the name, description, initial days (starting new activity and fetching the result)
 * and can define emails (another activity) to which he/she wants send an invitation. In the end the Activity is using a LoaderManager and a RESTLoader to send
 * a POST /event to create the event...
 */

public class CreateEventActivity extends FragmentActivity
        implements LoaderManager.LoaderCallbacks<RESTResponse>{

    private static final String TAG = CreateEventActivity.class.getName();

    private Button selectDatesButton;
    private Button invitePeopleButton;
    private Button createEvent;

    EditText createEventNameEditText , createEventDescEditText;
    private ArrayList<String> invitedUsers = new ArrayList<String>();
    private ArrayList<Day> allSelectedDays = new ArrayList<Day>();  //holds dates that will have been selected...
    private Event newEvent;
    private LoaderManager loaderManager;        //needed for the RESTLoader that will send POST /events
    private RESTResponse mResponse;
    CustomProgressSpinner progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.create_event_activity);

        createEventNameEditText = (EditText)findViewById(R.id.create_event_name);
        createEventDescEditText = (EditText)findViewById(R.id.create_event_desc);

        //TODO refactor?
        selectDatesButton   = (Button)findViewById(R.id.select_dates);
        selectDatesButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Event event = new Event();
                        Intent intent = new Intent(CreateEventActivity.this, CreateCalendarActivity.class);
                        intent.putExtra("allSelectedDays", CreateEventActivity.this.allSelectedDays);
                        intent.putExtra("event", event);
                        startActivityForResult(intent, 1);
                    }
                }
        );

        invitePeopleButton  = (Button)findViewById(R.id.invite_people);
        invitePeopleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateEventActivity.this, InvitePeopleActivity.class);
                        intent.putExtra("emails", CreateEventActivity.this.invitedUsers);
                        startActivityForResult(intent, 2);
                    }
                }
        );

        //Finish and send
        createEvent  = (Button)findViewById(R.id.create_and_invite_button);
        createEvent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "Creating the event...");

                        String warning = "";
                        if(CreateEventActivity.this.createEventNameEditText.getText().toString().length() < 1){
                            warning = "What's with the name?";
                        }else if(CreateEventActivity.this.createEventDescEditText.getText().toString().length() < 1){
                            warning = "Please, describe the event";
                        }else if(allSelectedDays.size() == 0){
                            warning = "Please, select days of the event";
                        }else if(invitedUsers.size() == 0){
                            warning = "Please, invite people";
                        }

                        //If warning is not empty show toast and return
                        if(!warning.equals("")){
                            Toast.makeText(CreateEventActivity.this.getApplicationContext(), warning, Toast.LENGTH_LONG).show();
                            return;
                        }

                        //Collect data from the EditFields....
                        newEvent = new Event();
                        newEvent.setName(CreateEventActivity.this.createEventNameEditText.getText().toString());
                        newEvent.setDescription(CreateEventActivity.this.createEventDescEditText.getText().toString());
                        newEvent.setId_creator(1);
                        newEvent.setDays(allSelectedDays);
                        newEvent.setInvited_users(invitedUsers);

                        //Get the loaderManager and initialize a loader 1 (POST /events)which is defined in onCreateLoader
                        if(loaderManager  == null ){
                            loaderManager = getSupportLoaderManager();
                            loaderManager.initLoader(1,null,CreateEventActivity.this);
                        }
                        else loaderManager.restartLoader(1, null, CreateEventActivity.this);

                    }
                }
        );

        //Hide keyboard
        View rootView = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Touch happened"+v.getClass())  ;
                if (v instanceof EditText == false) {
                    //Focus has been lost hide keyboard...
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //coming back from CalendarActivity
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                //fill with the values returned
                allSelectedDays = (ArrayList<Day>)data.getExtras().get("allSelectedDays");
                if(allSelectedDays != null && allSelectedDays.size() > 0) {
                    selectDatesButton.setText(allSelectedDays.size()+" "+_(this, R.string.days)+ " ["+VisualUtility.periodForSelectedDates(allSelectedDays)+"]");
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult received RESULT_CANCEL from CalendarActivity with data: "+data);
            }
        }

        //coming back from InvitePeople
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                //fill with the values returned
                invitedUsers = (ArrayList<String>)data.getExtras().get("emails");
                if(invitedUsers != null && invitedUsers.size() > 0) {
                    invitePeopleButton.setText(invitedUsers.size()+" people invited");
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult received RESULT_CANCEL from CalendarActivity with data: "+data);
            }
        }


    }//onActivityResult

    //onCreateLoader
    @Override
    public Loader<RESTResponse> onCreateLoader(int id, Bundle bundle) {
        //Create the loader...
        if(id == 1){
            Log.d(TAG, "Creating RESTLoader for the LoaderManager");

            if(NetUtils.isOnline(CreateEventActivity.this))    {
                //shows and returns
                progressDialog = CustomProgressSpinner.show(CreateEventActivity.this, "", "");

                Bundle myParams = new Bundle();
                String postBodyJson = newEvent.asJSON();            //custom json serialization

                myParams.putCharSequence("postBodyJson", postBodyJson);
                return new RESTLoader(this, HTTPVerb.POST, Uri.parse(Constants.REST_BASE_URL + "events"), myParams);

            }else{
                Toast.makeText(CreateEventActivity.this.getApplicationContext(), "No internet :(", Toast.LENGTH_LONG).show();
            }
        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<RESTResponse> loader, RESTResponse data) {
        switch (loader.getId()) {
            case 1:         //ID of the loader...
                // The asynchronous load is complete and the data is now available for use.
                mResponse = data;
                RESTEventsParser parser = new RESTEventsParser();       //new parser for /events

                progressDialog.dismiss();
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<RESTResponse> loader) {

        //TODO since loader is created when CREATE button is pressed we'll call RESET insetead of INIT if the loader exists already for this activity
        Log.d(TAG, "Restarting RESTLoader POST");

    }





}
