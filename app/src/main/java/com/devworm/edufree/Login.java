package com.devworm.edufree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
TextView gotoregister;
EditText gmaillogin,passwordlogin;
Button login;
ImageButton backlog;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        initial();
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        LogingFun();
        backlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();;
            }
        });
    }

    private void LogingFun() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sing In ...");
        progressDialog.setCancelable(false);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = gmaillogin.getText().toString();
                String password = passwordlogin.getText().toString();
                if (gmail.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Fill All Field", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(gmail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Error !" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void initial() {
        gotoregister = findViewById(R.id.gotoregister);
        gmaillogin = findViewById(R.id.gmaillogin);
        backlog = findViewById(R.id.backlog);
        passwordlogin = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.login);
    }
}