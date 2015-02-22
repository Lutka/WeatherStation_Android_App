
package com.lutka.weatherstation;

/**
 * Created by Paulina on 28/11/2014.
 */
public class HumidityGraphActivity extends GraphActivity
{
    @Override
    protected void onResponse(ReadingsFeed response)
    {
        //readings
        drawGraph(response.getHumidityReadings(), 0xff00ff00, response.getHumidityForecast(), 0xffff0000,"Humidity");
    }
}
