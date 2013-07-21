package com.letsmeetapp.activities.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.letsmeetapp.Constants;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.allevents.AllEventsListActivity;
import com.letsmeetapp.activities.createevent.CreateEventActivity;
import com.letsmeetapp.rest.*;
import com.letsmeetapp.utilities.NetUtils;


/**
 * This activity shows a splash screen while in the background try to check if the credentials found in shared preferences are correct
 * If the web service returns 200 it proceeds to AllEvents activity and if not it shows the layout for logging in.
 * A) User enters credentials and hits "log in"
 * B) hits create account...
 */
public class LoginActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<RESTResponse>{

    private static final String TAG = LoginActivity.class.getName();
    private LoaderManager loaderManager;
    private RESTResponse mResponse;
    private RESTLoader mRestLoader;         //It's set in onCreateLoader callback

    private EditText emailEditT, passEditT ;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*  Show splash screen.
        *   Get SharedPref login details. If none found show login screen. Contrary send a create a RESTLoader to check the credentils
        *   Get the loaderManager and initialize a loader with the id 1 which is defined in onCreateLoader (GET /events)
        */

        //TODO remove hard coded values...
        Session.getInstance().setEmail("user6real@abc.com");
        Session.getInstance().setMd5Pass("1234");

        //TODO Implement the logic of show splash screen and try to check the users login using the credentials from the SharedPrefs


        this.setContentView(R.layout.login_credentials_activity);
        emailEditT  = (EditText)this.findViewById(R.id.login_email);
        passEditT   = (EditText)this.findViewById(R.id.login_pass);
        emailEditT.setText(Session.getInstance().getEmail());
        passEditT.setText(Session.getInstance().getMd5Pass());

        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gather credentials and stoe them into Session instance ;)
                Session.getInstance().setEmail(emailEditT.getText().toString());
                Session.getInstance().setMd5Pass(passEditT.getText().toString());

                loaderManager = getSupportLoaderManager();
                loaderManager.restartLoader(1,null,LoginActivity.this);
            }
        });


    }


    //Loader Callbacks
    @Override
    public Loader<RESTResponse> onCreateLoader(int id, Bundle bundle) {
        //Create the loader...
        if(id == 1){
            Log.d(TAG, "Creating RESTLoader for the Loginactivity");

            if(NetUtils.isOnline(LoginActivity.this))    {
                mRestLoader = new RESTLoader(this, HTTPVerb.GET, Uri.parse(Constants.REST_BASE_URL + "users/login" + Session.getInstance().asURLauth()));
                return mRestLoader;

            }else{
                Toast.makeText(LoginActivity.this.getApplicationContext(), "No internet :(", Toast.LENGTH_LONG).show();
            }
        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<RESTResponse> loader, RESTResponse restResponse) {

        switch (loader.getId()) {
            case 1:         //ID of the loader...
                // The asynchronous load is complete and the data is now available for use.
                Log.d(TAG, "RESTLoader :: Loginactivity finished");
                mResponse = restResponse;
                if(mResponse.getCode() == 200){
                    //User is recognized
                    Log.d(TAG, "Code 200 returned");
                    //No parser because we look only the code
                    Intent intent = new Intent(LoginActivity.this, AllEventsListActivity.class);    //User recognized! continue to the events screen
                    startActivity(intent);
                    finish();   //destroy this activity from the. User cant go back to it
                }else if(mResponse.getCode()== 401){
                    //User has to provide email/pass
                    Log.d(TAG, "Code 401 returned");
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "User not recognized!", Toast.LENGTH_LONG).show();
                }else if(mResponse.getCode()== 0){
                    Log.w(TAG, "Code 0 returned."+" - not reaching the server");
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Not reaching the server :(!", Toast.LENGTH_LONG).show();

                }else{
                    Log.e(TAG, "Unexpected code " +mResponse.getCode()+" returned ?!");
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Not reaching the server :(!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<RESTResponse> loader) {
        switch (loader.getId()) {
            case 1:     //reset the loader with the id 1 (GET /events)
                Log.d(TAG, "onLoaderReset called");
                if(NetUtils.isOnline(LoginActivity.this))    {
                    //shows and returns
                   // progressDialog = CustomProgressSpinner.show(AllEventsListActivity.this,"","");
                    //listView.invalidate();
                }else{
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "No internet :(", Toast.LENGTH_LONG).show();
                }
        }
    }





}
