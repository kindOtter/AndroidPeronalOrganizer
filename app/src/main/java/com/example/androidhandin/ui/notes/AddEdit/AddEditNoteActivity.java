package com.example.androidhandin.ui.notes.AddEdit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidhandin.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "androidhandin.AddEditNoteActivity.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "androidhandin.AddEditNoteActivity.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE = "androidhandin.AddEditNoteActivity.EXTRA_DATE";
    public static final String EXTRA_ID = "androidhandin.AddEditNoteActivity.EXTRA_ID";

    private AddEditNoteViewModel viewModel;
    private EditText titleView;
    private TextInputEditText descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        TextView headerView = findViewById(R.id.addNoteTextView);
        titleView = findViewById(R.id.addNoteTitle);
        descriptionView = findViewById((R.id.addNoteDescription));
        viewModel = new ViewModelProvider(this).get(AddEditNoteViewModel.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            headerView.setText(getString(R.string.edit_note_date) + " " + intent.getStringExtra(EXTRA_DATE));
            titleView.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionView.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Add note");
        }
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
                createNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNote() {
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();
        if(title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_LONG).show();
            return;
        }
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id == -1) {
            viewModel.createNote(title, description);
        }
        else {
            viewModel.updateNote(id, title, description);
        }
        setResult(RESULT_OK);
        Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show();
        finish();
    }
}
