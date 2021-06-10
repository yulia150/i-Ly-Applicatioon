package com.example.ilyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {



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
}