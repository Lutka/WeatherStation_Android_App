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
    public void onErrorResponse(VolleyError error)
    {
        new AlertDialog.Builder(this)
                .setMessage(error.getMessage())
                .show();
    }
}
