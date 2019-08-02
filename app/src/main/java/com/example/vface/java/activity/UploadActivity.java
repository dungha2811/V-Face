package com.example.vface.java.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.vface.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    public static final String TAG = "UploadActivity";

    private Button upload;
    private Button chooseUploadImage;
    private Button takePicture;
    private Button cancel;
    private ImageView imageView;
    private StorageReference storageRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private ProgressDialog mProgressDialog;
    RequestQueue queue;
    private Uri input;

    private static int RESULT_LOAD_IMAGE = 1;
    private static int REQUEST_IMAGE_CAPTURE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //define variable
        chooseUploadImage = (Button) findViewById(R.id.btn_choose_image_to_upload);
        imageView = (ImageView) findViewById(R.id.iv_image);
        takePicture = (Button) findViewById(R.id.btn_take_picture);
        cancel = (Button) findViewById(R.id.btn_cancel);
        storageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        upload = (Button) findViewById(R.id.btn_upload);
        mProgressDialog = new ProgressDialog(UploadActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();

        //handle request queue
        queue = new RequestQueue(new DiskBasedCache(getCacheDir(),
                1024 * 1024), new BasicNetwork(new HurlStack()));

        queue.start();

        //disable upload button when image is not uploaded
        upload.setEnabled(false);
        upload.setAlpha(0.5f);

        //set on click for uploaded images
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageToFireStore();
            }
        });

        //set on click for choosing image
        chooseUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadImageIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadImageIntent, RESULT_LOAD_IMAGE);
                upload.setEnabled(true);
                upload.setAlpha(1);
                chooseUploadImage.setText(R.string.another_image);
                takePicture.setText(R.string.take_a_picture);
            }
        });

        //set on click for taking picture
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                upload.setEnabled(true);
                upload.setAlpha(1);
                takePicture.setText(R.string.take_another_picture);
                chooseUploadImage.setText(R.string.upload_image);
            }
        });

        //set on click for cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            input = data.getData();
            imageView.setImageURI(input);
        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            input= getImageUri(this,imageBitmap);
        }
    }

    public void UploadImageToFireStore(){
        mProgressDialog.setMessage("Uploading Image...");
        mProgressDialog.show();

        //get the signed in user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        final String userID = user.getUid();

        final StorageReference reference =
                storageRef.child("images/users/"+ userID+"/"+userID+".jpg");
        reference.putFile(input).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference userReference = storageRef.child("images/users/"
                        + userID + "/" + userID + ".jpg");
                Toast.makeText(UploadActivity.this, "Upload Success",
                        Toast.LENGTH_SHORT).show();
                userReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        Log.d("Upload Dung", uri.toString());
                        DocumentReference documentReference =
                                db.collection(getString(R.string.collection_users))
                                        .document(userID);
                        documentReference.update("imageLink", uri.toString());

                        String url =
                                "http://35.198.248.118:8081" +
                                        "/v-face-app-api/1.0.0/calculate-embedding-vector";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("HttpClient",
                                                "success! response: " + response);
                                        DocumentReference documentReference =
                                                db.collection(getString(R.string.collection_users))
                                                        .document(userID);
                                        documentReference.update("faceVector", response);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("HttpClient", "error: " + error.toString());
                            }
                        })
                            {
                                @Override
                                protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("image_file", uri.toString());

                                return params;
                            }
                        };
                        queue.add(stringRequest);
                        mProgressDialog.dismiss();
                        Intent intent = new Intent(UploadActivity.this,
                                MenuActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this,
                                "Upload Failed", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                });
            }
        });
    }
            public Uri getImageUri(Context inContext, Bitmap inImage) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                        inImage, "Title", null);
                return Uri.parse(path);
    }



    @Override
    protected void onStop() {
        super.onStop();
    }
}
