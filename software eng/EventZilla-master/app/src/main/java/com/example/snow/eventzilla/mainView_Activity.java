package com.example.snow.eventzilla;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.AUGUST;
import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;
import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.OCTOBER;
import static java.util.Calendar.SEPTEMBER;
import static java.util.Calendar.getInstance;

/**
 * Created by snow on 4/4/2015.
 */
public class mainView_Activity extends Activity{

    private Button prevButton;
    private Button nextButton;
    private TextView mTitle, monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private int prevMonth, nextMonth, CurrentMonth, CurrentYear, mfirst = 0;
    private TextSwitcher prevButtonText, nextButtonText;
    private View previd;
    private Animation myAnimationleft, myAnimationtop, myAnimationright, myAnimationbottom, myAnimationgrid;
    private GregorianCalendar mGregorianCalendar = new GregorianCalendar(getInstance().get(Calendar.YEAR), getInstance().get(Calendar.MONTH), getInstance().get(Calendar.DATE));
    private ArrayList<ArrayList<String>> eventDates;
    private Drawable mDrawable;
    private SessionState userState;
    private FragmentActivity eventlist;
    private GridView mGridView;
    private final static String TAG = "MainView_Activity";


    /**
     * Instantiating the view for the user, always needed
     * @param savedInstanceState
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.calendar_layout);  // points to calendar layout(it is shown)




        userState = new SessionState();

        eventDates = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("saved_data");
        Log.d(TAG, String.valueOf(eventDates.isEmpty()));

        myAnimationleft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        myAnimationright = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        myAnimationtop = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        myAnimationbottom = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        myAnimationgrid = AnimationUtils.loadAnimation(this, R.anim.fade_in);


        prevMonth = Calendar.getInstance().get(Calendar.MONTH) - 1;
        nextMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        CurrentMonth = Calendar.getInstance().get(Calendar.MONTH);
        CurrentYear = Calendar.getInstance().get(Calendar.YEAR);



        prevButton = (Button)findViewById(R.id.calendar_prev_button);
        nextButton = (Button)findViewById(R.id.calendar_next_button);
        mTitle = (TextView)findViewById(R.id.calendar_title);

        prevButton.setText(buttonmonths(prevMonth).substring(0,3));
        nextButton.setText(buttonmonths(nextMonth).substring(0,3));
        mTitle.setText(buttonmonths(CurrentMonth)+ " " + CurrentYear);

// set adapter
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setAdapter(new CalendarAdapter(this, mGregorianCalendar, eventDates));
        mGridView.setBackgroundColor(Color.WHITE);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (mfirst == 0) {
                    mfirst++;
                    previd = v;
                    previd.findViewById(R.id.grid_calendar_date).setSelected(true);
                    Log.d(TAG, v.findViewById(R.id.grid_calendar_date).toString());
                } else {
                    if (v.findViewById(R.id.grid_calendar_date) != previd) {
                        previd.findViewById(R.id.grid_calendar_date).setSelected(false);
                        previd = v;
                        previd.findViewById(R.id.grid_calendar_date).setSelected(true);
                    }
                }

                startActivity(new Intent(mainView_Activity.this, EventItemListActivity.class));
                Toast.makeText(getApplicationContext(), "" + position,
                        Toast.LENGTH_SHORT).show();


            }
        });

        monday = (TextView)findViewById(R.id.monday);
        monday.setWidth(mGridView.getColumnWidth());

        sunday = (TextView)findViewById(R.id.sunday);
        sunday.setWidth(mGridView.getColumnWidth());

        tuesday = (TextView)findViewById(R.id.tuesday);
        tuesday.setWidth(mGridView.getColumnWidth());

        wednesday = (TextView)findViewById(R.id.wednesday);
        wednesday.setWidth(mGridView.getColumnWidth());

        thursday = (TextView)findViewById(R.id.thursday);
        thursday.setWidth(mGridView.getColumnWidth());

        friday = (TextView)findViewById(R.id.friday);
        friday.setWidth(mGridView.getColumnWidth());

        saturday = (TextView)findViewById(R.id.saturday);
        saturday.setWidth(mGridView.getColumnWidth());

        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                v.startAnimation(myAnimationleft);
                nextButton.startAnimation(myAnimationright);
                mTitle.startAnimation(myAnimationbottom);
                mGridView.startAnimation(myAnimationleft);

                prevMonth = changingMonths(prevMonth, false);
                nextMonth = changingMonths(nextMonth, false);
                CurrentMonth = changingMonths(CurrentMonth, false);
                CurrentYear = yearCheck(CurrentMonth, false, CurrentYear);

                GregorianCalendar newCalendar = new GregorianCalendar(CurrentYear, CurrentMonth, getInstance().get(Calendar.DATE));
                mGridView.setAdapter(new CalendarAdapter(getApplicationContext(), newCalendar, eventDates));

                prevButton.setText(buttonmonths(prevMonth).substring(0, 3));
                nextButton.setText(buttonmonths(nextMonth).substring(0, 3));
                mTitle.setText(buttonmonths(CurrentMonth)+ " " + CurrentYear);

                Log.d(TAG, "" + prevMonth + " " + nextMonth + " " + CurrentMonth + " " + mGridView.getColumnWidth());
            }

        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(myAnimationright);
                prevButton.startAnimation(myAnimationleft);
                mTitle.startAnimation(myAnimationtop);
                mGridView.startAnimation(myAnimationright);

                prevMonth = changingMonths(prevMonth , true);
                nextMonth = changingMonths(nextMonth , true);
                CurrentMonth = changingMonths(CurrentMonth, true);
                CurrentYear = yearCheck(CurrentMonth, true, CurrentYear);

                GregorianCalendar newCalendar = new GregorianCalendar(CurrentYear, CurrentMonth, getInstance().get(Calendar.DATE));
                mGridView.setAdapter(new CalendarAdapter(getApplicationContext(), newCalendar, eventDates));

                prevButton.setText(buttonmonths(prevMonth).substring(0, 3));
                nextButton.setText(buttonmonths(nextMonth).substring(0, 3));
                mTitle.setText(buttonmonths(CurrentMonth)+ " " + CurrentYear);
                Log.d(TAG, "" + prevMonth + " " +nextMonth + " " + CurrentMonth);
            }
        });

    }


    public String buttonmonths(int months)
    {
        switch(months)
        {
            case JANUARY: return "January";
            case FEBRUARY:return "February";
            case MARCH: return "March";
            case APRIL: return "April";
            case MAY: return "May";
            case JUNE: return "June";
            case JULY: return "July";
            case AUGUST: return "August";
            case SEPTEMBER: return "September";
            case OCTOBER: return "October";
            case NOVEMBER: return "November";
            default: return "December";
        }
    }

    public int changingMonths(int month, boolean top)
    {
        if(top)
        {
            month +=1;
            switch(month)
            {
                case 12: return 0;
                default: return month;
            }
        }
        else
        {
            month-=1;
            switch(month)
            {
                case -1: return 11;
                default: return month;
            }
        }
    }


    public int yearCheck(int month, boolean top, int Year)
    {

        if(top) {
            if (month == 0) return Year+=1;
        }
        else {
            if(month == 11) return Year-=1;
        }
        return Year;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout_button:
                ParseUser.logOut();
                Intent intent = new Intent(getApplicationContext(), login_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                return true;
            case R.id.create_account:
                startActivity(new Intent(mainView_Activity.this, Account_Create_Activity.class));
                return true;
            case R.id.create_event_action_bar:
                startActivity(new Intent(mainView_Activity.this, CreateEvent_Activity.class));
                return true;
            case R.id.user_messages:
                startActivity(new Intent(mainView_Activity.this, UserMessages_Activity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        switch (userState.getRole())
        {
            case "Admin":
                break;
            default:
                menu.findItem(R.id.create_account).setVisible(false);
                break;
        }
        return true;
    }
}
