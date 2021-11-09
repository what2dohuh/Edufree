package com.devworm.edufree;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class SavedCourseFragment extends Fragment {
RecyclerView savedcourserec;
EditText searchinhome2;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
FirestoreRecyclerAdapter<Model, mViewHolder> Adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_saved_course, container, false);
        searchinhome2 = v.findViewById(R.id.searchinhome2);
        savedcourserec = v.findViewById(R.id.savedcourserec);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
if (firebaseAuth.getCurrentUser() != null) {
    show();
}
        searchinhome2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString().toLowerCase().replaceAll("\\s+", "");
                if (!search.isEmpty()) {
                    if (firebaseAuth.getCurrentUser() !=null) {
                        searching(search);
                    }
                }else{
                    show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    private void searching(String search) {
        Query query = firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses").orderBy("search").startAt(search).endAt(search + "\uf8ff").limit(10);
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
                        Intent intent = new Intent(getActivity(), ViewCourseActivity.class);
                        intent.putExtra("about", model.about);
                        intent.putExtra("nameofthecourse", model.nameofthecourse);
                        intent.putExtra("Thumbnail", model.coursethubnail);
                        intent.putExtra("link", model.link);
                        intent.putExtra("category", model.category);
                        startActivity(intent);
                    }
                });
            }
        };
        Adapter.startListening();
        savedcourserec.setAdapter(Adapter);
    }

    private void show() {
        Query query = firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("SavedCourses");
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
                        Intent intent = new Intent(getActivity(),ViewCourseActivity.class);
                        intent.putExtra("about",model.about);
                        intent.putExtra("nameofthecourse",model.nameofthecourse);
                        intent.putExtra("Thumbnail",model.coursethubnail);
                        intent.putExtra("link",model.link);
                        startActivity(intent);
                    }
                });
            }
        };
        savedcourserec.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter.startListening();
        savedcourserec.setAdapter(Adapter);
    }
}