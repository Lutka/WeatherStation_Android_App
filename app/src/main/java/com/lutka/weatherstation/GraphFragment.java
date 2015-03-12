package com.lutka.weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
 * This class is a super class for TemperatureGraphFragment and HumidityGraphFragment classes
 *
 * @author Paulina
 * @version 1.0
 * @since 28/11/2014.
 */
public abstract class GraphFragment extends Fragment implements Response.ErrorListener
{
    RequestQueue requestQueue;
    static final String READINGS_URL = "http://weather.cs.nuim.ie/output.php";

    HashMap<DataPoint, Integer> dataPointsToTimeStamp = new HashMap<>();
    ReadingsFeed response = null;

    protected abstract void onResponse(ReadingsFeed response);

    @Override
    public void onDestroyView()
    {
        //cancel any requests this might have been sent to avoid crashes
        //want to graph response on a fragment with no longer exists
        requestQueue.cancelAll(this);
        super.onDestroyView();
        dataPointsToTimeStamp.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.graphs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if(response != null)
        {
            onResponse(response);
        }
        else
        {
            //assing values returned by the Gson library to the GsonRequest<ReadingsFeed> structure
            GsonRequest<ReadingsFeed> readingRequest = new GsonRequest<ReadingsFeed>(READINGS_URL, this, ReadingsFeed.class)
            {
                @Override
                protected void deliverResponse(ReadingsFeed resReadingsFeed)
                {
                    super.deliverResponse(resReadingsFeed);
                    response = resReadingsFeed;
                    onResponse(resReadingsFeed);
                }
            };
            requestQueue.add(readingRequest);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
    }

    /**
     * This method converts integer value of the unix time representation to the human readable format,
     * Hour:Minutes, Day/Month/Year
     * @param unixTime integer representation of the time since 1/01/1970
     * @return string
     */
    public static String convertUnixTimeToDate(int unixTime)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) unixTime * 1000);
        // has to deal with am/pm
        return ("Time: "+ calendar.get(Calendar.HOUR_OF_DAY)+":00"+ "\nDate: "+
                "\n"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+ (calendar.get(Calendar.MONTH)+1) +"/"+calendar.get(Calendar.YEAR));
    }

    /**
     * This method takes in list of Readings and display the data on the graph
     *
     * @param readings real reading values particular type (temp, humidity)
     * @param readingsColor chosen color for displaying readings series
     * @param forecast forecast values of particular type (temp, humidity)
     * @param forecastColor chosen color for displaying forecast series
     * @param measurementType can be either (temperature or humidity)
     */

    public void drawGraph(List<Reading> readings,int readingsColor, List<Reading> forecast, int forecastColor, String measurementType)
    {
        GraphView graphView = (GraphView) getView().findViewById(R.id.dataGraph);

        DataPoint[] readingsData = listOfValuesToDataPointArray(readings);
        LineGraphSeries<DataPoint> readingsSeries = new LineGraphSeries<>(readingsData);

        readingsSeries.setDrawDataPoints(true);
        readingsSeries.setDataPointsRadius(6);
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
        forecastSeries.setDataPointsRadius(6);
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
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        //graph setup
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

    /**
     * This method builds builds up DataPoint array (to be displayed on the graph)
     * and dataPointsToTimeStamp hashMap(to be able to display human readable data format, when click on graph recorded)
     * using in both cases list of readings
     *
     * @param readings list of Readings: temperature-forecast, humidity-real readings, etc.
     * @return data DataPoint array type needed in order to graph the data
     */
    public DataPoint[] listOfValuesToDataPointArray(List<Reading> readings){

        DataPoint[] data = new DataPoint[readings.size()];

        int time;
        int value;
        DataPoint dataPoint;
        for (int i = 0; i < readings.size(); i++)
        {
            time =readings.get(i).getTime();
            value = readings.get(i).getValue();

            dataPoint = new DataPoint(new Date ((long)(time)*1000), value);
            data[i] = dataPoint;

            dataPointsToTimeStamp.put(dataPoint, time);
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