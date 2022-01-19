package com.devworm.edufree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadBlog extends AppCompatActivity {
    EditText writeBlog,title;
    ImageView viewImage;
    ImageButton AddImage,cancel,backbtn;
    FloatingActionButton sendFab;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    Uri blogThubnail;
    CardView viewCard;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_blog);
        initial();
        sendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty() || writeBlog.getText().toString().isEmpty()){
                    Toast.makeText(UploadBlog.this, "Write Somethings", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (blogThubnail !=null ) {
                     if (!blogThubnail.toString().isEmpty()) {
                        UploadImage();
                    }
                }else {
                       UploadFinal(link);
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blogThubnail = null;
                viewCard.setVisibility(View.INVISIBLE);
                viewImage.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }
        });
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void UploadImage() {
        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference uploader = storageReference.child("Thumbnail;/"+"thumbnail"+System.currentTimeMillis());
        uploader.putFile(blogThubnail).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        link = uri.toString();
                        UploadFinal(link);

                    }
                });
            }
        });
    }

    private void UploadFinal(String linkI) {
        modeBlog model = new modeBlog(writeBlog.getText().toString(), title.getText().toString(), linkI);
        firebaseFirestore.collection("Blog").document("AllBlog").collection(title.getText().toString()).add(model);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            blogThubnail = data.getData();
            Visible();
        }
    }

    private void Visible() {
        if (!blogThubnail.toString().isEmpty()) {
            viewCard.setVisibility(View.VISIBLE);
            viewImage.setVisibility(View.VISIBLE);
            Picasso.get().load(blogThubnail).into(viewImage);
            cancel.setVisibility(View.VISIBLE);
        }
    }

    private void initial() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        writeBlog = findViewById(R.id.writeBlog);
        backbtn = findViewById(R.id.backbtn);
        viewCard = findViewById(R.id.viewuserimage);
        title = findViewById(R.id.title);
        viewImage = findViewById(R.id.viewImage);
        AddImage = findViewById(R.id.AddImage);
        cancel = findViewById(R.id.cancel);
        sendFab = findViewById(R.id.sendFab);
    }
}