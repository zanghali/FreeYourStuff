package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;


import com.ayetlaeufferzangui.freeyourstuff.R;

public class NavigationActivity extends FragmentActivity {


    public static PagerAdapter mPagerAdapter;
    private ViewPager vPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Instantiate a ViewPager and a PagerAdapter.
        vPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(mPagerAdapter);

        //load the item 1 when loading the activity
        vPager.setCurrentItem(1);
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


}
