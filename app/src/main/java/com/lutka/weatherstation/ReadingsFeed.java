package com.lutka.weatherstation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Paulina on 16/11/2014.
 */
public class ReadingsFeed
{
    @SerializedName("TemperatureReadings")
    List<Reading> temperatureReadings;

    @SerializedName("HumidityReadings")
    List<Reading> humidityReadings;

    public List<Reading> getTemperatureReadings()
    {
        return temperatureReadings;
    }

    public List<Reading> getHumidityReadings()
    {
        return humidityReadings;
    }
}



