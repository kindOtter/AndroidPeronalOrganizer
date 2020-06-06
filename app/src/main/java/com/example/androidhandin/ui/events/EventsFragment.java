package com.example.androidhandin.ui.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhandin.Constants;
import com.example.androidhandin.ui.events.AddEdit.AddEditEventActivity;
import com.example.androidhandin.DBmodels.Event;
import com.example.androidhandin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.events_listOfEvents_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final EventsAdapter adapter = new EventsAdapter();
        recyclerView.setAdapter(adapter);
        eventsViewModel.getAllEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                adapter.submitList(events);
            }
        });
        FloatingActionButton fab = root.findViewById(R.id.floatingButtonAddEvent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddEditEventActivity();
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                eventsViewModel.deleteEvent(adapter.getEventAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Event deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new EventsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                Intent intent = new Intent(getContext(), AddEditEventActivity.class);
                intent.putExtra(Constants.EXTRA_ID, event.getId());
                intent.putExtra(Constants.EXTRA_DATE, event.getAlarmDate());
                intent.putExtra(Constants.EXTRA_TITLE, event.getTitle());
                intent.putExtra(Constants.EXTRA_DESCRIPTION, event.getDescription());
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.empty_menu, menu);
    }

    private void startAddEditEventActivity() {
        Context context = getContext();
        Class addEditEvent = AddEditEventActivity.class;
        Intent intent = new Intent(context, addEditEvent);
        startActivity(intent);
    }
}
