package com.lutka.weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import com.weather.lutka.weatherstation.R;

import java.util.List;

/**
 * Created by Paulina on 28/11/2014.
 */
public abstract class GraphActivity extends Activity implements Response.ErrorListener
{
    RequestQueue requestQueue;
    static final String READINGS_URL = "http://weather.cs.nuim.ie/output.php";

    protected abstract void onResponse(ReadingsFeed response);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        GsonRequest<ReadingsFeed> readingRequest = new GsonRequest<ReadingsFeed>(READINGS_URL, this, ReadingsFeed.class)
        {
            @Override
            protected void deliverResponse(ReadingsFeed response)
            {
                super.deliverResponse(response);
                onResponse(response);
            }
        };
        requestQueue.add(readingRequest);

        // int num = 100;
        //  GraphView.GraphViewData[] data = new GraphView.GraphViewData[];
    }

    public void drawGraph(List<Reading> readings,int readingsColor, List<Reading> forecast, int forecastColor, String measurementType)
    {
        GraphView graphView = (GraphView) findViewById(R.id.dataGraph);

        DataPoint[] readingsData = listOfValuesToDataPointArray(readings);
        LineGraphSeries<DataPoint> readingsSeries = new LineGraphSeries<>(readingsData);

        readingsSeries.setDrawDataPoints(true);
        readingsSeries.setDataPointsRadius(10);
        readingsSeries.setThickness(8);
        readingsSeries.setColor(readingsColor);

        readingsSeries.setOnDataPointTapListener(new OnDataPointTapListener()
        {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint)
            {
                Toast.makeText(GraphActivity.this, "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        // add data
        graphView.addSeries(readingsSeries);

        DataPoint[] forecastData = listOfValuesToDataPointArray(forecast);
        LineGraphSeries<DataPoint> forecastSeries = new LineGraphSeries<>(forecastData);

        forecastSeries.setDrawDataPoints(true);
        forecastSeries.setDataPointsRadius(10);
        forecastSeries.setThickness(8);
        forecastSeries.setColor(forecastColor);

        forecastSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(GraphActivity.this, "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        // add data
        graphView.addSeries(forecastSeries);

        // set view port, start=a, size=b
      // graphView.getViewport().setMinY(-5);
      // graphView.getViewport().setMaxY(25);

        //graphView.setLegendRenderer();
        graphView.setTitle(measurementType);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);

    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        new AlertDialog.Builder(this)
                .setMessage(error.getMessage())
                .show();
    }

    public DataPoint[] listOfValuesToDataPointArray(List<Reading> readings){

        DataPoint[] data = new DataPoint[readings.size()];

        int time;
        int value;
        for (int i = 0; i < readings.size(); i++)
        {
            time = readings.get(i).getTime();
            value = readings.get(i).getValue();

            data[i] = new DataPoint(time, value);
        }
        return data;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        requestQueue.stop();
    }


}
