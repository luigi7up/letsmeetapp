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

    public CalendarDayView(Context context, Day day ,int dimension) {
        super(context, null);
        this.day = day;
        this.dimension = dimension;

        //Inflate the the xml layout _calendar_day that holds all sub views
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.calendar_day, this);

        //Extract the text and set it into the text view
        TextView dayNumberTextView = (TextView)findViewById(R.id.daynumber);
        dayNumberTextView.setText(String.valueOf(day.getDateDayNumber()));

        Log.d("Luka", "Creating Calendar Day View for "+day.toString()+ " with dimension "+dimension);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);    //To change body of overridden methods use File | Settings | File Templates.

        if(this.getDay().isSelected() == false) setBackgroundColor(Color.GREEN);
        else setBackgroundColor(Color.GRAY);

        setMeasuredDimension(dimension-3,dimension-3);
        Log.d("Luka", "onMeasure CalendarDayView with dimension "+dimension);
    }

    /*  GET SET */
    public Day getDay() {
        return day;
    }
    public void setDay(Day day){
        this.day = day;
    }


}
