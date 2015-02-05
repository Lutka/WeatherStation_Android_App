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

    public void drawGraph(List<Reading> readings,int colorReadings, List<Reading> forecast, int colorForecast, String measurementType)
    {
        DataPoint[] readingsData = listOfValuesToDataPointArray(readings);

        GraphView graphView = (GraphView) findViewById(R.id.dataGraph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(readingsData);

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        series.setColor(colorReadings);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(GraphActivity.this, "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        // add data
        graphView.addSeries(series);

       /* //to include second series
        int forecastSize = 100;//forecast.size();
        GraphView.GraphViewData[] data1 = new GraphView.GraphViewData[num];
        j = 0;
        for (int i = forecastSize - num; i < forecastSize; i++)
        {
            int time = forecast.get(i).getTime();
            int value = forecast.get(i).getValue();
            if(j < data.length)
            {
                data1[j] = new GraphView.GraphViewData(time, value);
                j++;
            }
        }
        GraphViewSeries graphViewForecast =  new GraphViewSeries("Forecast",
                new GraphViewSeries.GraphViewSeriesStyle(colorForecast, 3), data1);

        // graph with dynamically genereated horizontal and vertical labels
        //GraphView graphView1 =  new LineGraphView(this, measurementType);

        // add data
        graphView.addSeries(graphViewForecast);
        *//**end of second series*//*
*/

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
