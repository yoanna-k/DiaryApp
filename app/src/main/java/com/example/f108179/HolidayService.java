package com.example.f108179;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HolidayService {
    @GET("holidays")
    Call<HolidayResponse> getHolidays(
            @Query("api_key") String apiKey,
            @Query("country") String country,
            @Query("year") int year
    );
}
