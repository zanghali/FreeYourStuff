package com.ayetlaeufferzangui.freeyourstuff.Navigation;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ayetlaeufferzangui.freeyourstuff.List.ListFragment;
import com.ayetlaeufferzangui.freeyourstuff.Map.MapsFragment;
import com.ayetlaeufferzangui.freeyourstuff.R;



public class NavigationActivity extends AppCompatActivity {



    ListFragment mListFragment;

    MapsFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


//        if (savedInstanceState == null){
//            mListFragment = new ListFragment();
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().add(R.id.content, mListFragment).commit();
//        }

        
        if (savedInstanceState == null){
            mMapFragment = new MapsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.content, mMapFragment).commit();
        }


    }


}
