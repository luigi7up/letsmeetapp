package com.letsmeetapp.activities.allevents;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.letsmeetapp.activities.createevent.CreateEventActivity;
import com.letsmeetapp.model.Event;

/**
 * Listener for the items in the AllEvents view. It starts another activity with events details passed to it....
 */
public class AllEventsItemOnClickListener implements AdapterView.OnItemClickListener{

    private Context mContext;

    public AllEventsItemOnClickListener(Context context){
        mContext = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AllEventsItemView itemView = (AllEventsItemView)view;
        Event event = itemView.getEvent();

        Intent intent = new Intent(mContext, EventDetailsActivity.class);
        intent.putExtra("event", event);
        mContext.startActivity(intent);

    }
}
