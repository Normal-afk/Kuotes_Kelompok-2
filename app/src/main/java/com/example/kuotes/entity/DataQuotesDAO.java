package com.example.kuotes.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataQuotesDAO {
    @Insert
    Long insertData(DataQuotes dataQuotes);

    @Query("Select * from quotes_table")
    List<DataQuotes> getData();

    @Delete
    void deleteData(DataQuotes item);
}
