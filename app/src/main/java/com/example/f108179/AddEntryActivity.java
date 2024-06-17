package com.example.f108179;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEntryActivity extends AppCompatActivity {
    private EditText titleEntry;
    private EditText contentEntry;
    private EntryViewModel entryViewModel;
    private int entryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        titleEntry = findViewById(R.id.titleEntry);
        contentEntry = findViewById(R.id.contentEntry);
        ImageView btnSaveEntry = findViewById(R.id.btnSaveEntry);

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        if (getIntent().hasExtra("entry_id")) {
            entryId = getIntent().getIntExtra("entry_id", -1);
            entryViewModel.getEntryById(entryId).observe(this, entry -> {
                if (entry != null) {
                    titleEntry.setText(entry.getTitle());
                    contentEntry.setText(entry.getContent());
                }
            });
        }

        ImageView btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> finish());

        btnSaveEntry.setOnClickListener(v -> {
            String title = titleEntry.getText().toString().trim();
            String content = contentEntry.getText().toString().trim();
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(AddEntryActivity.this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                // Check if an entry already exists for today
                entryViewModel.getEntryByDate(currentDate).observe(AddEntryActivity.this, existingEntry -> {
                    if (existingEntry != null && entryId == -1) {
                        Toast.makeText(AddEntryActivity.this, "An entry for today already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Entry entry = new Entry(title, content, currentDate);
                        if (entryId != -1) {
                            entry.setId(entryId);
                            entryViewModel.editEntry(entry);
                        } else {
                            entryViewModel.insertEntry(entry);
                        }
                        finish();
                    }
                });
            }
        });
    }
}