package com.ayetlaeufferzangui.freeyourstuff.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayetlaeufferzangui.freeyourstuff.Model.Category;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.MarkerModel;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.ViewItem.ViewItemActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.io.Serializable;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapsFragment";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 200;

    private GoogleMap mMap;
    private MapView mMapView;
    private View mView;

    private List<Item> listItem;

    private ClusterManager<MarkerModel> mClusterManager;


    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance(List<Item> listItem) {
        MapsFragment listFragment = new MapsFragment();

        Bundle args = new Bundle();

        args.putSerializable("listItem", (Serializable) listItem);
        listFragment.setArguments(args);

        return listFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_maps, container, false);
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = mView.findViewById(R.id.map);

        //get the list of item from NavigationActivity
        listItem = (List<Item>) getArguments().getSerializable("listItem");

        //Map
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        mMap = googleMap;

        //Disable Map Toolbar:
        mMap.getUiSettings().setMapToolbarEnabled(false);

        centerMapOnMyPosition();

        displayMarker();

    }


    private void centerMapOnMyPosition(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( new String[]{ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else {
            mMap.setMyLocationEnabled(true);

            LocationListener locationListener = new MyLocationListener();
            LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 10, locationListener);

            Location loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (loc != null) {
                double latitude = loc.getLatitude();
                double longitude = loc.getLongitude();

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            }
        }

    }

    private void displayMarker(){

        mClusterManager = new ClusterManager<MarkerModel>(this.getContext(), mMap);
        mClusterManager.setRenderer(new OwnIconRendered(this.getActivity().getApplicationContext(), mMap, mClusterManager));

        mMap.setOnCameraIdleListener(mClusterManager);

        mClusterManager.setOnClusterItemInfoWindowClickListener(mClusterItemInfoWindowClickListener);
        mMap.setOnInfoWindowClickListener(mClusterManager);


        if(listItem != null){

            for(Item currentItem :  listItem){

                String[] gps = currentItem.getGps().split(",");
                double lat = Double.parseDouble(gps[0]);
                double lng = Double.parseDouble(gps[1]);
                LatLng latLng = new LatLng(lat, lng);

                MarkerModel currentMarkerModel = new MarkerModel(currentItem, latLng, currentItem.getTitle(), Category.createIconUrl(Category.valueOf(currentItem.getCategory())));
                mClusterManager.addItem(currentMarkerModel);

            }
        }
    }

    public ClusterManager.OnClusterItemInfoWindowClickListener<MarkerModel> mClusterItemInfoWindowClickListener = new ClusterManager.OnClusterItemInfoWindowClickListener<MarkerModel>() {

        @Override
        public void onClusterItemInfoWindowClick(MarkerModel markerModel) {
            Item currentItem = markerModel.getItem();

            Intent intent = new Intent(getActivity(), ViewItemActivity.class);
            intent.putExtra("id_item", currentItem.getId_item());
            intent.putExtra("category", currentItem.getCategory());
            intent.putExtra("title", currentItem.getTitle());
            intent.putExtra("description", currentItem.getDescription());
            intent.putExtra("photo", currentItem.getPhoto());
            intent.putExtra("address", currentItem.getAddress());
            intent.putExtra("phone", currentItem.getPhone());
            intent.putExtra("status", currentItem.getStatus());
            intent.putExtra("gps", currentItem.getGps());
            intent.putExtra("availability", currentItem.getAvailability());
            intent.putExtra("id_user", currentItem.getId_user());
            intent.putExtra("distance", currentItem.getDistance());
            startActivity(intent);
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    centerMapOnMyPosition();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    class OwnIconRendered extends DefaultClusterRenderer<MarkerModel> {

        public OwnIconRendered(Context context, GoogleMap map,
                               ClusterManager<MarkerModel> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MarkerModel item, MarkerOptions markerOptions) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(item.getCategoryIconUrl()));
            markerOptions.title(item.getTitle());
            //markerOptions.snippet(item.getId_item());



            super.onBeforeClusterItemRendered(item, markerOptions);
        }


    }

    private class MyLocationListener implements LocationListener {

        private static final String TAG = "MyLocationListener";

        @Override
        public void onLocationChanged(Location loc) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude())));
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

