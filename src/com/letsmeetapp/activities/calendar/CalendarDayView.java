package com.letsmeetapp.activities.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letsmeetapp.R;
import com.letsmeetapp.model.Day;

/**
 * Represents a one day in a calendar. It extends LinearLayout because it consists of a few simple UI widgets
 * User: luka
 * Date: 21.05.13.
 * Time: 20:36
 */
public class CalendarDayView extends LinearLayout{

    private Context mContext;
    private Day day;
    private int dimension;      //holds the width/height value for the orientation

    private Style           style;
    private Availability    availability;
    private Behaviour       behaviour;

    private boolean daySelected;
    private boolean isDead;        //If a month starts on Wednesday then Monday and Tuesday are dead: gray, no date

    public CalendarDayView(Context context, Day day ,int dimension) {
        this(context, day, dimension, false);
    }

    public CalendarDayView(Context context, Day day ,int dimension, boolean isDead) {
        super(context, null);
        this.mContext    = context;
        this.day         = day;
        this.dimension   = dimension;
        this.setDead(isDead);

        //Inflate the the xml layout _calendar_day that holds all sub views
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(isDead == true){
            layoutInflater.inflate(R.layout.calendar_day_dead, this);
        } else{
            //Inflate the the xml layout _calendar_day that holds all sub views
            layoutInflater.inflate(R.layout.calendar_day, this);
            TextView dayNumberTextView = (TextView)findViewById(R.id.daynumber);
            dayNumberTextView.setText(String.valueOf(day.getDateDayNumber()));
        }

    }


    @Override
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);    //To change body of overridden methods use File | Settings | File Templates.

        CalendarActivity parentCalendarActivity = (CalendarActivity)mContext;
        //CalendarActivity parentActivity = (CalendarActivity)parentCalendarAdapter.getmContext();

        //If the day that is about to be returned as a grid view exist in allSelectedDayList mark it as selected
        if(parentCalendarActivity.getAllSelectedDays().contains(this.getDay())) this.setSelected(true);

        if(this.isDaySelected() == false) setStyle(Style.NOT_SELECTED);
        else setStyle(Style.SELECTED);

        if(this.getDay().isInEvent())
        {
            if((this.getDay().getCurrentUserAvailability()).equalsIgnoreCase("y")==true) setAvailability(Availability.AVAILABLE_Y);
            if((this.getDay().getCurrentUserAvailability()).equalsIgnoreCase("n")==true) setAvailability(Availability.AVAILABLE_N);
        }


        setMeasuredDimension(dimension-1,dimension-1);
    }

    //Toggles the value selected from true to false
    public void toggleSelected(){

        //if(isDead() == true) return;                //don't allow click
        //if(this.getDay().isInEvent()) return;       //don't allow click

        Log.d("Luka","View for the day "+this.getDay()+" is toggled...");

        if(this.isDaySelected()) {
            this.setDaySelected(false);
            setStyle(Style.NOT_SELECTED);
        }
        else {
            this.setDaySelected(true);
            setStyle(Style.SELECTED);
        }
    }


    public void setStyle(Style s){
        this.style = s;
        setBackgroundColor(s.getColor());
    }

    public  void setAvailability(Availability a){
        this.availability = a;
    }
    public void setBehaviour(Behaviour b){
        this.behaviour = b;
    }

    /*
    * Possible styles CalendarDayView
    * */
    enum Style {

       /*
        private final int SELECTED_COLOR        = Color.argb(220,220,220,220);
        private final int NOT_SELECTED_COLOR    = Color.argb(220,240,240,240);
        private final int IN_EVENT_COLOR        = Color.argb(180,140,140,140);
        private final int AVAILABLE_Y_COLOR             = Color.GREEN;
        private final int AVAILABLE_N_COLOR             = Color.RED;
         */

        SELECTED(Color.argb(220,220,220,220)), NOT_SELECTED(Color.argb(220,240,240,240));
        private int color;
        private Style(int color){
            this.color = color;
        }
        public int getColor(){return this.color;}

    }
    enum Availability{
        AVAILABLE_Y,AVAILABLE_M,AVAILABLE_N;
    }
    /*
    * Posible interaction states of CalendarDayView
    * */
    enum Behaviour {
        CLICKABLE, NOT_CLICKABLE;
    }



    @Override
    public String toString(){
        return day.toString();
    }



    /*  GET SET */
    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDaySelected() {
        return daySelected;
    }

    public void setDaySelected(boolean daySelected) {
        this.daySelected = daySelected;
    }
}
