package com.example.ilyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;
    TextView nameP, nameForP, nimP, tlpP, emailP, fakultasP, prodiP;
    ImageView imgProfile;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseR;
    private FirebaseStorage firebaseS;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseR = FirebaseDatabase.getInstance().getReference();
        firebaseS = FirebaseStorage.getInstance();
        storageR = firebaseS.getReference();

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        imgProfile = findViewById(R.id.profile_pic);
        imgProfile.setOnClickListener(this);

        nameForP = findViewById(R.id.name_for_profile);
//        nameForP.setText(user.);
        nameP = findViewById(R.id.name_profile);
        nimP = findViewById(R.id.nim_profile);
        tlpP = findViewById(R.id.tlp_profile);
        emailP = findViewById(R.id.email_profile);
        fakultasP = findViewById(R.id.fakultas_profile);
        prodiP = findViewById(R.id.prodi_profile);



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                imgProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.profile_pic:
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
                break;


        }

    }
}