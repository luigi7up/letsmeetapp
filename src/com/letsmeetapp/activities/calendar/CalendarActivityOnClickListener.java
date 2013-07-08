package com.letsmeetapp.activities.calendar;

import android.app.Dialog;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import com.letsmeetapp.R;

import java.util.HashSet;

/**
 * This is a onTouch handler for calendarGridView set in CalendarActivity class. It captures touch movement coordinates
 * translates them into positions in the grid that were touched and toggles isSelected on the DayView that was touched
 */
public class CalendarActivityOnClickListener implements View.OnTouchListener {

    private static final String TAG = CalendarActivityOnClickListener.class.getName();

    private CalendarActivity calendarActivityContext;   //Context in which touch happened (CalendarAtivity)
    private GridView calendarGridView;
    private HashSet<Point> movementCoordinates = new HashSet<Point>();  //contains the coordinates of a movement. Duplicates excluded (hence Set)

    //Constructor
    public CalendarActivityOnClickListener(CalendarActivity context){
        calendarActivityContext = context;
        calendarGridView = calendarActivityContext.getCalendarGridView();  //assign touched gridView into a local variable
    }

    /**
     * handler for a touch event over the calendarGridView
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //NOTE: ACTION_MOVE until you dont release it fires, ACTION_UP once you release it fires it
        int newEventCode = event.getAction();

        //Collect movement coordinates in a set
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //Collect the coordinates of the movement and when ACTION_UP event is fired execute selection and view invalidation
            movementCoordinates.add(new Point((int)event.getX(), (int)event.getY()));
            return true;
        }

        //Register the stop of movement
        if (event.getAction() == MotionEvent.ACTION_UP) {

            //If no movement happened, just indicate you got the event to stop propagation...
            if(movementCoordinates == null) return true;

            //Convert coordinates into Set of positions in the grid that were touched
            HashSet<Integer> positionsTouched = new HashSet<Integer>();
            int position;
            for(Point p : movementCoordinates){
                position = calendarGridView.pointToPosition(p.x, p.y);
                if(position == -1) {
                    Log.d("Luka", "Coordinates converted into -1. Probbably touched the space between the two");
                    break;
                }
                positionsTouched.add(position);
            }

            //For all touched positions: touchedDayView.toggleSelected();
            for(int p:positionsTouched){
                CalendarDayView touchedDayView = (CalendarDayView)this.calendarActivityContext.getCalendarAdapter().getItem(p);
                touchedDayView.toggleSelected();

                //If the day that is about to be returned as a grid view exist in allSelectedDayList mark it as selected
                if(calendarActivityContext.getAllSelectedDays().contains(touchedDayView.getDay())) {
                    calendarActivityContext.getAllSelectedDays().remove(touchedDayView.getDay());
                }else{
                    calendarActivityContext.getAllSelectedDays().add(touchedDayView.getDay());
                }

                if(touchedDayView.getDay().isInEvent() == true){
                    showAvailabilityDialog(touchedDayView);

                }
            }

            //clear the arrays...
            calendarGridView.clearFocus();
            movementCoordinates.clear();
            positionsTouched.clear();

            return true;
        }

        return false;

    }

    /*
    * Takes the context in which to show the dialog and a CalendarDayView on which it should perform the action of switching its Day availability to OK or NO
    * */
    private void showAvailabilityDialog(CalendarDayView dayView){
        Log.d(TAG, "POPUP!");

        final CalendarDayView dayView1 = dayView;

        final Dialog dialog = new Dialog(calendarActivityContext);
        dialog.setContentView(R.layout.availability_popup);
        dialog.show();
        //Get the reference to OK and NO buttons
        Button ok_button = (Button)dialog.findViewById(R.id.availability_btn_yes);
        Button no_button = (Button)dialog.findViewById(R.id.availability_btn_no);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked OK on " + dayView1.getDay().getDateAsString());
                calendarActivityContext.event.getEventDayByDateString(dayView1.getDay().getDateAsString()).setCurrentUserAvailability("y");

                Log.d(TAG, "and it's set to " + calendarActivityContext.event.getEventDayByDateString(dayView1.getDay().getDateAsString()).getCurrentUserAvailability());

                dialog.dismiss();

            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked NO  "+dayView1.getDay().getDateAsString());
                calendarActivityContext.event.getEventDayByDateString(dayView1.getDay().getDateAsString()).setCurrentUserAvailability("n");
                Log.d(TAG, "and it's set to " + calendarActivityContext.event.getEventDayByDateString(dayView1.getDay().getDateAsString()).getCurrentUserAvailability());
                dialog.dismiss();
            }
        });




    }


}
