package com.example.f108179;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HolidayActivity extends AppCompatActivity {
    private TextView holidaysTextView;
    private HolidayService holidayService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_layout);

        ImageButton btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v -> finish());

        holidaysTextView = findViewById(R.id.listHolidays);

        Retrofit retrofit = ApiClient.getClient();
        holidayService = retrofit.create(HolidayService.class);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        Locale currentLocale = Locale.getDefault();
        String countryCode = currentLocale.getCountry();

        fetchHolidays(countryCode, year);
    }

    private void fetchHolidays(String country, int year) {
        Call<HolidayResponse> call = holidayService.getHolidays("ZOXNmNdB8B0GhNoCvkFNSOpEoXxma87v", country, year);

        call.enqueue(new Callback<HolidayResponse>() {
            @Override
            public void onResponse(@NonNull Call<HolidayResponse> call, @NonNull Response<HolidayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HolidayResponse.Holiday> holidays = response.body().getResponse().getHolidays();
                    StringBuilder holidayText = new StringBuilder();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date currentDate = new Date();

                    for (HolidayResponse.Holiday holiday : holidays) {
                        try {
                            Date holidayDate = sdf.parse(holiday.getDate().getIso());
                            if (holidayDate != null && holidayDate.after(currentDate)) {
                                holidayText.append(holiday.getName()).append(": ").append(holiday.getDate().getIso()).append("\n");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    holidaysTextView.setText(holidayText.toString());
                } else {
                    Toast.makeText(HolidayActivity.this, "Error fetching holidays", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HolidayResponse> call, @NonNull Throwable t) {
                Toast.makeText(HolidayActivity.this, "Error fetching holidays", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
