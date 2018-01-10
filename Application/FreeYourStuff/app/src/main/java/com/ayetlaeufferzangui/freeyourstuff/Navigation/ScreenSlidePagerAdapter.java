package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ayetlaeufferzangui.freeyourstuff.List.ListFragment;
import com.ayetlaeufferzangui.freeyourstuff.Map.MapsFragment;
import com.ayetlaeufferzangui.freeyourstuff.Settings.SettingsFragment;

/**
 * Created by lothairelaeuffer on 21/12/2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 3;
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new SettingsFragment();
            case 1:
                return new ListFragment();
            case 2:
                return new MapsFragment();
            default:
                return new ListFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
