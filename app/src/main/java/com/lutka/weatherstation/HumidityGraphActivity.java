
package com.lutka.weatherstation;

/**
 * Created by Paulina on 28/11/2014.
 */
public class HumidityGraphActivity extends GraphActivity
{
    @Override
    protected void onResponse(ReadingsFeed response)
    {
        drawGraph(response.getHumidityReadings(), "Humidity");
    }
}
