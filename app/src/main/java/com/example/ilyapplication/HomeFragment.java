package com.example.ilyapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {


    private DatabaseReference dbReference;
    private FirebaseUser firebaseUser;
    private String userID;
    String jml;
    Button buka, tutup;
    FirebaseAuth mAuth;

    TextView infoPengunjung, kapasitasPengunjung, nameUser;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        infoPengunjung = view.findViewById(R.id.info_pengunjung);
        kapasitasPengunjung = view.findViewById(R.id.kapasitas_pengunjung);
        buka = view.findViewById(R.id.status_buka);
        tutup = view.findViewById(R.id.status_tutup);
        nameUser = view.findViewById(R.id.name_user);

        //name
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        dbReference = FirebaseDatabase.getInstance().getReference("users");
        dbReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name;
                UserData userData = snapshot.getValue(UserData.class);
                if (userData!=null){
                    name = userData.nama;

                    nameUser.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //info jumlah pengunjung
        dbReference = FirebaseDatabase.getInstance().getReference().child("kapasitasPengunjung");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pengunjung = snapshot.getValue(String.class);
                if (pengunjung!=null){
                    jml = pengunjung;
                    infoPengunjung.setText(jml + " Orang");
                    kapasitasPengunjung.setText(jml+"/15");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //info date
        TextView month = view.findViewById(R.id.month);
        Calendar calendar = Calendar.getInstance();

        String date = calendar.get(Calendar.YEAR) + "-"
                + calendar.get(Calendar.MONTH)
                + "-" + calendar.get(Calendar.DAY_OF_MONTH)
                + " at " + calendar.get(Calendar.HOUR_OF_DAY)
                + ":" + calendar.get(Calendar.MINUTE);

        int jam = calendar.get(Calendar.HOUR_OF_DAY);
        if ( jam <= 19 ){
            buka.setEnabled(true);
        }else {
            tutup.setEnabled(true);
        }
        month.setText(date);
        return view;
    }


}