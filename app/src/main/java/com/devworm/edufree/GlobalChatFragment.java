package com.devworm.edufree;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import javax.annotation.Nullable;

public class GlobalChatFragment extends Fragment {
RecyclerView globalchatrec;
EditText yourcommentglobal;
ImageButton sendbtn;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
FirestoreRecyclerAdapter<commentModel, commentViewHolder> Adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_global_chat, container, false);
        initial(v);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        Query query = firebaseFirestore.collection("GlobalChat").orderBy("timestamp",Query.Direction.ASCENDING);

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

                CharSequence date = DateFormat.format("EEEE,MMM d,yyyy h:mm:ss a ", commentModel.timestamp.toDate());
                commentViewHolder.Date.setText(date);
                if (firebaseAuth.getCurrentUser() != null) {
                  if (firebaseAuth.getCurrentUser().getUid().equals(commentModel.id)) {
                      commentViewHolder.othersname.setText("You:");
                  }else {commentViewHolder.othersname.setText(commentModel.othersname);}
              }else {
                  commentViewHolder.othersname.setText(commentModel.othersname);
              }
                commentViewHolder.othercomments.setText(commentModel.othercomments);
            }

            @Override
            public void onDataChanged() {
                globalchatrec.post(new Runnable() {
                    @Override
                    public void run() {
                        globalchatrec.smoothScrollToPosition(Adapter.getItemCount());
                    }
                });

            }
        };
        globalchatrec.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter.startListening();
        globalchatrec.setAdapter(Adapter);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = yourcommentglobal.getText().toString();
                if (!comments.replaceAll("\\s","").isEmpty()) {
                    commentModel model;
                    if (firebaseAuth.getCurrentUser() !=null) {
                         model = new commentModel(firebaseAuth.getCurrentUser().getDisplayName(), comments, null, firebaseAuth.getUid(), new Timestamp(new Date()));
                    }else {
                         model = new commentModel("Anonymous", comments, null, null, new Timestamp(new Date()));
                    }
                    firebaseFirestore.collection("GlobalChat").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            globalchatrec.smoothScrollToPosition(Adapter.getItemCount());
                        }
                    });
                }
                comments = null;
                yourcommentglobal.setText("");
            }
        });
        return v;
    }

    private void initial(View v) {
        globalchatrec = v.findViewById(R.id.globalchatrec);
        yourcommentglobal = v.findViewById(R.id.yourcommentglobal);
        sendbtn = v.findViewById(R.id.sendbtn);
    }
}