package com.devworm.edufree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class Comment extends AppCompatActivity {
RecyclerView recyclerView;
EditText yourcommentglobal2;
ImageButton sendbtn2;
TextView textView11;
FirebaseAuth firebaseAuth;
Intent intent;
ImageButton backbtninviewactivity2;
FirestoreRecyclerAdapter<commentModel, commentViewHolder> Adapter;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        intent = getIntent();
        recyclerView = findViewById(R.id.recyclerView);
        yourcommentglobal2 = findViewById(R.id.yourcommentglobal2);
        sendbtn2 = findViewById(R.id.sendbtn2);
        textView11 = findViewById(R.id.textView11);
        backbtninviewactivity2 = findViewById(R.id.backbtninviewactivity2);
        backbtninviewactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView11.setText(intent.getStringExtra("nameofthecourse"));
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Stuff").document(intent.getStringExtra("nameofthecourse")).collection("Comments").orderBy("timestamp",Query.Direction.ASCENDING);

        final FirestoreRecyclerOptions<commentModel> Options = new FirestoreRecyclerOptions.Builder<commentModel>()
                .setQuery(query, commentModel.class)
                .build();

        Adapter = new FirestoreRecyclerAdapter<commentModel, commentViewHolder>(Options) {
            @NonNull
            @Override
            public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.globalchat_singlerow, parent, false);
                return new commentViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final commentViewHolder commentViewHolder, int i, @NonNull final commentModel commentModel) {
             if (firebaseAuth.getCurrentUser().getUid().equals(commentModel.id)){
                 commentViewHolder.othersname.setText("You:");
             }else {
                 commentViewHolder.othersname.setText(commentModel.othersname+":");
             }
                commentViewHolder.othercomments.setText(commentModel.othercomments);
            }

        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter.startListening();
        recyclerView.setAdapter(Adapter);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(Adapter.getItemCount());
            }
        });
        sendbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = yourcommentglobal2.getText().toString();
                if (!comments.replaceAll("\\s","").isEmpty()) {
                    commentModel model = new commentModel(firebaseAuth.getCurrentUser().getDisplayName(), comments, null, firebaseAuth.getUid(), new Timestamp(new Date()));
                    firebaseFirestore.collection("Stuff").document(intent.getStringExtra("nameofthecourse")).collection("Comments").add(model);
                    Adapter.startListening();
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(Adapter.getItemCount());
                        }
                    });
                }
                comments = null;
                yourcommentglobal2.setText("");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Adapter != null) {
            Adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(Adapter.getItemCount());
        }
    }
}