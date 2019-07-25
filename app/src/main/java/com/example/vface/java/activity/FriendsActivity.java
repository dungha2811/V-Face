package com.example.vface.java.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.vface.R;
import com.example.vface.java.fragment.FriendlyFragment;

public class FriendsActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    private FragmentPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        // Instantiate a ViewPager and a PagerAdapter.
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(0);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return FriendlyFragment.newInstance("namnh64","0969052193",
                            "https://firebasestorage.googleapis.com/v0/b/vface-48591.appspot.com/o/images%2Fusers%2FCCGYqSdLMDSCuiCWf5NXeCEytmA3%2FCCGYqSdLMDSCuiCWf5NXeCEytmA3.jpg?alt=media&token=094dc26f-e225-4df5-90fd-c53385a361af",26);
                case 1:
                    return FriendlyFragment.newInstance("namnh64","0969052193",
                            "https://firebasestorage.googleapis.com/v0/b/vface-48591.appspot.com/o/images%2Fusers%2FCCGYqSdLMDSCuiCWf5NXeCEytmA3%2FCCGYqSdLMDSCuiCWf5NXeCEytmA3.jpg?alt=media&token=094dc26f-e225-4df5-90fd-c53385a361af",69);
                case 2:
                    return FriendlyFragment.newInstance("namnh64","0969052193",
                            "https://firebasestorage.googleapis.com/v0/b/vface-48591.appspot.com/o/images%2Fusers%2FCCGYqSdLMDSCuiCWf5NXeCEytmA3%2FCCGYqSdLMDSCuiCWf5NXeCEytmA3.jpg?alt=media&token=094dc26f-e225-4df5-90fd-c53385a361af",69);

                default:
                    return new FriendlyFragment();

            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
