package com.cse120.ontask;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Get the calendar day, month, year
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        //Return the new Date Picker
        return new DatePickerDialog(getActivity(), (AddItemActivity)getActivity(), year, month, day);
    }

    //Function to retrieve the user set date which will be used in AddItemActivity
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
       //Overwritten By AddItemActivity...
    }
}