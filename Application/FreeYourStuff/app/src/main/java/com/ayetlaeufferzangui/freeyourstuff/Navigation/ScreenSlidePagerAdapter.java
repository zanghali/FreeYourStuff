package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ayetlaeufferzangui.freeyourstuff.List.ListFragment;

/**
 * Created by lothairelaeuffer on 21/12/2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new ListFragment();
            case 1:
                return new ListFragment();
            default:
                return new ListFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
