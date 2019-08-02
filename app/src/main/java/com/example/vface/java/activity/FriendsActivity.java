package com.example.vface.java.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.vface.R;
import com.example.vface.java.entity.User;
import com.example.vface.java.entity.UserFaceVector;
import com.example.vface.java.fragment.ScreenSlidePagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class FriendsActivity extends AppCompatActivity {

    private static int NUMBER_OF_RETURN_PERSON = 3;
    private FirebaseFirestore db;
    private Bundle bundle;
    private FirebaseAuth firebaseAuth;
    ArrayList<User> list = new ArrayList<>();
    ArrayList<UserFaceVector> listVector = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        bundle = getIntent().getExtras();
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        // Instantiate a ViewPager and a PagerAdapter.
        getUserList();

    }

    void getUserList() {



        //get the signed in user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        final String userID = user.getUid();

        db.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //get current user face vector
                final String userFaceVector = (String) documentSnapshot.get("faceVector");
                //get data
                db.collection("Users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int count = 0;
                                    double smallest = Double.MIN_VALUE;
                                    for (QueryDocumentSnapshot document
                                            : Objects.requireNonNull(task.getResult())) {

                                        //get id
                                        String id = (String) document.get("id");

                                        //get gender
                                        String genderDb = Objects
                                                .requireNonNull(document.get("gender"))
                                                .toString();

                                        String genderBundle = bundle.getString("gender");

                                        String faceVector = Objects.requireNonNull(document
                                                .get("faceVector")).toString();
                                        if (Objects.equals(genderDb,genderBundle)) {

                                            try {
                                                ArrayList<String> vectorUser = jsonStringToArray(userFaceVector);
                                                ArrayList<String> vectorAll = jsonStringToArray(faceVector);

                                                double distance = findMinPercentage(vectorUser, vectorAll);

//                                        if (!Objects.equals(userID, id)) {
                                                Log.d("Dung in Users", distance + "");
                                                list.add(document.toObject(User.class));
                                                listVector.add(new UserFaceVector(id, distance));
                                                count++;
//                                                }
//                                            }
//                                        }
                                                if (count > NUMBER_OF_RETURN_PERSON) {
                                                    break;
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    ViewPager mPager = (ViewPager) findViewById(R.id.pager);
                                    FragmentPagerAdapter pagerAdapter =
                                            new ScreenSlidePagerAdapter(getSupportFragmentManager(),
                                                    list, listVector);
                                    mPager.setAdapter(pagerAdapter);
                                    mPager.setCurrentItem(0);

                                } else {
                                    Log.d("Dung",
                                            "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
    }

    double findMinPercentage(ArrayList<String> list1, ArrayList<String> list2){
        double percentage ;
        double sum = 0 ;
        for(int i = 0; i < 127; i++ ){
            sum += Math.pow(2,
                    Double.parseDouble(list1.get(i)) - Double.parseDouble(list2.get(i)));
        }

        double distance = Math.sqrt(sum);

        percentage = Math.min(100, 100* 0.65 /distance);

        return percentage;
    }

    ArrayList<String> jsonStringToArray(String jsonString) throws JSONException {

        ArrayList<String> stringArray = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            stringArray.add(jsonArray.getString(i));
        }

        return stringArray;
    }
}
