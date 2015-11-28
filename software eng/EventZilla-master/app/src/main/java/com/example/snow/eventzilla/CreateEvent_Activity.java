package com.example.snow.eventzilla;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by Adam on 4/25/2015.
 */
public class CreateEvent_Activity extends Activity {

    // possible to change fields,
    private EditText startTime, endTime, startDate, endDate, eventName1, eventDescription,eventName, eventLocation, location, description;
    private DialogFragment timeFragment, dateFragment;
    private Button createButton;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.event_creation);

        timeFragment = new TimePickerFragment();
        dateFragment = new DatePickerFragment();

        /* create text field grabbers */
        //assignAssistant = (EditText)findViewById(R.id.event_creation_spinner);


        /* two button listeners for create event and cancel */
        //Button createButton = (button) findViewById(R.id.);

        startTime = (EditText)findViewById(R.id.event_creation_start_time_edit_text);
        endTime = (EditText)findViewById(R.id.event_creation_end_time_edit_text);
        startDate = (EditText)findViewById(R.id.event_creation_start_date_edit_text);
        endDate = (EditText)findViewById(R.id.event_creation_end_date_edit_text);
        location = (EditText) findViewById(R.id.event_creation_location_edit_text);
        eventName1 = (EditText) findViewById(R.id.eventNameEditText);
        description = (EditText) findViewById(R.id.event_creation_description_edit_text);
        createButton = (Button) findViewById(R.id.event_creation_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Inform the user the button has been clicked
                //add event to the database

                ParseObject event = new ParseObject("EventTable");
                event.put("eventDate",startDate.getText().toString());
                event.put("eventEndDate", endDate.getText().toString());
                event.put("time", startTime.getText().toString());
                event.put("endTime", endTime.getText().toString());
                event.put("eventName", eventName1.getText().toString());
                event.put("description", description.getText().toString());
                event.put("location",location.getText().toString());

                event.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null)
                        {
                            Toast.makeText(getApplicationContext(), "Event Saved!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFragment.registerForContextMenu(startTime);
                timeFragment.show(getFragmentManager(), "timePicker");
                }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFragment.registerForContextMenu(endTime);
                timeFragment.show(getFragmentManager(), "timePicker");
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFragment.registerForContextMenu(startDate);
                dateFragment.show(getFragmentManager(), "datePicker");
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFragment.registerForContextMenu(endDate);
                dateFragment.show(getFragmentManager(), "datePicker");
            }
        });



    }



}
