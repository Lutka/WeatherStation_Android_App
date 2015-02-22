package com.lutka.weatherstation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SectionIndexer;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.weather.lutka.weatherstation.R;

/**
 * Created by Paulina on 16/11/2014.
 */

public class MainActivity extends Activity implements Response.ErrorListener
{
    //adapter for keeping fragments
    SectionsPagerAdapter mSectionsPagerAdapter;
    //viewPager that will host the section contents
    ViewPager mViewPager;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.pager);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }

  @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

  @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        new AlertDialog.Builder(this)
                .setMessage(error.getMessage())
                .show();
    }
}
