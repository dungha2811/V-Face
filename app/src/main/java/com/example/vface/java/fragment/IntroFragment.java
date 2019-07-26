package com.example.vface.java.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vface.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class IntroFragment extends Fragment {


    public IntroFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static IntroFragment newInstance() {
        IntroFragment introFragment = new IntroFragment();
        return introFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_swipe);
        imageView.setImageResource(R.drawable.arrow);

        // Inflate the layout for this fragment
        return view;
    }
}
