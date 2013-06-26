package com.letsmeetapp.activities.allevents;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.letsmeetapp.model.Event;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: luka
 * Date: 02.06.13.
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class AllEventsListAdapter extends BaseAdapter{

    private static final String TAG = AllEventsListAdapter.class.getName();

    //Remember the calling context...
    private Context mContext;

    private ArrayList<Event> allItems;

    public AllEventsListAdapter(Context context, ArrayList<Event> allItems) {
        this.mContext = context;
        this.allItems = allItems;
    }

    public int getCount() {
        return allItems.size();
    }

    public Object getItem(int position) {
        return allItems.get(position);
    }

    /** Use the array index as a unique id. */
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param convertView The old view to overwrite, if one is passed
     * @returns a AllEventsItemView that holds wraps around an Event
     */
    public View getView(int position, View convertView, ViewGroup parent) {


        Log.d(TAG, "getView for position " + position);
        Log.d(TAG, "event on that position is " + allItems.get(position).getName());


        AllEventsItemView itemView;

        if (convertView == null) {
            Log.d(TAG, "View created");
            itemView = new AllEventsItemView(mContext, allItems.get(position));
        } else {
            Log.d(TAG, "View from cache");
            //Reusing cached view, but assigning the Event at the position: allItems.get(position)
            itemView = (AllEventsItemView) convertView;
            itemView.setEvent((Event)allItems.get(position));
            itemView.updateViewData();
        }

        return itemView;
    }


}
