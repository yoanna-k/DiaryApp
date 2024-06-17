package com.example.f108179;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EntryViewModel entryViewModel;
    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Scheduler.scheduleDailyReminder(this);

        FloatingActionButton btnStart = findViewById(R.id.btnStart);
        ImageButton btnCalendar = findViewById(R.id.btn_calendar);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEntries);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEntryActivity.class);
            startActivity(intent);
        });

        btnCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HolidayActivity.class);
            startActivity(intent);
        });

        adapter = new EntryAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        entryViewModel.getAllEntries().observe(this, entries -> {
            if (entries != null) {
                adapter.setEntries(entries);
            }
        });


        adapter.setOnEntryClickListener(new EntryAdapter.OnEntryClickListener() {
            @Override
            public void onEditClick(Entry entry) {
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                if(entry.getDate().equals(date)) {
                    Intent intent = new Intent(MainActivity.this, AddEntryActivity.class);
                    intent.putExtra("entry_id", entry.getId());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Entry can't be edited anymore", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDeleteClick(Entry entry) {
                entryViewModel.deleteEntry(entry);
                Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEntryClick(Entry entry) {
                EntryDetailFragment fragment = EntryDetailFragment.newInstance(entry.getTitle(), entry.getContent(), entry.getDate());

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
            }
        });
    }

}