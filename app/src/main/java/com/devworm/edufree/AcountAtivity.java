package com.devworm.edufree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

public class AcountAtivity extends AppCompatActivity {
    FrameLayout frameLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_ativity);
        frameLayout2 = findViewById(R.id.frameLayout2);
        getSupportFragmentManager().beginTransaction() .setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,  // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.frameLayout2,new AccountFragment()).commit();
    }
}