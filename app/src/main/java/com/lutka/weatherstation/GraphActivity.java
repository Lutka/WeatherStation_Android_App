package com.lutka.weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
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
        int num = 100;
        int readingSize = 100;//readings.size();
        //obsluzyc przypadek gdy liczba elementow jest mniejsza niz 100


        /*
        // GraphView 4.x
GraphView graph = new GraphView(this);
LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
graph.addSeries(series);
 */



        DataPoint[] data = new DataPoint[num];
        int j = 0;
        for (int i = readingSize - num; i < readingSize; i++)
        {
            int time = readings.get(i).getTime();
            int value = readings.get(i).getValue();
            if(j < data.length)
            {
                data[j] = new DataPoint(time, value);
                j++;
            }
        }
       // GraphViewSeries graphViewReadings =  new GraphViewSeries("Readings",
          //      new GraphViewSeries.GraphViewSeriesStyle(colorReadings, 3), data);

        // graph with dynamically genereated horizontal and vertical labels
        GraphView graphView =  new GraphView(this);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);

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

       // graphView.setViewPort(readings.get(readingSize-num).getTime(), 10*(60*60));
       // graphView.setScrollable(true);
        //graphView.setScalable(true);//zooming

        //graphView.setLegendRenderer();
        graphView.setTitle(measurementType);

        LinearLayout layout = (LinearLayout) findViewById(R.id.dataGraph);

        layout.addView(graphView);
    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        new AlertDialog.Builder(this)
                .setMessage(error.getMessage())
                .show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        requestQueue.stop();
    }
}
