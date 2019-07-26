package com.example.vface.java.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.vface.R;
import com.example.vface.java.FireStoreCallBack;
import com.example.vface.java.entity.User;
import com.example.vface.java.fragment.FriendlyFragment;
import com.example.vface.java.fragment.IntroFragment;
import com.example.vface.java.fragment.ScreenSlidePagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Objects;

public class FriendsActivity extends AppCompatActivity {

    static int NUMBER_OF_RETURN_PERSON = 3;
    FirebaseFirestore db;
    Bundle bundle;
    ArrayList<User> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();

        // Instantiate a ViewPager and a PagerAdapter.
        getUserList();

//        String emailAddress = list.get(0).getEmailAddress();

    }


    void getUserList() {
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                String genderDb = Objects.requireNonNull(document.get("gender")).toString();
                                String genderBundle = bundle.getString("gender");

                                if (Objects.equals(genderDb,genderBundle)) {
                                    list.add(document.toObject(User.class));
                                }
                                if(count > NUMBER_OF_RETURN_PERSON){
                                    break;
                                }
                                count++;
                            }

                            ViewPager mPager = (ViewPager) findViewById(R.id.pager);
                            FragmentPagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),list);
                            mPager.setAdapter(pagerAdapter);
                            mPager.setCurrentItem(0);

                        } else {
                            Log.d("Dung", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
