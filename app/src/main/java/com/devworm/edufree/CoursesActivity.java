package com.devworm.edufree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.nio.file.Watchable;

public class CoursesActivity extends AppCompatActivity {
TextView Categoryname;
ImageButton backbtn;
EditText SearchcoursesinCA;
RecyclerView coursesrecinCA;
FirestoreRecyclerAdapter<Model, mViewHolder> Adapter;
Intent intent;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
String categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        initial();
        firebaseAuth = FirebaseAuth.getInstance();
        Categoryname.setText(categories);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Courses").document("Categories").collection(categories);

        final FirestoreRecyclerOptions<Model> Options = new FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query, Model.class)
                .build();

        Adapter = new FirestoreRecyclerAdapter<Model, mViewHolder>(Options) {
            @NonNull
            @Override
            public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.particularcourse_singlerow, parent, false);
                return new mViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final mViewHolder mViewHolder, int i, @NonNull final Model model) {
                mViewHolder.aboutthecourse.setText(model.about);
                mViewHolder.coursename.setText(model.nameofthecourse);
                Picasso.get().load(model.coursethubnail).into(mViewHolder.ThubnailCourse);
                if (firebaseAuth.getCurrentUser() !=null) {
                    firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(model.nameofthecourse).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value.exists()) {
                                mViewHolder.savecourse.setImageResource(R.drawable.ic_baseline_book_24);
                            } else {
                                mViewHolder.savecourse.setImageResource(R.drawable.ic_baseline_bookmarks_24);
                            }
                        }
                    });
                }
                mViewHolder.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),Comment.class);
                        intent.putExtra("about",model.about);
                        intent.putExtra("nameofthecourse",model.nameofthecourse);
                        startActivity(intent);
                    }
                });
                mViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),ViewCourseActivity.class);
                        intent.putExtra("about",model.about);
                        intent.putExtra("nameofthecourse",model.nameofthecourse);
                        intent.putExtra("Thumbnail",model.coursethubnail);
                        intent.putExtra("category",model.category);
                        intent.putExtra("link",model.link);
                        startActivity(intent);
                    }
                });
            }
        };
        coursesrecinCA.setLayoutManager(new LinearLayoutManager(this));
        Adapter.startListening();
        coursesrecinCA.setAdapter(Adapter);

        SearchcoursesinCA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString().toLowerCase().replaceAll("\\s+", "");
                Query query = firebaseFirestore.collection("Courses").document("Categories").collection(categories).orderBy("search").startAt(search).endAt(search+"\uf8ff").limit(10);
                final FirestoreRecyclerOptions<Model> Options = new FirestoreRecyclerOptions.Builder<Model>()
                        .setQuery(query, Model.class)
                        .build();

                Adapter = new FirestoreRecyclerAdapter<Model, mViewHolder>(Options) {
                    @NonNull
                    @Override
                    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.particularcourse_singlerow, parent, false);
                        return new mViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull final mViewHolder mViewHolder, int i, @NonNull final Model model) {
                        mViewHolder.aboutthecourse.setText(model.about);
                        mViewHolder.coursename.setText(model.nameofthecourse);
                        Picasso.get().load(model.coursethubnail).into(mViewHolder.ThubnailCourse);
                        if (firebaseAuth.getCurrentUser() !=null) {
                            firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(model.nameofthecourse).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if (value.exists()) {
                                        mViewHolder.savecourse.setImageResource(R.drawable.ic_baseline_book_24);
                                    } else {
                                        mViewHolder.savecourse.setImageResource(R.drawable.ic_baseline_bookmarks_24);
                                    }
                                }
                            });
                        }
                        mViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(),ViewCourseActivity.class);
                                intent.putExtra("about",model.about);
                                intent.putExtra("nameofthecourse",model.nameofthecourse);
                                intent.putExtra("Thumbnail",model.coursethubnail);
                                intent.putExtra("link",model.link);
                                intent.putExtra("category",model.category);
                                startActivity(intent);
                            }
                        });
                    }
                };
                Adapter.startListening();
                coursesrecinCA.setAdapter(Adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initial() {
        intent = getIntent();
        categories = intent.getStringExtra("categories");
        Categoryname = findViewById(R.id.Categoryname);
        SearchcoursesinCA = findViewById(R.id.SearchcoursesinCA);
        backbtn = findViewById(R.id.backbtn);
        coursesrecinCA = findViewById(R.id.coursesrecinCA);
    }
}