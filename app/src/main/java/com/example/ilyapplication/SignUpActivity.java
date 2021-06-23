package com.example.ilyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp;
    ImageButton btnBack;
    EditText edtNIM, edtName, edtEmail, edtFakultas, edtProdi, edtTlp, edtPassword, edtConfirmPassword;
    ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(this);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        edtNIM = findViewById(R.id.edt_txt_add_nim);
        edtName = findViewById(R.id.edt_txt_add_name);
        edtEmail = findViewById(R.id.edt_txt_add_email);
        edtFakultas = findViewById(R.id.edt_txt_add_fakultas);
        edtProdi = findViewById(R.id.edt_txt_add_prodi);
        edtTlp = findViewById(R.id.edt_txt_add_telephone);
        edtPassword = findViewById(R.id.edt_txt_add_password);
        edtConfirmPassword = findViewById(R.id.edt_txt_confirm_password);
        progressBar = findViewById(R.id.progressBar);

    }

    public void registUser(){
        String nim = edtNIM.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String fakultas = edtFakultas.getText().toString().trim();
        String prodi = edtProdi.getText().toString().trim();
        String tlp = edtTlp.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (nim.isEmpty()){
            edtNIM.setError("NIM Can't be Empty!");
            edtNIM.requestFocus();
            return;
        }
        if (name.isEmpty()){
            edtName.setError("Name Can't be Empty!");
            edtName.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Email Address not valid!");
            edtEmail.requestFocus();
            return;
        }
        if (email.isEmpty()){
            edtEmail.setError("Email Address Can't be Empty!");
            edtEmail.requestFocus();
            return;
        }
        if (fakultas.isEmpty()){
            edtFakultas.setError("Fakultas Can't be Empty!");
            edtFakultas.requestFocus();
            return;
        }
        if (prodi.isEmpty()){
            edtProdi.setError("Program Studi Can't be Empty!");
            edtProdi.requestFocus();
            return;
        }
        if (tlp.isEmpty()){
            edtTlp.setError("Telephone can't be empty!");
            edtTlp.requestFocus();
            return;
        }
        if (password.isEmpty() || password.length() < 6){
            edtPassword.setError("password is not valid!");
            edtPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty() ){
            edtConfirmPassword.setError("password is not valid");
            edtConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserData userData = new UserData(nim, name, email, fakultas, prodi, tlp);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this,"Register Successfull!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(SignUpActivity.this, "Register Failed! Try Again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(SignUpActivity.this, "Register Failed!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                registUser();
                break;
            case R.id.btn_back:
                Intent intent1 = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent1);
                break;
        }
    }

}