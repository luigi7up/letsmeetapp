package com.letsmeetapp.activities.allevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.utilities.VisualUtility;

/**
 * View that is created for each list item in the alleventsList...
 */
public class AllEventsItemView extends LinearLayout {

    private Event event;        //Model for the view
    private TextView name,eventCreator,eventCreationDate,eventPeriod,eventStatus ;


    public AllEventsItemView(Context context, Event event) {
        super(context);
        this.event = event;

        //Inflate the the xml layout _calendar_day that holds all sub views
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.all_events_list_item, this);

        name = (TextView)findViewById(R.id.all_events_event_name);
        eventCreator = (TextView)findViewById(R.id.all_events_event_creator);;
        eventCreationDate = (TextView)findViewById(R.id.all_events_date_created);;
        eventPeriod = (TextView)findViewById(R.id.all_events_period);;
        eventStatus = (TextView)findViewById(R.id.all_events_status);;

        name.setText(event.getName());
        eventCreator.setText(event.getCreatorEmail());



    }

    //SETTERS AND GETTERS
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }

}
