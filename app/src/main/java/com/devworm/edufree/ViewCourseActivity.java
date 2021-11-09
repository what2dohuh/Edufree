package com.devworm.edufree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class ViewCourseActivity extends AppCompatActivity {
ImageButton backbtninviewactivity,savecoursebtn;
ImageView thumbnail;
TextView aboutthecourseviewcourse,Coursename;
Button downloadbtn,visitOurWebsite;
Intent intent;
FirebaseAuth firebaseAuth;
Boolean save = false;
FirebaseFirestore firebaseFirestore;
String name,image,link,search,category,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        initial();
        intent = getIntent();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        backbtninviewactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name = intent.getStringExtra("nameofthecourse");
        image = intent.getStringExtra("Thumbnail");
        link = intent.getStringExtra("link");
        search = name.toLowerCase().replaceAll("\\s+", "");
        category = intent.getStringExtra("category");
        about = intent.getStringExtra("about");
        Coursename.setText(name);
        Picasso.get().load(image).into(thumbnail);
        aboutthecourseviewcourse.setText(about);
        savecoursebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() == null) {
                    new AlertDialog.Builder(ViewCourseActivity.this)
                            .setMessage("You Have To Login To Save Courses")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    finish();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                } else {
                    save = true;
                    if (save) {
                        savingcourse();
                    }
                }
            }
        });
    }

    private void savingcourse() {
        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(name).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (save) {
                    if (value.exists()) {
                        save = false;
                        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(name).delete();
                        savecoursebtn.setImageResource(R.drawable.ic_baseline_bookmarks_24);
                    } else {
                        save = false;
                        Model model = new Model(name, image, link, search, category, about);
                        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(name).set(model);
                        savecoursebtn.setImageResource(R.drawable.ic_baseline_book_24);
                    }
                    save = false;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(name).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value.exists()) {
                        savecoursebtn.setImageResource(R.drawable.ic_baseline_book_24);
                    } else {
                        savecoursebtn.setImageResource(R.drawable.ic_baseline_bookmarks_24);
                    }
                }
            });
        }
    }

    private void initial() {
        backbtninviewactivity = findViewById(R.id.backbtninviewactivity);
        savecoursebtn = findViewById(R.id.savecoursebtn);
        thumbnail = findViewById(R.id.thumbnail);
        Coursename = findViewById(R.id.Coursename);
        aboutthecourseviewcourse = findViewById(R.id.aboutthecourseviewcourse);
        downloadbtn = findViewById(R.id.downloadbtn);
        visitOurWebsite = findViewById(R.id.visitOurWebsite);
    }
}