package com.letsmeetapp.activities.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letsmeetapp.R;

/**
 * Represents a one day in a calendar. It extends LinearLayout because it consists of a few simple UI widgets
 * User: luka
 * Date: 21.05.13.
 * Time: 20:36
 */
public class CalendarDayView extends LinearLayout{

    private Day day;
    private int dimension;      //holds the width/height value for the orientation
    private Context mContext;
    private final int SELECTED_COLOR = Color.argb(255,220,220,220);
    private final int NOT_SELECTED_COLOR = Color.argb(255,255,255,255);


    public CalendarDayView(Context context, Day day ,int dimension) {
        super(context, null);
        this.mContext = context;
        this.day = day;
        this.dimension = dimension;

        //Inflate the the xml layout _calendar_day that holds all sub views
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.calendar_day, this);

        //Extract the text and set it into the text view
        TextView dayNumberTextView = (TextView)findViewById(R.id.daynumber);
        dayNumberTextView.setText(String.valueOf(day.getDateDayNumber()));

    }

    @Override
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);    //To change body of overridden methods use File | Settings | File Templates.

        CalendarActivity parentCalendarActivity = (CalendarActivity)mContext;
        //CalendarActivity parentActivity = (CalendarActivity)parentCalendarAdapter.getmContext();

        //If the day that is about to be returned as a grid view exist in allSelectedDayList mark it as selected
        if(parentCalendarActivity.getAllSelectedDays().contains(this.getDay())) this.getDay().setSelected(true);

        if(this.getDay().isSelected() == false) setBackgroundColor(NOT_SELECTED_COLOR);
        else setBackgroundColor(SELECTED_COLOR);

        setMeasuredDimension(dimension-1,dimension-1);
        Log.d("Luka", "onMeasure CalendarDayView with day "+this.getDay().getDate());
    }

    //Toggles the value selected from true to false
    public void toggleSelected(){

        Log.d("Luka","View for the day "+this.getDay()+" is toggled...");
        if(this.getDay().isSelected()) {
            this.getDay().setSelected(false);
            setBackgroundColor(NOT_SELECTED_COLOR);
        }
        else {
            this.getDay().setSelected(true);
            setBackgroundColor(SELECTED_COLOR);
        }

    }


    /*  GET SET */
    public Day getDay() {
        return day;
    }
    public void setDay(Day day){
        this.day = day;
    }


}
