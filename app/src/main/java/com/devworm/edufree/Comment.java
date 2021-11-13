package com.devworm.edufree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;

public class Comment extends AppCompatActivity {
RecyclerView recyclerView;
EditText yourcommentglobal2;
ImageButton sendbtn2;
LottieAnimationView empty;
ImageView profileimage2;
TextView textView11;
FirebaseAuth firebaseAuth;
Intent intent;
ImageButton backbtninviewactivity2;
FirestoreRecyclerAdapter<commentModel, commentViewHolder> Adapter;
FirebaseFirestore firebaseFirestore;
String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initial();
        image = "https://firebasestorage.googleapis.com/v0/b/edufreeteam1.appspot.com/o/image%2Feithicalhacking.png?alt=media&token=324f7542-6556-40ac-be44-c592707bb4cf";
        backbtninviewactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (firebaseAuth.getCurrentUser() != null) {
            showpropic();
        }
        Query query = firebaseFirestore.collection("Stuff").document(intent.getStringExtra("nameofthecourse")).collection("Comments").orderBy("timestamp",Query.Direction.ASCENDING);

        final FirestoreRecyclerOptions<commentModel> Options = new FirestoreRecyclerOptions.Builder<commentModel>()
                .setQuery(query, commentModel.class)
                .build();
            Fire(Options);


        sendbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() !=null){
                    commenting();
                }
                else {
                    new AlertDialog.Builder(Comment.this)
                            .setMessage("You Have To Login To Comment ")
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
                }
            }
        });
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void initial() {
        intent = getIntent();
        profileimage2 = findViewById(R.id.profileimage2);
        recyclerView = findViewById(R.id.recyclerView);
        yourcommentglobal2 = findViewById(R.id.yourcommentglobal2);
        sendbtn2 = findViewById(R.id.sendbtn2);
        textView11 = findViewById(R.id.textView11);
        backbtninviewactivity2 = findViewById(R.id.backbtninviewactivity2);
        empty = findViewById(R.id.animationView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        textView11.setText(intent.getStringExtra("nameofthecourse"));
    }

    private void commenting() {
        String comments = yourcommentglobal2.getText().toString();
        if (!comments.replaceAll("\\s","").isEmpty()) {
            commentModel model = new commentModel(firebaseAuth.getCurrentUser().getDisplayName(), comments, image, firebaseAuth.getUid(), new Timestamp(new Date()));
            firebaseFirestore.collection("Stuff").document(intent.getStringExtra("nameofthecourse")).collection("Comments").add(model);
            Adapter.startListening();
            recyclerView.smoothScrollToPosition(Adapter.getItemCount());
        }
        comments = null;
        yourcommentglobal2.setText("");
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
                        image = m.get("Details").toString();
                        Picasso.get().load(image).into(profileimage2);
                    } else {
                        Picasso.get().load(image).into(profileimage2);
                    }
                }
            }
        });
    }

    private void Fire(FirestoreRecyclerOptions<commentModel> Options) {
        Adapter = new FirestoreRecyclerAdapter<commentModel, commentViewHolder>(Options) {
            @NonNull
            @Override
            public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.globalchat_singlerow, parent, false);
                return new commentViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final commentViewHolder commentViewHolder, int i, @NonNull final commentModel commentModel) {

                CharSequence date = DateFormat.format("EEEE,MMM d,yyyy h:mm:ss a ", commentModel.timestamp.toDate());
                commentViewHolder.Date.setText(date);
              //  Toast.makeText(Comment.this, commentModel.othersname, Toast.LENGTH_SHORT).show();
                if (!commentModel.othercomments.isEmpty()){
                    empty.setVisibility(View.GONE);
                }
                if (firebaseAuth.getCurrentUser() != null) {
                    if (firebaseAuth.getCurrentUser().getUid().equals(commentModel.id)) {
                        commentViewHolder.othersname.setText("You:");

                    } else {
                        commentViewHolder.othersname.setText(commentModel.othersname + ":");
                    }
                    commentViewHolder.othercomments.setText(commentModel.othercomments);
                } else {
                    commentViewHolder.othersname.setText(commentModel.othersname + ":");
                    commentViewHolder.othercomments.setText(commentModel.othercomments);
                }
                    Picasso.get().load(commentModel.othersprofilepic).into(commentViewHolder.othersprofilepic);
            }

        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter.startListening();
        recyclerView.setAdapter(Adapter);
        recyclerView.smoothScrollToPosition(Adapter.getItemCount());

//        firebaseFirestore.collection("Stuff").document(intent.getStringExtra("nameofthecourse")).
//                get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot snapshot) {
//                if (snapshot != null) {
//                    empty.setVisibility(View.INVISIBLE);
//                }else {
//                    empty.setVisibility(View.VISIBLE);
//                }
//            }
//       });
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