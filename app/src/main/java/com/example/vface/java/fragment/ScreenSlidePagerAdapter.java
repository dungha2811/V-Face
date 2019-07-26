package com.example.vface.java.fragment;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.vface.java.entity.User;

import java.util.ArrayList;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 4;

    private ArrayList<User> users;
    public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<User> list) {
        super(fm);
        users = list;
//        Log.d("Dung adapter",list.get(0).getUsername());
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Fragment getItem(int position) {
        //get the user to store in database
        switch (position){

            case 0:
                return IntroFragment.newInstance();
            case 1:
                return FriendlyFragment.newInstance(users.get(position-1).getUsername(),
                        users.get(position-1).getPhoneNumber(),
                        users.get(position-1).getImageLink(),69);
            case 2:
                return FriendlyFragment.newInstance(users.get(position-1).getUsername(),
                        users.get(position-1).getPhoneNumber(),
                        users.get(position-1).getImageLink(),69);

            case 3:
                return FriendlyFragment.newInstance(users.get(position-1).getUsername(),
                        users.get(position-1).getPhoneNumber(),
                        users.get(position-1).getImageLink(),69);

            default:
                return new FriendlyFragment();

        }

    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
