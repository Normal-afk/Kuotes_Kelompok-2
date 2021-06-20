package com.example.kuotes.view;

import android.os.AsyncTask;

import com.example.kuotes.entity.AppDatabase;
import com.example.kuotes.entity.DataQuotes;

import java.util.List;

public class MainPresenter implements MainContact.presenter {

    private MainContact.homeView hView;
    private MainContact.favoView fView;

    public MainPresenter(MainContact.favoView fView) {
        this.fView = fView;
    }

    public MainPresenter(MainContact.homeView hView) {
        this.hView = hView;
    }

    class InsertData extends AsyncTask<Void,Void,Long>{

        private AppDatabase appDatabase;
        private DataQuotes dataQuotes;

        public InsertData(AppDatabase appDatabase, DataQuotes dataQuotes) {
            this.appDatabase = appDatabase;
            this.dataQuotes = dataQuotes;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return appDatabase.dao().insertData(dataQuotes);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            hView.successAdd();
        }
    }

    @Override
    public void insertData(String quote, String author, AppDatabase appDatabase) {
        final DataQuotes dataQuotes = new DataQuotes();
        dataQuotes.setQuote(quote);
        dataQuotes.setAuthor(author);
        new InsertData(appDatabase,dataQuotes).execute();
    }

    @Override
    public void readData(AppDatabase appDatabase) {
        List<DataQuotes> list;
        list = appDatabase.dao().getData();
        fView.getData(list);
    }

    class DeleteData extends AsyncTask<Void,Void,Long>{

        private AppDatabase appDatabase;
        private DataQuotes dataQuotes;

        public DeleteData(AppDatabase appDatabase, DataQuotes dataQuotes) {
            this.appDatabase = appDatabase;
            this.dataQuotes = dataQuotes;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            appDatabase.dao().deleteData(dataQuotes);
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            fView.successDelete();
        }
    }

    @Override
    public void deleteData(DataQuotes dataQuotes, AppDatabase appDatabase) {
        new DeleteData(appDatabase,dataQuotes).execute();
    }
}
