package com.time.apitime;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("temp")
    private double temp;

    public double getTemp() {
        return temp;
    }
}
