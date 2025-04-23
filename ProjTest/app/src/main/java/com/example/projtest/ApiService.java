package com.example.projtest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("query")
    Call<AlphaVantageResponse> getMutualFundData(
            @Query("apikey") String apiKey,
            @Query("symbol") String symbol,
            @Query("function") String function,
            @Query("datatype") String dataType
    );

    // Overloaded method with default values
    default Call<AlphaVantageResponse> getMutualFundData(String apiKey, String symbol) {
        return getMutualFundData(apiKey, symbol, "TIME_SERIES_DAILY", "json");
    }
}