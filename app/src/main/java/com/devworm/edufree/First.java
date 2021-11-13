package com.devworm.edufree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

public class First extends AppCompatActivity {
FirebaseAuth firebaseAuth;
LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        animationView = findViewById(R.id.animationView);
        animationView.animate().setDuration(1000).setStartDelay(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firebaseAuth = FirebaseAuth.getInstance();
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(),LoginOrRegister.class));
                }
                finish();
            }
        },1000);

    }
}