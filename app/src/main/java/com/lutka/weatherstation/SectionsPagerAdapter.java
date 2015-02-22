package com.lutka.weatherstation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * Created by Paulina on 21/02/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment;

        if (position == 0)
        {
            fragment = new TemperatureGraphActivity();
        }
        else
        {
            fragment = new HumidityGraphActivity();
        }

        Bundle args = new Bundle();
        args.putInt("page", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 2;
    }
}
