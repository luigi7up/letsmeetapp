package com.letsmeetapp.activities.createevent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.letsmeetapp.BaseActivity;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.eventcalendar.CalendarActivity;
import com.letsmeetapp.activities.eventinvite.InvitePeopleActivity;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.utilities.VisualUtility;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 30/05/13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class CreateEventActivity extends BaseActivity {

    private Button selectDatesButton;
    private Button invitePeopleButton;
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Day> allSelectedDays = new ArrayList<Day>();  //holds dates that will have been selected...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.create_event_activity);

        //Set font on the field for event's name...
        EditText createEventName = (EditText)findViewById(R.id.create_event_name);
        Typeface font = Typeface.createFromAsset(getAssets(), "Comfortaa_Regular.ttf");
        createEventName.setTypeface(font);

        //TODO refactor?
        selectDatesButton   = (Button)findViewById(R.id.select_dates);
        selectDatesButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateEventActivity.this, CalendarActivity.class);
                        intent.putExtra("allSelectedDays", CreateEventActivity.this.allSelectedDays);
                        startActivityForResult(intent, 1);
                    }
                }
        );

        //TODO implement onACtivityresult...
        invitePeopleButton  = (Button)findViewById(R.id.invite_people);
        invitePeopleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateEventActivity.this, InvitePeopleActivity.class);
                        intent.putExtra("emails", CreateEventActivity.this.emails);
                        startActivityForResult(intent, 2);
                    }
                }
        );

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //requestCode for CalendarActivity
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                //fill with the values returned
                allSelectedDays = (ArrayList<Day>)data.getExtras().get("allSelectedDays");
                if(allSelectedDays != null && allSelectedDays.size() > 0) {
                    selectDatesButton.setText(allSelectedDays.size()+" "+_(R.string.days)+ " ["+VisualUtility.periodForSelectedDates(allSelectedDays)+"]");
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.d("Luka", "onAcivityResult received RESULT_CANCEL from CalendarActivity with data: "+data);
            }
        }

        //requestCode for InvitePeople
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                //fill with the values returned
                emails = (ArrayList<String>)data.getExtras().get("emails");
                if(emails != null && emails.size() > 0) {
                    invitePeopleButton.setText(emails.size()+" people invited");
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.d("Luka", "onAcivityResult received RESULT_CANCEL from CalendarActivity with data: "+data);
            }
        }


    }//onActivityResult


}
