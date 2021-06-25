package com.example.ilyapplication;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email;

    @Override
    protected void onStart() {
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        super.onStart();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected( MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            fragment = new HomeFragment();
                            break;
                        case R.id.nav_qrCode:
                            fragment = new QRCodeFragment();
                            break;
                        case R.id.nav_library:
                            fragment = new LibraryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView btnNav = findViewById(R.id.nav_bottom);
        btnNav.setOnNavigationItemSelectedListener(navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.nav_profile){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }else if(id==R.id.nav_logout){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            try {
                mAuth.signOut();
                Toast.makeText(this, "User Sign out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }catch (Exception e) {

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        mAuth.signOut();
    }
}