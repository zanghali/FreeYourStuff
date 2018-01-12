package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationActivity extends FragmentActivity {


    private static final String TAG = "NavigationActivity";
    public static PagerAdapter mPagerAdapter;
    private ViewPager vPager;

    private List<Item> listItem;

    private ProgressBar progressBar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new GetItemListTask().execute();


    }


    @Override
    public void onBackPressed() {
        if (vPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            vPager.setCurrentItem(vPager.getCurrentItem() - 1);
        }
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

                JSONObject paramObject = new JSONObject();
                paramObject.put("gps", "45.741284,4.862928");
                paramObject.put("distance", "500000");


                //items = service.getItemByFilterGeo(paramObject.toString()).execute().body();
                items = service.getItemList().execute().body();



            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return items;
        }


        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);

            listItem =items;
            // Instantiate a ViewPager and a PagerAdapter.
            vPager = (ViewPager) findViewById(R.id.viewpager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), listItem);
            vPager.setAdapter(mPagerAdapter);

            //to indicate the current page
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(vPager, true);

            //load the item 1 when loading the activity
            vPager.setCurrentItem(1);


            progressBar.setVisibility(View.INVISIBLE);



        }
    }

}
