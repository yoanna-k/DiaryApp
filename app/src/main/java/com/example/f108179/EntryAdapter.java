package com.example.f108179;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private List<Entry> entries;

    public EntryAdapter(ArrayList<Entry> entries) {
        this.entries = entries;
    }
    private OnEntryClickListener onEntryClickListener;


    @NonNull
    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryAdapter.ViewHolder holder, int position) {
        Entry entry = entries.get(position);
        holder.titleTextView.setText(entry.getTitle());
        holder.contentTextView.setText(entry.getContent());
        holder.date.setText(entry.getDate());
        holder.btnDelete.setOnClickListener(v -> {
            if (onEntryClickListener != null) {
                onEntryClickListener.onDeleteClick(entry);
            }
        });
        holder.btnEdit.setOnClickListener(v -> {
            if (onEntryClickListener != null) {
                onEntryClickListener.onEditClick(entry);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (onEntryClickListener != null) {
                onEntryClickListener.onEntryClick(entry);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public void setOnEntryClickListener(OnEntryClickListener listener) {
        this.onEntryClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;
        public TextView date;
        private final ImageButton btnDelete;
        private final ImageButton btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            date = itemView.findViewById(R.id.dateTextView);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }

    }

    public interface OnEntryClickListener {
        void onDeleteClick(Entry entry);
        void onEditClick(Entry entry);
        void onEntryClick(Entry entry);
    }


}
