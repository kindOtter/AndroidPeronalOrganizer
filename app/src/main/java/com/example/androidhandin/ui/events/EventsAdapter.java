package com.example.androidhandin.ui.events;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhandin.DBmodels.Event;
import com.example.androidhandin.R;

import java.util.Calendar;
import java.util.logging.SimpleFormatter;

public class EventsAdapter extends ListAdapter<Event, EventsAdapter.EventHolder> {

    private OnItemClickListener listener;

    protected EventsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getAlarmDate() == newItem.getAlarmDate();
        }
    };

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        final Event currentEvent = getItem(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentEvent.getAlarmDate());
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        holder.eventViewTitle.setText(currentEvent.getTitle());
        holder.eventViewDate.setText(simpleFormatter.format(calendar.getTime()));

    }

    public Event getEventAt(int position) {
        return getItem(position);
    }

    class EventHolder extends RecyclerView.ViewHolder {
        private TextView eventViewTitle;
        private TextView eventViewDate;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventViewTitle = itemView.findViewById(R.id.event_view_title);
            eventViewDate = itemView.findViewById(R.id.event_view_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
