package com.lutka.weatherstation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.weather.lutka.weatherstation.R;

/**
 * A class which extends GraphFragment and calls drawGraph using temperature related values and parameters
 *
 * @author Paulina
 * @version 1.0
 * @since 23/11/2014.
 */
public class TemperatureGraphFragment extends GraphFragment
{
    @Override
    protected void onResponse(ReadingsFeed response)
    {
        drawGraph(response.getTemperatureReadings(), 0xff00ff00, response.getTemperatureForecast(),0xffff0000, "Temperature");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        TextView reliabilityFigure = (TextView) view.findViewById(R.id.reliablityFigure);
        reliabilityFigure.setText("reliability : 75%");
    }
}