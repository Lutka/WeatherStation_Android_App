package com.lutka.weatherstation;

/**
 * Created by Paulina on 23/11/2014.
 */
public class TemperatureGraphFragment extends GraphActivity
{
    @Override
    protected void onResponse(ReadingsFeed response)
    {
        drawGraph(response.getTemperatureReadings(), 0xff00ff00, response.getTemperatureForecast(),0xffff0000, "Temperature");        //forecast
    }
}
