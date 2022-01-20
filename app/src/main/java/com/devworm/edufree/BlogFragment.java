package com.devworm.edufree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class BlogFragment extends Fragment {
    RecyclerView blogReac;
    FloatingActionButton addBlog;
    FirestoreRecyclerAdapter<modeBlog, viewHolder> Adapter;
    FirebaseAuth firebaseAuth;
    String UserImage,name;
    FirebaseFirestore firebaseFirestore;
    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_blog, container, false);
        blogReac = v.findViewById(R.id.blogReac);
        addBlog = v.findViewById(R.id.addBlog);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if (firebaseAuth.getCurrentUser()!= null) {
         addBlog.setVisibility(View.VISIBLE);
            addBlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), UploadBlog.class));
                }
            });
        }else {
            addBlog.setVisibility(View.INVISIBLE);
        }
        Query query = firebaseFirestore.collection("Blog").orderBy("timestamp",Query.Direction.DESCENDING);

        final FirestoreRecyclerOptions<modeBlog> Options = new FirestoreRecyclerOptions.Builder<modeBlog>()
                .setQuery(query, modeBlog.class)
                .build();

        Adapter = new FirestoreRecyclerAdapter<modeBlog, viewHolder>(Options) {
            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_singlerow, parent, false);
                return new viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final viewHolder mViewHolder, int i, @NonNull final modeBlog model) {
                 mViewHolder.Title.setText(model.getTitle());
                 mViewHolder.BlogText.setText(model.getWiteBAlog());
               if (model.getId() != null) {
                   firebaseFirestore.collection("Users").document(model.getId()).collection("Details").document("lol").
                           get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                       @Override
                       public void onSuccess(DocumentSnapshot snapshot) {
                           if (snapshot != null) {
                               if (snapshot.get("ProfilePic") != null) {
                                   HashMap<String, Object> m = new HashMap<>();
                                   m.put("Details", snapshot.getData().get("ProfilePic"));
                                   m.put("Name", snapshot.getData().get("Name"));
                                   UserImage = m.get("Details").toString();
                                   name = m.get("Name").toString();
                                   Picasso.get().load(UserImage).into(mViewHolder.profileimage);
                                   mViewHolder.userName.setText(name);
                               }else {
                                   UserImage = "https://firebasestorage.googleapis.com/v0/b/edufreeteam1.appspot.com/o/image%2Feithicalhacking.png?alt=media&token=324f7542-6556-40ac-be44-c592707bb4cf";
                               }
                           }
                       }
                   });
               }
                CharSequence date = DateFormat.format("EEEE,MMM d,yyyy,h:mm:ss a ", model.timestamp.toDate());
                mViewHolder.time.setText(date);
                if (model.getImageUrl() != null) {
                    Picasso.get().load(model.getImageUrl()).into(mViewHolder.thumb);
                }
                    else {
                    mViewHolder.thumb.setVisibility(View.GONE);

                }
                    mViewHolder.thumb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(),ViewImage.class);
                            intent.putExtra("Image",model.getImageUrl());
                            startActivity(intent);
                        }
                    });
            }
        };
        blogReac.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter.startListening();
        blogReac.setAdapter(Adapter);
        blogReac.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return v;
    }

}