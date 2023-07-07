package com.time.apitime;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("main")
    private Temperature temperature;

    public Temperature getTemperature() {
        return temperature;
    }
}
