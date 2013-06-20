package com.letsmeetapp.activities.allevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.utilities.TextUtils;
import com.letsmeetapp.utilities.VisualUtility;

/**
 * View that is created for each list item in the alleventsList...
 */
public class AllEventsItemView extends LinearLayout {

    private Event event;        //Model for the view
    private TextView name,eventCreator,eventDescription,eventDayNumber,invitedUsersNumber ;


    public AllEventsItemView(Context context, Event event) {
        super(context);
        this.event = event;

        //Inflate the the xml layout _calendar_day that holds all sub views
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.all_events_list_item, this);

        name = (TextView)findViewById(R.id.all_events_event_name);
        eventCreator = (TextView)findViewById(R.id.all_events_event_creator);
        eventDescription = (TextView)findViewById(R.id.all_events_event_desc) ;
        eventDayNumber = (TextView)findViewById(R.id.all_events_days_number) ;
        invitedUsersNumber   = (TextView)findViewById(R.id.all_events_num_of_people) ;

        name.setText(TextUtils.shorten(event.getName(),60));
        eventDescription.setText(TextUtils.shorten(event.getDescription(),60));
        eventDayNumber.setText(TextUtils.numOfDays(context, event.getDays().size()));
        invitedUsersNumber.setText(TextUtils.numOfPeople(context, event.getInvited_users().size()));

    }

    //SETTERS AND GETTERS
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }

}
