package com.example.f108179;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private final EntryRepository repository;
    private final LiveData<List<Entry>> allEntries;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        repository = new EntryRepository(application);
        allEntries = repository.getAllEntries();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }

    public void insertEntry(Entry entry) {
        repository.insertEntry(entry);
    }

    public void deleteEntry(Entry entry) {
        repository.deleteEntry(entry);
    }

    public void editEntry(Entry entry) {
        repository.editEntry(entry);
    }

    public LiveData<Entry> getEntryById(int id) { return repository.getEntryById(id); }

    public LiveData<Entry> getEntryByDate(String date) { return repository.getEntryByDate(date); }
}
