package com.example.f108179;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Entry entry);

    @Query("SELECT * FROM entry_table")
    LiveData<List<Entry>> getAllEntries();

    @Delete
    void delete(Entry entry);

    @Update
    void update(Entry entry);

    @Query("SELECT * FROM entry_table WHERE date = :date LIMIT 1")
    LiveData<Entry> getEntryByDate(String date);

    @Query("SELECT * FROM entry_table WHERE id = :id LIMIT 1")
    LiveData<Entry> getEntryById(int id);
}