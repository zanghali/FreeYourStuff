package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ayetlaeufferzangui.freeyourstuff.List.ListFragment;
import com.ayetlaeufferzangui.freeyourstuff.Map.MapsFragment;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.ayetlaeufferzangui.freeyourstuff.Settings.*;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationActivity extends FragmentActivity {


    private static final String TAG = "NavigationActivity";

    private List<Item> listItem;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ProgressBar progressBar;

    private int[] tabIcons = {
            R.drawable.ic_settings_black_24dp,
            R.drawable.ic_list_black_24dp,
            R.drawable.ic_map_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        viewPager = findViewById(R.id.viewpager);


        tabLayout = findViewById(R.id.tab_layout);


        new GetItemListTask().execute();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment( SettingsFragment.newInstance(), "Settings");
        adapter.addFragment( ListFragment.newInstance(listItem), "List");
        adapter.addFragment( MapsFragment.newInstance(listItem), "Maps");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private class GetItemListTask extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected void onPreExecute() {
            // Show Progress bar
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Item> doInBackground(Void... voids) {
            List<Item> items = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);


                items = service.getItemByFilterGeo("45.741284,4.862928", "50000000").execute().body();
                //items = service.getItemList().execute().body();



            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return items;
        }


        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);

            listItem =items;

            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();


            progressBar.setVisibility(View.INVISIBLE);



        }
    }

}
