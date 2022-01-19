package com.devworm.edufree;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
FrameLayout frameLayout;
ChipNavigationBar bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(savedInstanceState);
    }
    private void initialize(Bundle savedInstanceState) {
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment temp = null;
                switch (i) {
                    case R.id.blog:
                        temp = new BlogFragment();
                        break;
                    case R.id.home:
                        temp = new HomeFragment();
                        break;
                    case R.id.chat:
                        temp = new GlobalChatFragment();
                        break;
                    case R.id.saved:
                        temp = new SavedCourseFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction() .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,  // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frameLayout,temp).commit();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.home){
            super.onBackPressed();
        }
        else {
            bottomNavigationView.setItemSelected(R.id.home,true);
        }
    }
}