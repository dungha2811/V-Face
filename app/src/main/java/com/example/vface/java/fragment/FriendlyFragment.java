package com.example.vface.java.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vface.R;
import com.squareup.picasso.Picasso;


public class FriendlyFragment extends Fragment {

    private String username;
    private String phoneNumber;
    private String imageUrl;
    private int age;

    public static FriendlyFragment newInstance(String username,
                                               String phoneNumber,String imgUrl,int age){
        FriendlyFragment friendlyFragment = new FriendlyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        bundle.putString("phoneNumber",phoneNumber);
        bundle.putString("image",imgUrl);
        bundle.putInt("age",age);
        friendlyFragment.setArguments(bundle);
        return friendlyFragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendly, container, false);

        //define fragment variable
        ImageView ivImageView = (ImageView) view.findViewById(R.id.iv_fragment);
        TextView tvUsername = (TextView) view.findViewById(R.id.tv_fragment_username);
        TextView tvAge = (TextView) view.findViewById(R.id.tv_fragment_age);
        TextView tvPhoneNumber = (TextView) view.findViewById(R.id.tv_fragment_phoneNumber);

        Picasso.get().load(imageUrl).into(ivImageView);
        tvUsername.setText("Username: " + username);
        tvAge.setText("Age:" + age);
        tvPhoneNumber.setText("Phone number: "+ phoneNumber);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        username = getArguments().getString("username");
        imageUrl = getArguments().getString("image");
        age = getArguments().getInt("age");
        phoneNumber = getArguments().getString("phoneNumber");
    }
}
