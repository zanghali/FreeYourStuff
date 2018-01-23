package com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter.DemandItemAdapter;
import com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter.OfferItemAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;
import static com.ayetlaeufferzangui.freeyourstuff.Service.ENDPOINT;


public class OfferDemandFragment extends Fragment {

    private static final String ID_USER = "id_user";
    private static final String OFFER_DEMAND = "offerDemand";
    private static final String TAG = "OfferDemandFragment";

    private String mId_user;
    private String mOfferDemand;

    private RecyclerView recyclerView;
    private OfferItemAdapter offerItemAdapter;
    private DemandItemAdapter demandItemAdapter;

    private TextView textView;

    public OfferDemandFragment() {
        // Required empty public constructor
    }

    public static OfferDemandFragment newInstance(String id_user, String offerDemand) {
        OfferDemandFragment fragment = new OfferDemandFragment();
        Bundle args = new Bundle();
        args.putString(ID_USER, id_user);
        args.putString(OFFER_DEMAND, offerDemand);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId_user = getArguments().getString(ID_USER);
            mOfferDemand = getArguments().getString(OFFER_DEMAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer_demand, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.list_recycler_view);
        textView = view.findViewById(R.id.textView);

        LocationListener locationListener = new MyLocationListener();
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        double latitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        double longitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

        String gps = String.valueOf(latitude) + ',' + String.valueOf(longitude);

        new GetOfferDemandTask().execute(gps);
    }

    private class GetOfferDemandTask extends AsyncTask<String, Void, List<Item>> {
        @Override
        protected List<Item> doInBackground(String... params) {
            List<Item> result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String gps = params[0];

                if(mOfferDemand=="Offer")
                    result = service.getItemByUser(mId_user, gps).execute().body();
                else if (mOfferDemand=="Demand")
                    result = service.getItemOfUserInterestedBy(mId_user, gps).execute().body();

            } catch (IOException e) {
                Log.e(TAG, "ERROR");
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<Item> listItem) {

            if(listItem == null || listItem.isEmpty()){
                textView.setText(R.string.no_item);
            }else {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                //specify an adapter
                if (mOfferDemand == "Offer") {
                    offerItemAdapter = new OfferItemAdapter(listItem, getContext(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Item item) {
                            new DeleteItemTask().execute(item.getId_item(), item.getPhoto(), mId_user);
                        }
                    });
                    recyclerView.setAdapter(offerItemAdapter);
                } else if (mOfferDemand == "Demand") {
                    demandItemAdapter = new DemandItemAdapter(listItem, getContext(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(Item item) {
                            new DeleteUserInterestedByItem().execute(mId_user, item.getId_item());
                        }
                    });
                    recyclerView.setAdapter(demandItemAdapter);
                }
            }


        }
    }

    private class DeleteItemTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);


                String id_item = params[0];
                String photo = params[1];
                String id_user = params[2];

                result = service.deleteItem(id_item, photo, id_user).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result == "true"){
                Log.e(TAG,"true");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "item deleted", Toast.LENGTH_SHORT).show();
                    }
                });


                Intent intent = new Intent(getActivity(),OfferDemandActivity.class);
                intent.putExtra("id_user", mId_user);
                startActivity(intent);
                getActivity().finish();
            }else{
                Log.e(TAG, "ERROR");
            }

        }
    }


    private class DeleteUserInterestedByItem extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String id_user = params[0];
                String id_item = params[1];

                result = service.deleteUserInterestedByItem(id_user, id_item).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result == "true"){
                Log.e(TAG,"true");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "you are not interested anymore", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e(TAG, mId_user);
                Intent intent = new Intent(getActivity(),OfferDemandActivity.class);
                intent.putExtra("id_user", mId_user);
                startActivity(intent);
                getActivity().finish();
            }else{
                Log.e(TAG, "ERROR");
            }

        }
    }

    private class MyLocationListener implements LocationListener {
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
