package com.devworm.edufree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {
    Button logout,RegisterBtnAc;
    ImageView profileimage;
    TextView nameinprofie,gmailinprofile;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Uri filepath;
    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_account, container, false);
        logout = v.findViewById(R.id.logout);
        nameinprofie = v.findViewById(R.id.nameinprofie);
        profileimage = v.findViewById(R.id.profileimage);
        RegisterBtnAc = v.findViewById(R.id.RegisterBtnAc);
        gmailinprofile = v.findViewById(R.id.gmailinprofile);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            show();
        }
        profileimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (firebaseAuth.getCurrentUser() != null) {
                   Intent intent = new Intent();
                   intent.setType("image/*");
                   intent.setAction(Intent.ACTION_GET_CONTENT);
                   startActivityForResult(intent, 101);
               }else{
                   Toast.makeText(getContext(), "Login Or Register To Change Profile Picture", Toast.LENGTH_SHORT).show();
               }
           }
       });
        if (firebaseAuth.getCurrentUser() != null) {
            nameinprofie.setText(firebaseAuth.getCurrentUser().getDisplayName());
            gmailinprofile.setText(firebaseAuth.getCurrentUser().getEmail());
        }else
        {
            gmailinprofile.setText("None");
            nameinprofie.setText("Anonymous");
        }
        if (firebaseAuth.getCurrentUser() != null) {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                }
            });
        }else {
            logout.setVisibility(View.GONE);
            RegisterBtnAc.setVisibility(View.VISIBLE);
        }
        RegisterBtnAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Register.class));

            }
        });

        return v;
    }
    public void show(){
        //Check If Has Profile Pic.
        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("Details").document("lol").
                get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot != null) {
                    if (snapshot.get("ProfilePic") != null) {
                        HashMap<String, Object> m = new HashMap<>();
                        m.put("Details", snapshot.getData().get("ProfilePic"));
                        String image = m.get("Details").toString();
                        Picasso.get().load(image).into(profileimage);
                    } else {
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            filepath = data.getData();
            Picasso.get().load(filepath).into(profileimage);
            uploadimage();
        }
    }

    private void uploadimage() {
        // Upload Image
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference update = storageReference.child("Profilepic/"+"img"+System.currentTimeMillis());
        update.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                update.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("ProfilePic",uri.toString());
                        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("Details").document("lol").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                            }
                        });
                        Picasso.get().load(uri).into(profileimage);
                        filepath = null;
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}