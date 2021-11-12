package com.devworm.edufree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Admin extends AppCompatActivity {
LinearLayout Thubnail;
EditText nameofcourseadmin,About,Link;
CheckBox Androiddev,Ethicalhacking,Project,Webdev,Businessandmarketing;
Button Upload;
FirebaseFirestore firebaseFirestore;
Uri CourseThubnail,Final;
StorageReference storageReference;
boolean androiddev,ethicalhacking,project,webdev,businessandmarketing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Initial();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image :");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        Androiddev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                androiddev = isChecked;
            }
        });
        Ethicalhacking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ethicalhacking = isChecked;
            }
        });
        Project.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                project = isChecked;
            }
        });
        Webdev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                webdev = isChecked;
            }
        });
        Businessandmarketing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                businessandmarketing = isChecked;
            }
        });
        Thubnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uploading(progressDialog);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            CourseThubnail = data.getData();
        }
    }

    private void Uploading(final ProgressDialog progressDialog) {
        progressDialog.show();
        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference uploader = storageReference.child("Thumbnail;/"+"thumbnail"+System.currentTimeMillis());
        uploader.putFile(CourseThubnail).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Final = uri;
                        final StorageReference uploaderimage = storageReference.child("image/"+"img"+System.currentTimeMillis());
                        uploaderimage.putFile(CourseThubnail).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                uploaderimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String nameofcourse = nameofcourseadmin.getText().toString();
                                        String link = Link.getText().toString();

                                        String child = "";
                                        if(androiddev){
                                            child = "AndroidDev";
                                        }
                                        if(ethicalhacking){
                                            child = "EthicalHacking";
                                        }
                                        if(project){
                                            child = "Projects";
                                        }
                                        if (webdev){
                                            child = "WebDev";
                                        }
                                        if (businessandmarketing){
                                            child = "BusinessAndMarketing";
                                        }
                                        progressDialog.dismiss();
                                        Model model= new Model(nameofcourse,uri.toString(),link,nameofcourse.toLowerCase().replaceAll("\\s+", ""),child,About.getText().toString());
                                        firebaseFirestore.collection("Courses").document("Categories").collection(child).add(model);
                                        firebaseFirestore.collection("Courses").document("Categories").collection("All").add(model);
                                    }
                                });
                            }
                        });

                    }
                });
            }
        });
    }

    private void Initial() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        nameofcourseadmin = findViewById(R.id.nameofcourseadmin);
        Thubnail = findViewById(R.id.Thubnail);
        About = findViewById(R.id.About);
        Link = findViewById(R.id.Link);
        Androiddev = findViewById(R.id.Androiddev);
        Ethicalhacking = findViewById(R.id.Ethicalhacking);
        Project = findViewById(R.id.Project);
        Webdev = findViewById(R.id.Webdev);
        Businessandmarketing = findViewById(R.id.Businessandmarketing);
        Upload = findViewById(R.id.Upload);
    }
}