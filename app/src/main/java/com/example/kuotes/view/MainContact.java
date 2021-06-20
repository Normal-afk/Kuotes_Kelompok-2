package com.example.kuotes.view;

import com.example.kuotes.entity.AppDatabase;
import com.example.kuotes.entity.DataQuotes;

import java.util.List;

public interface MainContact {
    interface homeView{
        void successAdd();
    }

    interface favoView{
        void successDelete();
        void getData(List<DataQuotes> list);
        void deleteData(DataQuotes item);
    }

    interface presenter{
        void insertData(String quote, String author, AppDatabase appDatabase);
        void readData(AppDatabase appDatabase);
        void deleteData(DataQuotes dataQuotes, AppDatabase appDatabase);
    }
}
