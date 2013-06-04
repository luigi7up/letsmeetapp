package com.letsmeetapp.rest;

import android.content.AsyncTaskLoader;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import com.letsmeetapp.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luka
 * Date: 03.06.13.
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class RestLoader extends AsyncTaskLoader {

    HttpRequestBase request = null;
    Context mContext;

    public RestLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Object loadInBackground() {
        return getResource();
    }





    public HttpResponse getResource(){
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(Constants.REST_BASE_URL+"groups");

        HttpResponse response = null;

        try {
            // Execute HTTP Post Request
            response = httpclient.execute(httpget);

        } catch (ClientProtocolException e) {
            Log.e("Luka","IOException");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Luka","IOException");
        }

        return response;
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }






}
