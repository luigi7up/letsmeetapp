package com.letsmeetapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.creating.CreateCalendarActivity;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.utilities.VisualUtility;

import java.util.ArrayList;

import static com.letsmeetapp.utilities.TextUtils._;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 6/06/13
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */
public class EventDetailsActivity extends Activity {

    private static final String TAG = EventDetailsActivity.class.getName();

    private Event event;
    private Button eventCalendarButton;
    private TextView nameTextView, descriptionTextView, invitedPeopleTextView, daysTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_details_activity);

        //get passed event object
        this.event = (Event)getIntent().getExtras().get("event");
        Toast.makeText(this.getApplicationContext(), "Im gonna show the details of "+event.getName(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Details for the event "+ event.getName());


        nameTextView               = (TextView)findViewById(R.id.event_details_name);
        descriptionTextView        = (TextView)findViewById(R.id.event_details_description);
        //invitedPeopleTextView      = (TextView)findViewById(R.id.event_details_invited_people);
        //daysTextView               = (TextView)findViewById(R.id.event_details_days);

        nameTextView.setText(event.getName());
        descriptionTextView.setText(event.getDescription());
        //daysTextView.setText(event.getDays().toString());
        //invitedPeopleTextView.setText(event.getInvited_users().toString());

        //Create event button
        eventCalendarButton = (Button)findViewById(R.id.open_event_calendar_button);
        eventCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetailsActivity.this, CreateCalendarActivity.class);
                intent.putExtra("event", EventDetailsActivity.this.event);      //Calendar doesn't implemetn Parcelable so I send the whole event instead of event.getCreationdate...
                intent.putExtra("allSelectedDays",new ArrayList<Day>());        //Calendar doesn't implemetn Parcelable so I send the whole event instead of event.getCreationdate...
                startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //coming back from CalendarActivity
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                //fill with the values returned
                //event = (Event)data.getParcelableExtra("event");
                EventDetailsActivity.this.event = (Event)data.getParcelableExtra("event");
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult received RESULT_CANCEL from CalendarActivity with data: "+data);
            }
        }
    }
}
