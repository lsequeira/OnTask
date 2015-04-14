package com.cse120.ontask;

import android.os.Bundle;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.cse120.ontask.AddTaskActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get the current hour and minute
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (AddTaskActivity)getActivity(), hour, minute, false);

    }
    //Function to retrieve the user set times which will be used in AddTaskActivity
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        //Overwritten By AddTaskActivity...
    }


}
