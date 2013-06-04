package com.letsmeetapp.activities.allevents;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.createevent.CreateEventActivity;
import com.letsmeetapp.model.Day;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.rest.RestLoader;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents the main screen in the application that shows a list of events a user has been invited to or that he(she
 * has created...
 */
public class AllEventsListActivity extends ListActivity {

    private Button createEventButton;
    private ArrayList<Event> allEvents;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //Give it the layout
        setContentView(R.layout.all_events_list);


        allEvents = new ArrayList<Event>();
        allEvents = fillWithTestData();

        RestLoader rest = new RestLoader(AllEventsListActivity.this);
        if(rest.isOnline()) Toast.makeText(getApplicationContext(), "Internet OK", Toast.LENGTH_LONG).show();
        else Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();



        HttpResponse response = rest.get();

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(builder.toString());

            JSONArray finalResult = new JSONArray(tokener);
        }catch (Exception e){
            e.printStackTrace();
        }
        AllEventsListAdapter listAdapter = new AllEventsListAdapter(AllEventsListActivity.this, allEvents);
        this.setListAdapter(listAdapter);


        createEventButton = (Button)findViewById(R.id.goto_create_event);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllEventsListActivity.this, CreateEventActivity.class);
                startActivity(intent);

            }
        });

    }


    //Returns an ArrayList with Events (TESTING DATA!!!)
    private ArrayList<Event> fillWithTestData() {

        ArrayList<String> emails = new ArrayList<String>();
        emails.add("alibaba@gmail.com");
        emails.add("luigi@gmail.com");
        emails.add("jenny.young@co.uk");

        Calendar calendar = Calendar.getInstance();

        ArrayList<Day> initialEventDays = new ArrayList<Day>();
        initialEventDays.add(new Day(calendar));
        calendar.add(Calendar.MONTH, 1);
        initialEventDays.add(new Day(calendar));
        calendar.add(Calendar.MONTH, 2);
        initialEventDays.add(new Day(calendar));

        for(int i=0; i<20; i++){
            Event a = new Event();
            a.setName("Morbi dui lectus, lacinia vel");
            a.setCreationDate(calendar);
            a.setCreatorEmail("luigi7up@gmail.com");
            a.setInitialEventDays(initialEventDays);
            a.setInvitedPeopleEmails(emails);
            allEvents.add(a);

        }

        /*
        Event b = new Event();
        b.setName("Blastem ictis lec, laca velition");
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        b.setCreationDate(calendar);
        b.setCreatorEmail("luigi7up@gmail.com");
        b.setInitialEventDays(initialEventDays);
        b.setInvitedPeopleEmails(emails);
        */
        //allEvents.add(a);
        //allEvents.add(b);

        return allEvents;


    }


}
