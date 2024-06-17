package com.example.f108179;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryRepository {
    private final EntryDao entryDao;
    private final LiveData<List<Entry>> allEntries;

    public EntryRepository(Application application) {
        EntryDatabase db = EntryDatabase.getDatabase(application);
        entryDao = db.entryDao();
        allEntries = entryDao.getAllEntries();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }

    public void insertEntry(Entry entry) {
        EntryDatabase.databaseWriteExecutor.execute(() -> entryDao.insert(entry));
    }

    public void deleteEntry(Entry entry) {
        EntryDatabase.databaseWriteExecutor.execute(() -> entryDao.delete(entry));
    }

    public void editEntry(Entry entry) {
        EntryDatabase.databaseWriteExecutor.execute(() -> entryDao.update(entry));
    }
    public LiveData<Entry> getEntryById(int id) {
        return entryDao.getEntryById(id);
    }
    public LiveData<Entry> getEntryByDate(String date) {
        return entryDao.getEntryByDate(date);
    }
}
