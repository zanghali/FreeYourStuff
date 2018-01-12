package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.ayetlaeufferzangui.freeyourstuff.List.ListAdapter;
import com.ayetlaeufferzangui.freeyourstuff.List.ListFragment;
import com.ayetlaeufferzangui.freeyourstuff.List.ListRecyclerView;
import com.ayetlaeufferzangui.freeyourstuff.Map.MapsFragment;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.ayetlaeufferzangui.freeyourstuff.Settings.SettingsFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lothairelaeuffer on 21/12/2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ScreenSlidePagerAdapter";
    private static final int NUM_PAGES = 3;

    private List<Item> listItem;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<Item> listItem) {
        super(fm);

        this.listItem= listItem;

    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new SettingsFragment();
            case 1:
                return ListFragment.newInstance(listItem);
            case 2:
                return MapsFragment.newInstance(listItem);
            default:
                return ListFragment.newInstance(listItem);
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }


}
