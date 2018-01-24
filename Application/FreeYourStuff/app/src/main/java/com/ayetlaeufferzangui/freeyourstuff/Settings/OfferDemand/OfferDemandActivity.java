package com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ayetlaeufferzangui.freeyourstuff.R;

/**
 * Created by lothairelaeuffer on 16/01/2018.
 */

public class OfferDemandActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String mId_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_demand);

        mId_user = getIntent().getStringExtra("id_user");

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment( OfferDemandFragment.newInstance(mId_user, "Offer"), getResources().getString(R.string.offer));
        adapter.addFragment( OfferDemandFragment.newInstance(mId_user, "Demand"), getResources().getString(R.string.demand));
        viewPager.setAdapter(adapter);
    }
}
