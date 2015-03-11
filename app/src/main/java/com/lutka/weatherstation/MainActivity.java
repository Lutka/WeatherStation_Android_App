package com.lutka.weatherstation;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.weather.lutka.weatherstation.R;

/**
 * This activity is created when an application starts,
 * it takes care of switching between different Fragment types: humidity, temperature
 *
 * @author Paulina
 * @version 1.0
 * @since 16/11/2014.
 */

public class MainActivity extends Activity
{
    Fragment temperatureFragment,
            humidityFragment;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureFragment = new TemperatureGraphFragment();
        humidityFragment = new HumidityGraphFragment();
        displayFragment(temperatureFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //switch for displaying the right fragment
        switch(item.getItemId())
        {
            case R.id.temperature:
                displayFragment(temperatureFragment);
                return true;

            case R.id.humidity:
                displayFragment(humidityFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method displays the requested fragment, depending on user choice is gonna be:
     * temperature or humidity graph
     *
     *@param fragment can be either humidityFragment of temperatureFragment
    */
    public void displayFragment(Fragment fragment)
    {
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

}