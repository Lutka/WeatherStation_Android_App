
package com.lutka.weatherstation;

/**
 * A class which extends GraphFragment and calls drawGraph using humidity related values and parameters
 * @author Paulina
 * @version 1.0
 * @since 28/11/2014.
 */
public class HumidityGraphFragment extends GraphFragment
{
    @Override
    protected void onResponse(ReadingsFeed response)
    {
        drawGraph(response.getHumidityReadings(), 0xff00ff00, response.getHumidityForecast(), 0xffff0000,"Humidity");
    }
}