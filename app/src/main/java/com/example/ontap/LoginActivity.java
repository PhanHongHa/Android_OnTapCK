package com.example.ontap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText txtEmail_L, txtPass_L;
    Button btnLogin_L;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        mapping();
        btnLogin_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String user = txtEmail_L.getText().toString().trim();
        String pass = txtPass_L.getText().toString().trim();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Successed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, RecyclerViewActivity.class));

                } else {
                    Toast.makeText(LoginActivity.this, "Email hoặc Password sai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mapping() {
        txtEmail_L = findViewById(R.id.txtEmail_Login);
        txtPass_L = findViewById(R.id.txtPassword_Login);
        btnLogin_L = findViewById(R.id.btnLogin_Login);

    }
}