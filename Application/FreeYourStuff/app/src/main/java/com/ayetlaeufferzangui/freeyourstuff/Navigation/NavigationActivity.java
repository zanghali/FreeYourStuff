package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationActivity extends FragmentActivity {

    private static final String TAG = "NavigationActivity";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 200;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar progressBar;

    private String distanceFilter;
    private String categoryFilter;
    private String availabilityFilter;

    private List<Item> listItem;

    private String gps;

    private int[] tabIcons = {
            R.drawable.ic_settings_black_24dp,
            R.drawable.ic_list_black_24dp,
            R.drawable.ic_map_black_24dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        progressBar = findViewById(R.id.progressBar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);

        //get the filters
        distanceFilter = getIntent().getStringExtra("distance");
        categoryFilter = getIntent().getStringExtra("category");
        availabilityFilter = getIntent().getStringExtra("availability");

        //if filters are not set, set to default
        if (distanceFilter == null) {
            distanceFilter = "5000";
        }
        if (categoryFilter == null) {
            categoryFilter = "";
        }
        if (availabilityFilter == null) {
            availabilityFilter = "";
        }

        //request permission, handle GPS and create list
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {

            handleGps();
            new GetItemListTask().execute();
        }

    }

    @SuppressLint("MissingPermission")
    public void handleGps(){
        //permission is granted
        LocationListener locationListener = new MyLocationListener();
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        double latitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        double longitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

        gps = String.valueOf(latitude) + ',' + String.valueOf(longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    handleGps();
                    new GetItemListTask().execute();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //TODO IMPORTANT
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
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

                items = service.getItemByFilterGeo(gps, distanceFilter).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return items;
        }


        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);

            listItem = new ArrayList<Item>();

            if(items != null){
                //check filters(category and availability)
                if(categoryFilter.equals("") && availabilityFilter.equals("")){
                    listItem = items;
                }else if (categoryFilter.equals("")){
                    //filter on the availability
                    for(Item item : items){
                        if(item.getAvailability().equals(availabilityFilter)){
                            listItem.add(item);
                        }
                    }
                }else if (availabilityFilter.equals("")){
                    //filter on the category
                    for(Item item : items){
                        if(item.getCategory().equals(categoryFilter)){
                            listItem.add(item);
                        }
                    }
                }else{
                    for(Item item : items){
                        //filter on the category an the availability
                        if(item.getCategory().equals(categoryFilter) && item.getAvailability().equals(availabilityFilter)){
                            listItem.add(item);
                        }
                    }
                }
            }



            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();

            progressBar.setVisibility(View.INVISIBLE);

        }
    }


    private class MyLocationListener implements LocationListener {

        private static final String TAG = "MyLocationListener";

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
