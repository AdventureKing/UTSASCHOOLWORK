package com.example.daddyz.turtleboys.searchevent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.daddyz.turtleboys.R;
import com.example.daddyz.turtleboys.subclasses.GigUser;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jinbir on 6/26/2015.
 */
public class searchEvent extends Fragment {

    private EditText city;
    private EditText state;
    private LatLng selectedLatLng;
    private EditText keyword;
    private Context context;
    private TextView searchRadiusText;
    private long searchRadius_miles;
    private double searchRadius_kilometers;
    private View rootView;
    private SeekBar searchRadiusSeekBar;
    private SeekBar.OnSeekBarChangeListener searchRadiusSeekbarListener;

    private Button search;
    private Button reset;

    private EditText toDateText;
    private EditText fromDateText;
    private DialogFragment fromDateSelector;
    private DialogFragment toDateSelector;
    private Date toDate;
    private Date fromDate;

    private RadioGroup radioSortbyGroup;
    private RadioButton sortbyButton;
    private RadioButton defaultButton;
    private String home;
    private final GigUser currentUser = ParseUser.createWithoutData(GigUser.class, ParseUser.getCurrentUser().getObjectId());
    private double userParseLat;
    private double userParseLong;

    private EditText toTimeText;
    private EditText fromTimeText;
    private DialogFragment fromTimeSelector;
    private DialogFragment toTimeSelector;
    private Time toTime;
    private Time fromTime;

    private double MILESINAKILOMETER = 0.621;

    private Toolbar toolbar;
    private ActionBar actionBar;

    private FragmentManager fragManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.searchevent, container, false);
        //Set up a touch listener so when user taps the screen, the keyboard will hide.
        //View view = rootView.findViewById(R.id.registrationPage);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {

        super.onStart();

        userParseLat = currentUser.getUserHome().getLatitude();
        userParseLong = currentUser.getUserHome().getLongitude();

        //City
        //TODO Add Google's city autocomplete
        city = (EditText) rootView.findViewById(R.id.city);
        city.setHint(R.string.searchCity);

        //state
        state = (EditText) rootView.findViewById(R.id.state);
        state.setHint(R.string.searchState);

        //Search keyword for events
        keyword = (EditText) rootView.findViewById(R.id.keyword);
        keyword.setHint(R.string.searchKeyword);

        searchRadiusText = (TextView) rootView.findViewById(R.id.searchRadiusText);

        //Sortby RadioGroup
        radioSortbyGroup = (RadioGroup) rootView.findViewById(R.id.sortGroup);
        sortbyButton = (RadioButton) rootView.findViewById(radioSortbyGroup.getCheckedRadioButtonId());
        defaultButton = (RadioButton) rootView.findViewById(R.id.sortDate);

        //SearchRadius Seekbar
        searchRadiusSeekBar = (SeekBar) rootView.findViewById(R.id.searchRadius);
        //Inital Seekbar values
        searchRadius_miles = 0;
        //searchRadius_kilometers = searchRadius_miles / MILESINAKILOMETER;
        searchRadiusText.setText(String.format("Set Search Radius\t %d miles"
                , searchRadius_miles));
        /*
        searchRadiusText.setText(String.format("Set Search Radius\t %d miles / %.02f km"
                , searchRadius_miles, searchRadius_kilometers));
         */

        searchRadiusSeekbarListener =
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        searchRadius_miles = progress;
                        //searchRadius_kilometers = searchRadius_miles / MILESINAKILOMETER;
                        searchRadiusText.setText(String.format("Set Search Radius\t %d miles"
                                , searchRadius_miles));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };
        searchRadiusSeekBar.setOnSeekBarChangeListener(searchRadiusSeekbarListener);


        //fromDate date picker
        fromDateSelector = new DatePickerFragment();
        fromDateText = (EditText) rootView.findViewById(R.id.fromDate);
        fromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDateSelector.registerForContextMenu(fromDateText);
                fromDateSelector.show(getFragmentManager(), "fromDatePicker");
            }
        });
        fromDateText.setText(R.string.anyDay);

        //toDate date picker
        toDateSelector = new DatePickerFragment();
        toDateText = (EditText) rootView.findViewById(R.id.toDate);
        toDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDateSelector.registerForContextMenu(toDateText);
                toDateSelector.show(getFragmentManager(), "toDatePicker");

            }
        });
        toDateText.setText(R.string.anyDay);

        fromDateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Reset button causes a call to this
                // Checks if toDateText is a date. If it is, then convert to date object.
                if ( !(fromDateText.getText().toString().matches("Any Day")) ) {

                    String[] dates = fromDateText.getText().toString().split("/");

                    int tempMonth=Integer.parseInt(dates[0]);
                    tempMonth -= 1;
                    int tempYear =Integer.parseInt(dates[2]);
                    tempYear -= 1900;
                    int tempDay = Integer.parseInt(dates[1]);

                    fromDate = new Date(tempYear,tempMonth,tempDay);

                } else {
                    return;
                }

                //If toDate is set and fromDate is greater than toDate then set toDate to match fromDate
                if ( !(toDateText.getText().toString().matches("Any Day")) && fromDate.after(toDate) ) {
                    toDateText.setText(fromDateText.getText());
                }

            }
        });

        toDateText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // Reset button causes a call to this
                // Checks if toDateText is a date. If it is, then convert to date object.
                if ( !(toDateText.getText().toString().matches("Any Day")) ) {

                    String[] dates = toDateText.getText().toString().split("/");

                    int tempMonth=Integer.parseInt(dates[0]);
                    tempMonth -= 1;
                    int tempYear =Integer.parseInt(dates[2]);
                    tempYear -= 1900;
                    int tempDay = Integer.parseInt(dates[1]);

                    toDate = new Date(tempYear,tempMonth,tempDay);

                    //Toast.makeText(getActivity().getApplicationContext(), "SHOULDNT TRIGGER WHEN RESET IS HIT", Toast.LENGTH_SHORT).show();
                } else {
                    return;
                }

                // If fromDate is not set, then set it to toDate
                if ( fromDateText.getText().toString().matches("Any Day")) {
                    fromDateText.setText(toDateText.getText());
                }

                //If toDate is less than fromDate, then set fromDate to toDate
                if ( toDate.before(fromDate) ) {
                    fromDateText.setText(toDateText.getText());
                }
            }
        });

        //Search Button
        search = (Button) rootView.findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyword.getText().toString().trim().length() == 0 &&
                        city.getText().toString().trim().length() == 0 &&
                        state.getText().toString().trim().length() == 0){
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Something to Search.", Toast.LENGTH_SHORT).show();
                } else {
                    //Do a search
                    searchResultsFragment fragment = new searchResultsFragment();

                    fragment.setUserLat(userParseLat);
                    fragment.setUserLong(userParseLong);
                    fragment.setSearchQuery(keyword.getText().toString());
                    fragment.setFilterRadius(searchRadius_miles);
                    fragment.setFilterCity(city.getText().toString());
                    fragment.setFilterState(state.getText().toString());

                    Log.i("query", keyword.getText().toString());
                    Log.i("city", city.getText().toString());
                    Log.i("state", state.getText().toString());
                    Log.i("radius", Double.toString(searchRadius_miles));

                    getFragmentManager().beginTransaction().replace(R.id.frame, fragment, "searchResultsFragment").addToBackStack("searchResultsFragment").commit();
                    //Toast.makeText(getActivity().getApplicationContext(), "Search Event", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Reset all search fields; Nuke it button
        reset = (Button) rootView.findViewById(R.id.resetButton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city.setText("");
                state.setText("");
                keyword.setText("");
                radioSortbyGroup.clearCheck();
                defaultButton.toggle();
                //fromTimeText.setText(R.string.anyTime);
                //toTimeText.setText(R.string.anyTime);
                fromDateText.setText(R.string.anyDay);
                toDateText.setText(R.string.anyDay);
                searchRadiusSeekBar.setProgress(0);
                //Toast.makeText(getActivity().getApplicationContext(), "Reset fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void userHomeToAddress(){
        //Get Address From Location
        Geocoder geoCoder = new Geocoder(this.context, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            ParseGeoPoint userHome = ParseUser.getCurrentUser().getParseGeoPoint("userHome");
            List<Address> address = geoCoder.getFromLocation(userHome.getLatitude(), userHome.getLongitude(), 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            String finalAddress = builder.toString(); //This is the complete address.

            setuserHomeAddress(finalAddress);
            //Toast.makeText(getApplicationContext(), finalAddress, Toast.LENGTH_SHORT).show();


        } catch (IOException e) {}
        catch (NullPointerException e) {}
    }


    @Override
    public void onStop() {
        super.onStop();
    }



    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public void registerForContextMenu(View v) {
            dateset = (EditText) v;
        }

        private EditText dateset;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar time = Calendar.getInstance();
            String am_pm, timeformat;

            time.set(Calendar.MONTH, month);
            time.set(Calendar.DATE, day);
            time.set(Calendar.YEAR, year);

            timeformat = (String) android.text.format.DateFormat.format("M/dd/yyyy", time);
            dateset.setText(timeformat);
        }

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {


        @Override
        public void registerForContextMenu(View v) {
            timeset = (EditText) v;
        }

        private EditText timeset;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default time in the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, false);
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            // Do something with the time chosen by the user
            String am_pm, minuteString;

            if ( hour > 12) {
                am_pm = "PM";
                hour -= 12;
            } else if ( hour == 12 ) {
                am_pm = "PM";
            } else if (hour == 0 ) {
                am_pm = "AM";
                hour = 12;
            } else{
                am_pm = "AM";
            }

            if ( minute < 10) {
                minuteString = "0"+Integer.toString(minute);
            } else {
                minuteString = Integer.toString(minute);
            }

            timeset.setText(hour + ":" + minuteString + " " + am_pm);
        }

    }

    public void onBackPressed() {
        Log.d("Test", "This is being called in maindrawer");
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
    }

    public void setuserHomeAddress(String userHome){
        home = userHome;
    }

    public String getUserHome(){
        return home;
    }


}
