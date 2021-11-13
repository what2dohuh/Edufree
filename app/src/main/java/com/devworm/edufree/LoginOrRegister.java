package com.devworm.edufree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginOrRegister extends AppCompatActivity {
TextView skip;
Button Logingbtn,Registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_regiser);
       Registerbtn = findViewById(R.id.Registerbtn);
       Logingbtn = findViewById(R.id.Logingbtn);
       skip = findViewById(R.id.skip);
       Registerbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Register.class));

           }
       });
       Logingbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Login.class));
           }
       });
       skip.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
               finish();
           }
       });
    }
}