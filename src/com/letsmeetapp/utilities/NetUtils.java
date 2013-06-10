package com.letsmeetapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 6/06/13
 * Time: 9:31
 * To change this template use File | Settings | File Templates.
 */
public class NetUtils {


    /**
     * Checks if there is interenet connection for the device. It does it using the ConnectivityManager
     * @param mContext is the activity context that called this method. It's needed to be able to call getSystemService()
     * @return It returns false if no internet was found and true if there is internet
     */
    public static boolean isOnline(Context mContext){

        ConnectivityManager connec = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connec != null && (connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) ||(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)){
            //Toast.makeText(mContext.getApplicationContext(), "Fetching...", Toast.LENGTH_LONG).show();
            return true;
        }else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED ) {
            //Not connected.
            //Toast.makeText(mContext.getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
        }

        return false;
    }




}
