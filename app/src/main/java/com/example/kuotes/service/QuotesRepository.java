package com.example.kuotes.service;

import com.example.kuotes.model.quotes.QuotesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuotesRepository {
    @GET("/random")
    Call<QuotesResponse> getQuotes();
}
