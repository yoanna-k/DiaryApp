package com.example.f108179;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Entry.class}, version = 1, exportSchema = false)
public abstract class EntryDatabase extends RoomDatabase {
    public abstract EntryDao entryDao();

    private static volatile EntryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static EntryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EntryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EntryDatabase.class, "entry__database_")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
