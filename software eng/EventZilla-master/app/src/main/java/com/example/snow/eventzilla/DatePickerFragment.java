package com.example.snow.eventzilla;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

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


    @Override
    public void registerForContextMenu(View v)
    {
        dateset = (EditText) v;
    }

}
