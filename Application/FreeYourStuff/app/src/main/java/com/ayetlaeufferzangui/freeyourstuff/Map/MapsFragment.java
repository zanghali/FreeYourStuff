package com.ayetlaeufferzangui.freeyourstuff.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


//TODO change in order to not show the id in the snippet
public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "MapsFragment";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 200;

    private GoogleMap mMap;
    private MapView mMapView;
    private View mView;

    private ArrayList<MarkerModel> listMarker;
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

        //get the list of item from NavigationActivity
        listItem = (List<Item>) getArguments().getSerializable("listItem");


        mMapView = mView.findViewById(R.id.map);
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;

        //Disable Map Toolbar:
        mMap.getUiSettings().setMapToolbarEnabled(false);

        centerMapOnMyPosition();

        displayMarker();


    }


    private void centerMapOnMyPosition(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            //TODO get location and center map on location
            // version bellow doesn't work on Jej's phone
//            LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
//            double longitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
//            double latitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        } else {
            // Show rationale and request permission.
            requestPermissions( new String[]{ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    private void displayMarker(){

        listMarker = new ArrayList<MarkerModel>();

        mClusterManager = new ClusterManager<MarkerModel>(this.getContext(), mMap);
        mClusterManager.setRenderer(new OwnIconRendered(this.getActivity().getApplicationContext(), mMap, mClusterManager));

        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnCameraIdleListener(mClusterManager);


        if(listItem != null){

            for(Item currentItem :  listItem){

                String[] gps = currentItem.getGps().split(",");
                double lat = Double.parseDouble(gps[0]);
                double lng = Double.parseDouble(gps[1]);
                LatLng latLng = new LatLng(lat, lng);

                MarkerModel currentMarkerModel = new MarkerModel(currentItem.getId_item(), latLng, currentItem.getTitle(), Category.createIconUrl(Category.valueOf(currentItem.getCategory())));

                //listMarker.add(currentMarkerModel);


                //Marker currentMarker = mMap.addMarker(new MarkerOptions()
                //        .position(currentMarkerModel.getPosition())
                //        //.title(currentMarkerModel.getTitle())
                //        .icon(BitmapDescriptorFactory.fromResource(currentMarkerModel.getCategoryIconUrl()))
                //);
                //currentMarker.setTag(currentItem);


                mClusterManager.addItem(currentMarkerModel);





            }
        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
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

    @Override
    public void onLocationChanged(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
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



    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.e(TAG, "onInfoWindowClick");

        for (Item currentItem :listItem){
            if(currentItem.getId_item().equals(marker.getSnippet())){
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
            markerOptions.snippet(item.getId_item());



            super.onBeforeClusterItemRendered(item, markerOptions);
        }


    }
}
