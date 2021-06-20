package com.example.kuotes.entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {DataQuotes.class})//ambil entitas dari kelas DataQuotes
public abstract class AppDatabase extends RoomDatabase { //di-extends RoomDatabase
    public abstract DataQuotesDAO dao();
    private static AppDatabase appDatabase;

    public static AppDatabase iniDB(Context context) {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "dbQuotes").allowMainThreadQueries().build(); //nama databasenya dbQuotes
        return appDatabase;
    }
}

