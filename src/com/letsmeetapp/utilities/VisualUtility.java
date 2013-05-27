package com.letsmeetapp.utilities;

import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: luka
 * Date: 22.05.13.
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public class VisualUtility {

    static public float dpFromPxForScreen(int px, View currentView){

        return px / currentView.getContext().getResources().getDisplayMetrics().density;
    }


    static public float pxFromDpForScreen(int dp, View currentView)
    {
        return dp * currentView.getContext().getResources().getDisplayMetrics().density;
    }
}
