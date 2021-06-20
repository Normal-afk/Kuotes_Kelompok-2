package com.example.kuotes.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kuotes.model.quotes.QuotesResponse;
import com.example.kuotes.service.ApiMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesViewModel extends ViewModel {
    private ApiMain apiMain;

    private MutableLiveData<QuotesResponse> QuotesResult = new MutableLiveData<>();

    public void setQuotes(){
        if (this.apiMain == null){
            apiMain = new ApiMain();
        }
        apiMain.getApiQuotes().getQuotes().enqueue(new Callback<QuotesResponse>() {
            @Override
            public void onResponse(Call<QuotesResponse> call, Response<QuotesResponse> response) {
                QuotesResponse quotesResponse = response.body();
                if (quotesResponse != null && quotesResponse.getId() != null){
                    QuotesResult.postValue(quotesResponse);
                }
            }

            @Override
            public void onFailure(Call<QuotesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<QuotesResponse> getQuotes(){ return QuotesResult;}
}
