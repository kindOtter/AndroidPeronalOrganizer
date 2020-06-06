package com.example.androidhandin.Dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidhandin.ui.events.AddEdit.AddEditEventActivityViewModel;

public class TimePickerCustom extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private Calendar cal;
    private AddEditEventActivityViewModel viewModel;
    private TextView eventTime;
    private int hour;
    private int minute;

    public TimePickerCustom(Calendar calendar, AddEditEventActivityViewModel viewModel, TextView eventTime) {
        this.cal = calendar;
        this.viewModel = viewModel;
        this.eventTime = eventTime;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        eventTime.setText(viewModel.formatTime(cal.getTime()));
    }
}
