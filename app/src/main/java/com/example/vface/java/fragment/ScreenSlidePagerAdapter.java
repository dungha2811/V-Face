package com.example.vface.java.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.vface.java.entity.User;
import com.example.vface.java.entity.UserFaceVector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 3;

    private ArrayList<User> users;
    public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<User> list,
                                   ArrayList<UserFaceVector> userFaceVectors) {
        super(fm);
        ArrayList<User> listInside = new ArrayList<>();
        //sort faceVector array
        Collections.sort(userFaceVectors, new Comparator<UserFaceVector>() {
            @Override
            public int compare(UserFaceVector userFaceVector, UserFaceVector t1) {
                return Double.compare(t1.getPercentage(),userFaceVector.getPercentage());
            }
        });

        for(int i =0 ;i < userFaceVectors.size(); i++){
            for(int j =0 ;j <list.size();j++){
                if(userFaceVectors.get(i).getId().equals(list.get(j).getId())){
//                    Log.d("Dung adapter",userFaceVectors.get(i).getPercentage()+"");
                    listInside.add(list.get(i));
                    Log.d("Dung adapter",   listInside.get(i).getUsername()+" : "+userFaceVectors.get(i).getPercentage());
                }
            }


        }
        users = listInside;
        Log.d("Dung adapter",userFaceVectors.get(0).getPercentage()+"");
    }
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Fragment getItem(int position) {
        //get the user to store in database
        switch (position){

            case 0:
                return IntroFragment.newInstance();
            case 1:
                try {
                    return FriendlyFragment.newInstance(users.get(position-1).getUsername(),
                            users.get(position-1).getPhoneNumber(),
                            users.get(position-1).getImageLink(),
                            getAge(users.get(position-1).getDateOfBirth()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            case 2:
                try {
                    return FriendlyFragment.newInstance(users.get(position-1).getUsername(),
                            users.get(position-1).getPhoneNumber(),
                            users.get(position-1).getImageLink(),
                            getAge(users.get(position-1).getDateOfBirth()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            default:
                return new FriendlyFragment();

        }

    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getAge(String dateOfBirth) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf
                = new SimpleDateFormat("DD-MM-YYYY");
        Date d = sdf.parse(dateOfBirth);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate localDate = LocalDate.of(year, month, date);
        LocalDate now = LocalDate.now();
        Period dif = Period.between(localDate, now);
        return dif.getYears();
    }

}
