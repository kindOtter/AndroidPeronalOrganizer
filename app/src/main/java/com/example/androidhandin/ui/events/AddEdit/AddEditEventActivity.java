package com.example.androidhandin.ui.events.AddEdit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidhandin.Constants;
import com.example.androidhandin.Dialogs.DatePickerCustom;
import com.example.androidhandin.Dialogs.TimePickerCustom;
import com.example.androidhandin.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditEventActivity extends AppCompatActivity {

    private AddEditEventActivityViewModel viewModel;
    private EditText eventTitle;
    private TextInputEditText eventNotes;
    private TextView eventDate;
    private TextView eventTime;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);
        viewModel = new ViewModelProvider(this).get(AddEditEventActivityViewModel.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        Intent intent = getIntent();
        eventTitle = findViewById(R.id.addEventTitle);
        eventNotes = findViewById(R.id.addEventDescription);
        eventDate = findViewById(R.id.addEvent_date);
        eventTime = findViewById(R.id.addEvent_time);
        if(intent.hasExtra(Constants.EXTRA_ID)) {
            setTitle("Edit Event");
            calendar.setTimeInMillis(intent.getLongExtra(Constants.EXTRA_DATE, calendar.getTimeInMillis()));
            eventTitle.setText(intent.getStringExtra(Constants.EXTRA_TITLE));
            eventNotes.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION));
            eventDate.setText(viewModel.formatDate(calendar.getTime()));
            eventTime.setText(viewModel.formatTime(calendar.getTime()));

        } else {
            setTitle("Add Event");
            eventDate.setText(viewModel.formatDate(calendar.getTime()));
            eventTime.setText(viewModel.formatTime(calendar.getTime()));
        }
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatePicker();
            }
        });
        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTimePicker();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                createUpdateEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayDatePicker() {
        DatePickerCustom datePickerCustom = new DatePickerCustom(calendar, viewModel, eventDate);
        datePickerCustom.show(getSupportFragmentManager(), "Date Picker");
    }

    private void displayTimePicker() {
        TimePickerCustom timePickerCustom = new TimePickerCustom(calendar, viewModel, eventTime);
        timePickerCustom.show(getSupportFragmentManager(), "Time Picker");
    }

    private void createUpdateEvent() {
        String title = eventTitle.getText().toString();
        if(title.isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_LONG).show();
            return;
        }
        if(getIntent().hasExtra(Constants.EXTRA_ID)) {
            viewModel.updateEvent(getIntent().getIntExtra(Constants.EXTRA_ID, -1), title, eventNotes.getText().toString(), calendar.getTimeInMillis());
        } else {
            viewModel.createEvent(title, eventNotes.getText().toString(), calendar.getTimeInMillis());
        }
        CalendarEventDialog calendarEventDialog = new CalendarEventDialog(this, title, calendar.getTimeInMillis());
        calendarEventDialog.show(getSupportFragmentManager(), "Calendar dialog");
    }

    public void goToTheCalendar(String title, long date) {
        java.util.Calendar calendarEvent = java.util.Calendar.getInstance();
        calendarEvent.setTimeInMillis(date);
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", title);
        startActivity(i);
        finish();
    }

    public static class CalendarEventDialog extends DialogFragment {

        private AddEditEventActivity activity;
        private String title;
        private long date;

        public CalendarEventDialog(AddEditEventActivity addEditEventActivity, String title, long date) {
            this.activity = addEditEventActivity;
            this.title = title;
            this.date = date;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.event_dialog_question)
                    .setPositiveButton(R.string.event_dialog_positive, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            activity.goToTheCalendar(title, date);
                        }
                    })
                    .setNegativeButton(R.string.event_dialog_negative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(activity, "Event saved", Toast.LENGTH_LONG).show();
                            activity.finish();
                        }
                    });
            return builder.create();
        }
    }
}
