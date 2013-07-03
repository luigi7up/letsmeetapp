package com.letsmeetapp.activities.calendar.availability;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.letsmeetapp.activities.calendar.CalendarAdapter;
import com.letsmeetapp.model.Day;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: luka.eterovic
 * Date: 2/07/13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
public class AvailabilityCalendarAdapter extends CalendarAdapter {

    private static final String TAG = AvailabilityCalendarAdapter.class.getName();

    public AvailabilityCalendarAdapter(Context c, Calendar startingDate, ArrayList<Day> allSelectedDays) {
        super(c, startingDate, allSelectedDays);
    }
}
