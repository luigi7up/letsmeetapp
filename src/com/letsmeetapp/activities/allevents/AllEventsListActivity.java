package com.letsmeetapp.activities.allevents;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.createevent.CreateEventActivity;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.rest.HTTPVerb;
import com.letsmeetapp.rest.RESTEventsParser;
import com.letsmeetapp.rest.RESTLoader;
import com.letsmeetapp.rest.RESTResponse;
import android.support.v4.app.FragmentActivity;
import com.letsmeetapp.utilities.NetUtils;


import java.util.ArrayList;

/**
 * Represents the main screen in the application that shows a list of events a user has been invited to or that he(she
 * has created...
 */
public class AllEventsListActivity extends FragmentActivity
        implements LoaderManager.LoaderCallbacks<RESTResponse>{

    private static final String TAG = AllEventsListActivity.class.getName();

    private Button createEventButton;
    private ArrayList<Event> allEvents;
    private RESTResponse mResponse;
    AllEventsListAdapter listAdapter;
    ListView listView;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //Give it the layout
        setContentView(R.layout.all_events_list);

        //instantiate a progress bar
        progressDialog = new ProgressDialog(AllEventsListActivity.this,ProgressDialog.STYLE_SPINNER);

        //Get the loaderManager and initialize a loader 1 which is defined in onCreateLoader
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1,null,this);

        //get the reference to the list in the view to append it the adapter later...
        listView = (ListView) findViewById(R.id.all_events_list_view);
        listView.setOnItemClickListener(new AllEventsItemOnClickListener(this));

        //Create event button
        createEventButton = (Button)findViewById(R.id.goto_create_event);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllEventsListActivity.this, CreateEventActivity.class);
                startActivity(intent);

            }
        });

    }




    //Loader callbacks:
    @Override
    public android.support.v4.content.Loader<RESTResponse> onCreateLoader(int id, Bundle bundle) {

        if(id == 1){
            Log.d(TAG, "Creating RESTLoader for the LoaderManager");

            if(NetUtils.isOnline(AllEventsListActivity.this))    {
                progressDialog.show();
                return new RESTLoader(this, HTTPVerb.GET, Uri.parse("http://www.luigi7up.com/hosted/letsmeetapp-rest/events.json"));
            }else{
                Toast.makeText(AllEventsListActivity.this.getApplicationContext(), "No internet :(", Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<RESTResponse> loader, RESTResponse data) {
        switch (loader.getId()) {
            case 1:         //ID of the loader...
                // The asynchronous load is complete and the data is now available for use.
                mResponse = data;
                RESTEventsParser parser = new RESTEventsParser();       //new parser for /events
                listAdapter = new AllEventsListAdapter(this, parser.parse(mResponse));
                listView.setAdapter(listAdapter);
                progressDialog.dismiss();
                break;
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<RESTResponse> loader) {
        Log.d(TAG, "onLoaderReset called");
    }



}
