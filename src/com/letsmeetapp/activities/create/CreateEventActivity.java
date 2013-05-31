package com.letsmeetapp.activities.create;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.letsmeetapp.R;
import com.letsmeetapp.activities.calendar.CalendarActivity;
import com.letsmeetapp.activities.calendar.Day;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 30/05/13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class CreateEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.create_event_activity);

        EditText createEventName = (EditText)findViewById(R.id.create_event_name);
        Typeface font = Typeface.createFromAsset(getAssets(), "Comfortaa_Regular.ttf");
        createEventName.setTypeface(font);


        //TODO refactor?
        Button btn = (Button)findViewById(R.id.select_dates);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateEventActivity.this, CalendarActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }
        );

    }





    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){

                ArrayList<Day> allSelectedDays = (ArrayList<Day>)data.getExtras().get("allSelectedDays");

                Log.d("Luka","CalendarActivity returned the following days: ");
                for(Day d:allSelectedDays){

                    Log.d("Luka","day:"+d.toString());
                }


            }
            if (resultCode == RESULT_CANCELED) {
                Log.e("Luka", "onAcivityResult received RESULT_CANCEL from CalendarActivity with data: "+data);
            }
        }
    }//onActivityResult


}
