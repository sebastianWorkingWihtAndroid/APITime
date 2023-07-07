package com.time.apitime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private Button getTemperatureButton;
    private TextView temperatureTextView;

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "ae4ca62fa84fff267efe66b34d4fdb31";

    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        getTemperatureButton = findViewById(R.id.getTemperatureButton);
        temperatureTextView = findViewById(R.id.temperatureTextView);

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        weatherService = retrofit.create(WeatherService.class);

        getTemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = Double.parseDouble(latitudeEditText.getText().toString());
                double longitude = Double.parseDouble(longitudeEditText.getText().toString());

                Call<WeatherResponse> call = weatherService.getWeather(latitude, longitude, API_KEY,"metric");
                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            WeatherResponse weatherResponse = response.body();
                            if (weatherResponse != null) {
                                Temperature temperature = weatherResponse.getTemperature();
                                double temp = temperature.getTemp();
                                temperatureTextView.setText(String.format("%.1f°C", temp));
                            }
                        } else {
                            try {
                                String errorResponse = response.errorBody().string();
                                // Manejar el error de la API
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
            }
        });
    }
}