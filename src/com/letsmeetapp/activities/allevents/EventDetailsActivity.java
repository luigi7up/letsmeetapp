package com.letsmeetapp.activities.allevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.eventcalendar.CalendarActivity;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_details_activity);

        //get passed event object
        this.event = (Event)getIntent().getExtras().get("event");


        Toast.makeText(this.getApplicationContext(), "Im gonna show the details of "+event.getName(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Details for the event "+ event.getName());

        //Create event button
        eventCalendarButton = (Button)findViewById(R.id.open_event_calendar_button);
        eventCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetailsActivity.this, CalendarActivity.class);
                intent.putExtra("event", EventDetailsActivity.this.event);      //Calendar doesn't implemetn Parcelable so I send the whole event instead of event.getCreationdate...
                intent.putExtra("allSelectedDays",new ArrayList<Day>());      //Calendar doesn't implemetn Parcelable so I send the whole event instead of event.getCreationdate...
                //startActivityForResult(intent, 2);
                startActivity(intent);
            }
        });

    }
}
