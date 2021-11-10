package com.devworm.edufree;

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

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {
    CardView logout;
    ImageView profileimage;
    TextView nameinprofie,gmailinprofile;
    FirebaseAuth firebaseAuth;
    Uri filepath;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_account, container, false);
        logout = v.findViewById(R.id.logoutcard);
        nameinprofie = v.findViewById(R.id.nameinprofie);
        profileimage = v.findViewById(R.id.profileimage);
        gmailinprofile = v.findViewById(R.id.gmailinprofile);
        firebaseAuth=FirebaseAuth.getInstance();
       profileimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(intent,101);
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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(),Login.class));
                getActivity().finish();
            }
        });
        return v;
    }
    public void show(){
        //Check If Has Profile Pic.
    }
    @Override
    public void onStart() {
        super.onStart();
        show();

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
    }
}