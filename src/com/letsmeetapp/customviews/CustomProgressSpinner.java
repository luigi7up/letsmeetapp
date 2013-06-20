package com.letsmeetapp.customviews;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.letsmeetapp.R;

/**
 * Custom transparent Dialog with the progress spinner
 * Thanks to http://stackoverflow.com/questions/3225889/how-to-center-progress-indicator-in-progressdialog-easily-when-no-title-text-pa/3226233#3226233
 */

public class CustomProgressSpinner extends Dialog {


    public static CustomProgressSpinner show(Context context) {
        return show(context, "", "", false);
    }

    public static CustomProgressSpinner show(Context context, CharSequence title,
                                        CharSequence message) {
        return show(context, title, message, false);
    }

    public static CustomProgressSpinner show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static CustomProgressSpinner show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static CustomProgressSpinner show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate,
                                        boolean cancelable, OnCancelListener cancelListener) {

        CustomProgressSpinner dialog = new CustomProgressSpinner(context);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    public CustomProgressSpinner(Context context) {
        super(context, R.style.CustomProgressSpinner);
    }


}
