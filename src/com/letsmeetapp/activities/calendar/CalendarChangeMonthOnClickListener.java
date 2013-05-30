package com.letsmeetapp.activities.calendar;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *  It's a handler for the click events of the CalendarActivity header previous / next buttons.
 *  It creates a new CalendarAdapter, passes a new Calendar object set to the previous or next month
 *  to the adapter and attaches that adapter to the calendarGridView in the CalendarActivity
 */
public class CalendarChangeMonthOnClickListener implements View.OnClickListener {

    private CalendarActivity mContext;

    public CalendarChangeMonthOnClickListener(CalendarActivity calendarActivityContext){
        this.mContext = calendarActivityContext;
    }

    @Override
    public void onClick(View view) {

        if(view.getTag() == "nextButton") nextMonth();
        else if(view.getTag() == "prevButton") prevMonth();
    }


    public void prevMonth(){
        //mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH)-1);
        mContext.getmCalendar().add(Calendar.MONTH, -1);

        //Reset the gridViewAdapter because now it contains days for another month
        mContext.setCalendarAdapter(
                new CalendarAdapter(mContext,
                        mContext.getmCalendar(),
                        mContext.getAllSelectedDays()));

        mContext.getCalendarGridView().setAdapter(mContext.getCalendarAdapter());

        //TODO optimize! don't create object. Make it memeber var
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(mContext.getmCalendar().getTime());
        mContext.getCalendarHeaderMonth().setText(month_name);
    }

    public void nextMonth(){
        //mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH)-1);
        mContext.getmCalendar().add(Calendar.MONTH, +1);

        //Reset the gridViewAdapter because now it contains days for another month
        mContext.setCalendarAdapter(
                new CalendarAdapter(mContext,
                        mContext.getmCalendar(),
                        mContext.getAllSelectedDays()));

        mContext.getCalendarGridView().setAdapter(mContext.getCalendarAdapter());

        //TODO optimize! don't create object. Make it memeber var
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(mContext.getmCalendar().getTime());
        mContext.getCalendarHeaderMonth().setText(month_name);
    }
}
