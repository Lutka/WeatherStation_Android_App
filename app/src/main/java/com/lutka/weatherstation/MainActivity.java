package com.lutka.weatherstation;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.weather.lutka.weatherstation.R;

/**
 * Created by Paulina on 16/11/2014.
 */

public class MainActivity extends Activity
{

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new HumidityGraphFragment();
        displayFragment(fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.temperature:
                Fragment temperatureFragment = new TemperatureGraphFragment();
                displayFragment(temperatureFragment);
                return true;

            case R.id.humidity:
                Fragment humidityFragment = new HumidityGraphFragment();
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

    public void displayFragment(Fragment fragment)
    {
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

}
