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

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword,txtConform,txtName;
    Button btnRegister_R;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        mapping();
        btnRegister_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login () {
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String pass = txtPassword.getText().toString().trim();
        String conform = txtConform.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)|| TextUtils.isEmpty(conform)|| TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Thử lại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void mapping () {
        txtName = findViewById(R.id.txtTen_item);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConform = findViewById(R.id.txtConform);

        btnRegister_R = findViewById(R.id.btnLogin_R);
    }
}