package com.letsmeetapp.activities.allevents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.letsmeetapp.Constants;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.createevent.CreateEventActivity;
import com.letsmeetapp.customviews.CustomProgressSpinner;
import com.letsmeetapp.model.Event;
import com.letsmeetapp.rest.HTTPVerb;
import com.letsmeetapp.rest.RESTEventsParser;
import com.letsmeetapp.rest.RESTLoader;
import com.letsmeetapp.rest.RESTResponse;
import com.letsmeetapp.utilities.NetUtils;

import java.util.ArrayList;

/**
 * Represents the main screen in the application that shows a list of events a user has been invited to or that he(she
 * has created. The way it works is it gets an instance of LoaderManager class and it initializes a new RestLoader
 *
 */
public class AllEventsListActivity extends FragmentActivity
        implements LoaderManager.LoaderCallbacks<RESTResponse>{

    private static final String TAG = AllEventsListActivity.class.getName();

    private Button createEventButton, refreshListButton;
    private ArrayList<Event> allEvents;
    private RESTResponse mResponse;
    private RESTLoader mRestLoader;         //It's set in onCreateLoader
    AllEventsListAdapter listAdapter;
    LoaderManager loaderManager;
    ListView listView;
    CustomProgressSpinner progressDialog;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //Give it the layout
        setContentView(R.layout.all_events_list);

        //Get the loaderManager and initialize a loader with the id 1 which is defined in onCreateLoader (GET /events)
        loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1,null,this);

        //get the reference to the list in the view to append it the adapter later...
        listView = (ListView) findViewById(R.id.all_events_list_view);
        listView.setOnItemClickListener(new AllEventsItemOnClickListener(this));


        //Refresh list button fires restartLoader
        refreshListButton = (Button)findViewById(R.id.all_events_refresh_btn);
        refreshListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NetUtils.isOnline(AllEventsListActivity.this)== false) Toast.makeText(AllEventsListActivity.this.getApplicationContext(), "No internet :(", Toast.LENGTH_LONG).show();
                else {
                    //initLoader reuses previously created
                    //if(loaderManager.getLoader(1) == null)  AllEventsListActivity.this.loaderManager.initLoader(1,null,AllEventsListActivity.this);
                    //else AllEventsListActivity.this.loaderManager.restartLoader(1,null,AllEventsListActivity.this);
                    //TODO bug When activity is started without internet and then internet is turned on and refreshed it crashes!
                    AllEventsListActivity.this.loaderManager.restartLoader(1,null,AllEventsListActivity.this);
                }

            }
        });

        //Create event button
        createEventButton = (Button)findViewById(R.id.goto_create_event);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AllEventsListActivity.this, CreateEventActivity.class);
                startActivity(intent);

            }
        });

    }




    //Loader callbacks:
    @Override
    public android.support.v4.content.Loader<RESTResponse> onCreateLoader(int id, Bundle bundle) {

        //Create RESTLoader for GET /events
        if(id == 1){
            Log.d(TAG, "loaderManager.initLoader(1,null,this) called");
            if(NetUtils.isOnline(AllEventsListActivity.this))    {
                progressDialog = CustomProgressSpinner.show(AllEventsListActivity.this,"","");
                mRestLoader = new RESTLoader(this, HTTPVerb.GET, Uri.parse(Constants.REST_BASE_URL+"events"));
                return mRestLoader;     //this is passed to onLoadFinished callback
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
                RESTEventsParser parser = new RESTEventsParser();       //new parser for /events response
                listAdapter = new AllEventsListAdapter(this, parser.parse(mResponse));   //pass parsed response to listAdapter
                listView.setAdapter(listAdapter);
                AllEventsListActivity.this.progressDialog.dismiss();
                break;
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<RESTResponse> loader) {
        switch (loader.getId()) {
            case 1:     //reset the loader with the id 1 (GET /events)
                Log.d(TAG, "onLoaderReset called");
                if(NetUtils.isOnline(AllEventsListActivity.this))    {
                    //shows and returns
                    progressDialog = CustomProgressSpinner.show(AllEventsListActivity.this,"","");
                }else{
                    Toast.makeText(AllEventsListActivity.this.getApplicationContext(), "No internet :(", Toast.LENGTH_LONG).show();
                }
        }

    }



}
