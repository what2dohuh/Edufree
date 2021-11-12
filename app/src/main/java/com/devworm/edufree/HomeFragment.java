package com.devworm.edufree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HomeFragment extends Fragment {
CardView androiddev,ethicalhacking,projects,webdev,businessandmarketing;
TextView greeting;
EditText searchinhome;
FloatingActionButton floatingActionButton;
RecyclerView mainsearch;
ImageView profileimage;
ScrollView scrollView2;
Button VisitOurWebsite;
FirebaseAuth firebaseAuth;
FirestoreRecyclerAdapter<Model, mViewHolder> Adapter;
FirebaseFirestore firebaseFirestore;
String Image;
Boolean save = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initial(v);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() !=null) {
            greeting.setText(firebaseAuth.getCurrentUser().getDisplayName());
        }else {
            greeting.setText("User");
        }
        androiddev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CoursesActivity.class);
                intent.putExtra("categories","AndroidDev");
                startActivity(intent);
            }
        }); ethicalhacking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CoursesActivity.class);
                intent.putExtra("categories","EthicalHacking");
                startActivity(intent);            }
        }); projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CoursesActivity.class);
                intent.putExtra("categories","Projects");
                startActivity(intent);            }
        }); webdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CoursesActivity.class);
                intent.putExtra("categories","WebDev");
                startActivity(intent);            }
        }); businessandmarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CoursesActivity.class);
                intent.putExtra("categories","BusinessAndMarketing");
                startActivity(intent);            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Admin.class));
            }
        });
        if (firebaseAuth.getCurrentUser() !=null){
            showpropic();
        }
        mainsearch.setLayoutManager(new LinearLayoutManager(getContext()));
        searchinhome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString().toLowerCase().replaceAll("\\s+", "");
                searching(search);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    private void searching(String search) {
        if (!search.isEmpty()) {
            scrollView2.setVisibility(View.GONE);
            mainsearch.setVisibility(View.VISIBLE);
            Query query = firebaseFirestore.collection("Courses").document("Categories").collection("All").orderBy("search").startAt(search).endAt(search + "\uf8ff").limit(10);
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
                            Intent intent = new Intent(getContext(), Comment.class);
                            intent.putExtra("about", model.about);
                            intent.putExtra("nameofthecourse", model.nameofthecourse);
                            startActivity(intent);
                        }
                    });
                    mViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(), ViewCourseActivity.class);
                            intent.putExtra("about", model.about);
                            intent.putExtra("nameofthecourse", model.nameofthecourse);
                            intent.putExtra("Thumbnail", model.coursethubnail);
                            intent.putExtra("link", model.link);
                            intent.putExtra("category",model.category);
                            startActivity(intent);
                        }
                    });
                    mViewHolder.savecourse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (firebaseAuth.getCurrentUser() == null) {
                                new AlertDialog.Builder(getContext())
                                        .setMessage("You Have To Login To Save Courses")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(getContext(), Login.class));
                                                getActivity().finish();
                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                            } else {
                                save = true;
                                if (save) {  firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(model.nameofthecourse).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (save) {
                                            if (value.exists()) {
                                                save = false;
                                                firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(model.nameofthecourse).delete();
                                                mViewHolder.savecourse.setImageResource(R.drawable.ic_baseline_bookmarks_24);
                                            } else {
                                                save = false;
                                                Model modelof = new Model(model.nameofthecourse, model.coursethubnail, model.link, model.search, model.category, model.about);
                                                firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").document(model.nameofthecourse).set(modelof);
                                                mViewHolder.savecourse.setImageResource(R.drawable.ic_baseline_book_24);
                                            }
                                            save = false;
                                        }
                                    }
                                });
                                }
                            }
                        }
                    });

                }
            };
            Adapter.startListening();
            mainsearch.setAdapter(Adapter);
            mainsearch.addItemDecoration(new
                    DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
        }
        else {
            mainsearch.setVisibility(View.INVISIBLE);
            scrollView2.setVisibility(View.VISIBLE);
        }
    }

    private void initial(View v) {
        androiddev = v.findViewById(R.id.androiddev);
        ethicalhacking = v.findViewById(R.id.ethicalhacking);
        projects = v.findViewById(R.id.projects);
        webdev = v.findViewById(R.id.webdev);
        floatingActionButton = v.findViewById(R.id.floatingActionButton);
        businessandmarketing = v.findViewById(R.id.businessandmarketing);
        greeting = v.findViewById(R.id.greeting);
        searchinhome = v.findViewById(R.id.searchinhome);
        mainsearch = v.findViewById(R.id.mainsearch);
        profileimage = v.findViewById(R.id.profileimage);
        scrollView2 = v.findViewById(R.id.scrollView2);
        VisitOurWebsite = v.findViewById(R.id.VisitOurWebsite);
    }
    private void showpropic() {
        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("Details").document("lol").
                get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot != null) {
                    if (snapshot.get("ProfilePic") != null) {
                        HashMap<String, Object> m = new HashMap<>();
                        m.put("Details", snapshot.getData().get("ProfilePic"));
                        Image = m.get("Details").toString();
                        Picasso.get().load(Image).into(profileimage);
                    }
                }
            }
        });
    }
}