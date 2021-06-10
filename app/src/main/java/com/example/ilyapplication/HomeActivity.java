package com.example.ilyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.security.KeyStore;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //date and time
        TextView month = findViewById(R.id.month);
//        TextView day = findViewById(R.id.day);
//        TextView year = findViewById(R.id.year);

        Date currentTime = Calendar.getInstance().getTime();
        String formatDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        month.setText(formatDate);
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
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }else if(id==R.id.nav_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        mFirebaseAuth.signOut();
    }
}