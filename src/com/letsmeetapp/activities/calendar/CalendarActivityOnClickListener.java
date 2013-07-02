package com.letsmeetapp.activities.calendar;

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
public class CalendarActivityOnClickListener implements View.OnTouchListener {

    private CalendarActivityBase calendarActivityContext;   //Context in which touch happened (CalendarAtivity)
    private GridView calendarGridView;
    private HashSet<Point> movementCoordinates = new HashSet<Point>();  //contains the coordinates of a movement. Duplicates excluded (hence Set)

    //Constructor
    public CalendarActivityOnClickListener(CalendarActivityBase context){
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
            }

            //clear the arrays...
            calendarGridView.clearFocus();
            movementCoordinates.clear();
            positionsTouched.clear();

            return true;
        }

        return false;

    }


}
