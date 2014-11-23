package com.lutka.weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.weather.lutka.weatherstation.R;

import java.util.List;

/**
 * Created by Paulina on 23/11/2014.
 */
public class WeatherGraphActivity extends Activity implements Response.ErrorListener
{
    RequestQueue requestQueue;
    static final String READINGS_URL = "http://weather.cs.nuim.ie/output.php";

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
                drawGraph(response.getTemperatureReadings());

            }
        };
        requestQueue.add(readingRequest);

       // int num = 100;
      //  GraphView.GraphViewData[] data = new GraphView.GraphViewData[];
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


    @Override
    public void onErrorResponse(VolleyError error)
    {
        new AlertDialog.Builder(this)
                .setMessage(error.getMessage())
                .show();
    }

    public void drawGraph(List<Reading> readings)
    {
        int num = 100;
        int readingSize = readings.size();
        //obsluzyc przypadek gdy liczba elementow jest mniejsza niz 100
        GraphView.GraphViewData[] data = new GraphView.GraphViewData[num];
        int j =0;
        for (int i = readingSize - num; i < readingSize; i++)
        {
            int time = readings.get(j).getTime();
            int value = readings.get(j).getValue();

            data[j] = new GraphView.GraphViewData(time, value);
            j++;
        }
        // graph with dynamically genereated horizontal and vertical labels
        GraphView graphView =  new LineGraphView(this, "Temperature");

        // add data
        graphView.addSeries(new GraphViewSeries(data));
        // set view port, start=2, size=40
        graphView.setViewPort(readings.get(readingSize-num).getTime(), 10*(60*60));
        graphView.setScrollable(true);
        LinearLayout layout = (LinearLayout) findViewById(R.id.temperatureGraph);
        layout.addView(graphView);


    }
}
