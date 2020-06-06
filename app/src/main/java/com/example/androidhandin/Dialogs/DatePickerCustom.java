package com.example.androidhandin.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidhandin.ui.events.AddEdit.AddEditEventActivityViewModel;

public class DatePickerCustom extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Calendar cal;
    private AddEditEventActivityViewModel viewModel;
    private TextView eventDate;
    private int year;
    private int month;
    private int day;

    public DatePickerCustom(Calendar calendar, AddEditEventActivityViewModel viewModel, TextView eventDate) {
        this.cal = calendar;
        this.viewModel = viewModel;
        this.eventDate = eventDate;
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        eventDate.setText(viewModel.formatDate(cal.getTime()));
    }
}
