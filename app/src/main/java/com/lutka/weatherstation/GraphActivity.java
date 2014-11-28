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
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
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

    public void drawGraph(List<Reading> readings, String measurementType)
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
        GraphViewSeries graphViewReadings =  new GraphViewSeries("Real data readings",
                new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(90, 250, 00),3), data);

        // graph with dynamically genereated horizontal and vertical labels
        GraphView graphView =  new LineGraphView(this, measurementType);

        // add data
        graphView.addSeries(graphViewReadings);
        // set view port, start=a, size=b
        graphView.setViewPort(readings.get(readingSize-num).getTime(), 10*(60*60));
        graphView.setScrollable(true);
        graphView.setScalable(true);//zooming

        graphView.setShowLegend(true);
        graphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);


        graphView.setLegendWidth(200);

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
