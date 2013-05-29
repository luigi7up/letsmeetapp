package com.letsmeetapp.activities.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import java.util.HashSet;

/**
 * This is a onTouch handler for calendarGridView set in CalendarActivity class. It captures touch movement coordinates
 * translates them into positions in the grid that were touched and toggles isSelected on the DayView that was touched
 */
public class CalendarActivityOnTouchListener implements View.OnTouchListener {

    private CalendarActivity calendarActivityContext;   //Context in which touch happened (CalendarAtivity)
    private GridView calendarGridView;
    private HashSet<Point> movementCoordinates = new HashSet<Point>();  //contains the coordinates of a movement. Duplicates excluded (hence Set)

    //Constructor
    public CalendarActivityOnTouchListener(CalendarActivity context){
        this.calendarActivityContext = context;
        calendarGridView = calendarActivityContext.getCalendarGridView();  //assign touched gridView into a local variable
    }

    /**
     * handler for a touch event over the calendarGridView
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //NOTE: ACTION_MOVE until you dont release it fires, ACTION_UP once you release it fires it
        int newEventCode = event.getAction();

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //calendarGridView.requestFocusFromTouch();
            //calendarGridView.setSelection(calendarGridView.pointToPosition((int) event.getX(), (int) event.getY()));

            //Collect the coordinates of the movement and when ACTION_UP event is fired execute selection and view invalidation
            movementCoordinates.add(new Point((int)event.getX(), (int)event.getY()));
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //calendarGridView.requestFocusFromTouch();

            Log.d("Luka", "MotionEvent.ACTION_UP");

            //If no movement happened, just indicate you got the event...
            if(movementCoordinates == null) return true;

            //Convert coordinates into Set of positions in the grid that were touched
            HashSet<Integer> positionsTouched = new HashSet<Integer>();
            int position;
            for(Point p : movementCoordinates){
                Log.d("Luka", p.x +" / "+p.y);
                position = calendarGridView.pointToPosition(p.x, p.y);
                if(position == -1) {
                    Log.d("Luka", "Coordinates converted into -1. Probbably touched the space between the two");
                    break;
                }
                positionsTouched.add(position);
                //calendarGridView.setSelection(position);
            }
            //For all touched positions toggleSelected();
            CalendarDayView touchedDayView;
            for(int p:positionsTouched){

                touchedDayView = (CalendarDayView)this.calendarActivityContext.getCalendarAdapter().getItem(p);

                touchedDayView.toggleSelected();

                /*
                if(touchedDayView.getDay().isSelected()) {
                    touchedDayView.getDay().setSelected(false);
                    calendarActivityContext.getAllSelectedDays().remove(touchedDayView.getDay());
                }
                else {
                    touchedDayView.getDay().setSelected(true);
                    calendarActivityContext.getAllSelectedDays().add(touchedDayView.getDay());
                }
                  */
                Log.d("Luka", "Position touched" + p);

                //touchedDayView.setBackgroundColor(Color.RED);

            }

            //this.calendarActivityContext.getCalendarAdapter().notifyDataSetChanged();
            //this.calendarGridView.setAdapter(this.calendarActivityContext.getCalendarAdapter());

            //this.calendarGridView.noti

            //clear the HashSet of points
            calendarGridView.clearFocus();
            movementCoordinates.clear();
            positionsTouched.clear();

            return true;
        }

        return false;

    }


}
