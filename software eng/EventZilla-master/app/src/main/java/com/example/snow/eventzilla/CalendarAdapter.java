package com.example.snow.eventzilla;

import android.content.Context;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by hector on 4/11/15.
 */
public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private GregorianCalendar mCalendar;
    private int day, currentmonth;
    private String mString;
    private int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //get the eventDates array from eventTable and color the coressponding dates a color other than white
    private ArrayList<ArrayList<String>> eventDates;


    public CalendarAdapter(Context c, GregorianCalendar calendar, ArrayList<ArrayList<String>> dates) {
        mContext = c;
        mCalendar = calendar;
        eventDates = dates;
    }

    public int getCount() {
        return 42;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getminposdaymonth()
    {
        int days;

        GregorianCalendar minfind = new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), 1);


        return minfind.get(Calendar.DAY_OF_WEEK);
    }

    public int getmaxposdaymonth()
    {
        GregorianCalendar maxfind = new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.getActualMaximum(Calendar.DATE));

        return maxfind.get(Calendar.DAY_OF_WEEK);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        TextView mtextView, meventView;
        RelativeLayout mrelativeLayout;
        day = calendarday(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.single_grid, null);

        } else {
            grid = (View) convertView;
        }
        mtextView = (TextView)grid.findViewById(R.id.grid_calendar_date);
        mrelativeLayout = (RelativeLayout)grid.findViewById(R.id.calendar_event_indicator);
        meventView = (TextView)grid.findViewById(R.id.calendar_text_event_indicator);
        mString = (currentmonth + 1) + "/" + day + "/" + mCalendar.get(Calendar.YEAR);

        for(int i = 0; i < eventDates.size(); i ++)
        {
            if(eventDates.get(i).contains(mString))
            {
                mrelativeLayout.setVisibility(View.VISIBLE);
                meventView.setVisibility(View.VISIBLE);
                meventView.setTextColor(Color.BLACK);
                break;
            }
        }



        Log.d("CalendarAdapter", "" + mString);

        mtextView.setText("" + day);
        if(currentmonth < mCalendar.get(mCalendar.MONTH) || currentmonth > mCalendar.get(mCalendar.MONTH))
        {
            mtextView.setTextColor(Color.WHITE);
            mtextView.setBackgroundResource(R.drawable.calendar_textview_nmonth_selector);
        }
        else
        {
            mtextView.setBackgroundResource(R.drawable.calendar_textview_selector);
            mtextView.setTextColor(Color.BLACK);

            if(day == mCalendar.get(Calendar.DATE))
            {
                mtextView.setTextColor(mContext.getResources().getColor(R.color.white));
            }
        }

        if(mContext.getResources().getConfiguration().orientation == Surface.ROTATION_180)grid.setMinimumHeight((parent.getHeight()/6) * 2);
        else grid.setMinimumHeight(parent.getHeight()/6);
        return grid;
    }


    public int calendarday(int position){

        int prevmonth, currentmonth;
        prevmonth = prevmaxdaysmonth();
        currentmonth = mCalendar.getActualMaximum(Calendar.DATE);

        if((position + 1) < getminposdaymonth())
        {
            setCurrentmonth(mCalendar.get(Calendar.MONTH) -1 );
            return (prevmonth - (getminposdaymonth() - (position + 2)));
        }
        else if((position + 1) >= getminposdaymonth() && ((position + 1) - (getminposdaymonth() -1)) < currentmonth)
        {
            setCurrentmonth(mCalendar.get(Calendar.MONTH));
            return (((position + 1) - (getminposdaymonth() -1)) % currentmonth);
        }
        else if(((position + 1) - (getminposdaymonth() -1)) == currentmonth)
        {
            setCurrentmonth(mCalendar.get(Calendar.MONTH));
            return currentmonth;
        }
        else
        {
            setCurrentmonth(mCalendar.get(Calendar.MONTH) + 1);
            return (((position + 1) - (getminposdaymonth() -1)) % currentmonth);
        }
    }

    public int prevmaxdaysmonth()
    {
        int month = months[Calendar.FEBRUARY];
        if(mCalendar.isLeapYear(mCalendar.get(mCalendar.YEAR))) month += 1;

        switch (mCalendar.get(Calendar.MONTH) - 1)
        {
            case Calendar.JANUARY: return months[Calendar.JANUARY];
            case Calendar.FEBRUARY: return month;
            case Calendar.MARCH: return months[Calendar.MARCH];
            case Calendar.APRIL: return months[Calendar.APRIL];
            case Calendar.MAY: return months[Calendar.MAY];
            case Calendar.JUNE: return months[Calendar.JUNE];
            case Calendar.JULY: return months[Calendar.JULY];
            case Calendar.AUGUST: return months[Calendar.AUGUST];
            case Calendar.SEPTEMBER: return months[Calendar.SEPTEMBER];
            case Calendar.OCTOBER: return months[Calendar.OCTOBER];
            case Calendar.NOVEMBER: return months[Calendar.NOVEMBER];
            default: return months[Calendar.DECEMBER];
        }
    }

    public void setCurrentmonth(int i)
    {
        if(i < 0) currentmonth = Calendar.DECEMBER;
        else if(i > Calendar.DECEMBER) currentmonth = Calendar.JANUARY;
        else currentmonth = i;
    }

}
