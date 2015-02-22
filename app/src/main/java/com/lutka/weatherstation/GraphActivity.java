package com.lutka.weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.weather.lutka.weatherstation.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Paulina on 28/11/2014.
 */
public abstract class GraphActivity extends Fragment implements Response.ErrorListener
{
    RequestQueue requestQueue;
    static final String READINGS_URL = "http://weather.cs.nuim.ie/output.php";

    HashMap<DataPoint, Integer> dataPointsToTimeStamp = new HashMap<>();

    protected abstract void onResponse(ReadingsFeed response);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.graphs, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());

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

      return layout;
    }

    public static String convertUnixTimeToDate(int unixTime)
    {
             //Date d = new Date(timeStamp);
       // return (d.toString());
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis((long) unixTime * 1000);
        // has to deal with am/pm
        return ("Time: "+ calendar.get(Calendar.HOUR_OF_DAY)+":00"+ "\nDate: "+
                "\n"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+ (calendar.get(Calendar.MONTH)+1) +"/"+calendar.get(Calendar.YEAR));
    }

    public void drawGraph(List<Reading> readings,int readingsColor, List<Reading> forecast, int forecastColor, String measurementType)
    {
        GraphView graphView = (GraphView) getView().findViewById(R.id.dataGraph);

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
                Toast.makeText(getActivity(), "Readings:\n" + convertUnixTimeToDate(dataPointsToTimeStamp.get(dataPoint)) +"\nValue: "+dataPoint.getY(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Forecast:\n" +convertUnixTimeToDate(dataPointsToTimeStamp.get(dataPoint)) +"\nValue: "+dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });
        // add data
        graphView.addSeries(forecastSeries);

        // set view port, start=a, size=b
      // graphView.getViewport().setMinY(-5);
      // graphView.getViewport().setMaxY(25);

        //graphView.setLegendRenderer();

        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        //---set horizontal labels - could be good idea to calculate for how many day there is the data
        //graphView.getGridLabelRenderer().setNumHorizontalLabels(10);


        graphView.setTitle(measurementType);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);

    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        new AlertDialog.Builder(getActivity())
                .setMessage(error.getMessage())
                .show();
    }

    public DataPoint[] listOfValuesToDataPointArray(List<Reading> readings){

        DataPoint[] data = new DataPoint[readings.size()];

        int time;
        int value;
        DataPoint dataPoint;
        //long epoch = System.currentTimeMillis()/1000;
        for (int i = 0; i < readings.size(); i++)
        {
           time =readings.get(i).getTime();
            //time = readings.get(i).getTime();
            value = readings.get(i).getValue();

            dataPoint = new DataPoint(new Date ((long)(time)*1000), value);
            data[i] = dataPoint;

            dataPointsToTimeStamp.put(dataPoint, time );
        }
        return data;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        requestQueue.stop();
    }
}
