package com.example.ilyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;
    Button btnChangeProfile;
    TextView nameP, nameForP, nimP, tlpP, emailP, fakultasP, prodiP, btnLogout;
    ImageView imgProfile;
    private FirebaseAuth mAuth;

    private FirebaseStorage firebaseS;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageR;
    String name, nim, tlp, email, fakultas, prodi;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseR;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        databaseR = FirebaseDatabase.getInstance().getReference("users");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        imgProfile = findViewById(R.id.profile_pic);
        btnChangeProfile = findViewById(R.id.btn_change_profile);
        btnChangeProfile.setOnClickListener(this);

        nameForP = findViewById(R.id.name_for_profile);
//        nameForP.setText(user.);
        nameP = findViewById(R.id.name_profile);
        nimP = findViewById(R.id.nim_profile);
        tlpP = findViewById(R.id.tlp_profile);
        emailP = findViewById(R.id.email_profile);
        fakultasP = findViewById(R.id.fakultas_profile);
        prodiP = findViewById(R.id.prodi_profile);

        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);

        databaseR.child(userID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData userData = snapshot.getValue(UserData.class);
                if (userData!=null){
                    name = userData.nama;
                    nim = userData.nim;
                    tlp = userData.telephone;
                    email = userData.email;
                    fakultas = userData.fakultas;
                    prodi = userData.prodi;

                    nameP.setText(name);
                    nameForP.setText(name);
                    nimP.setText(nim);
                    tlpP.setText(tlp);
                    emailP.setText(email);
                    fakultasP.setText(fakultas);
                    prodiP.setText(prodi);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(this.getClass().getName().toUpperCase(), "Failed read data!", error.toException() );

            }
        });
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
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_change_profile:
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
                break;
            case R.id.btn_logout:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                try {
                    mAuth.signOut();
                    Toast.makeText(this, "User Sign out!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
                }catch (Exception e) {

                }
                break;


        }

    }
}