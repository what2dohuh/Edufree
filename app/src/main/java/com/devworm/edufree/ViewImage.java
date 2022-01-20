package com.devworm.edufree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImage extends AppCompatActivity {
ImageView imageView10;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        imageView10 = findViewById(R.id.image);
        intent = getIntent();
        Picasso.get().load(intent.getStringExtra("Image")).into(imageView10);

    }
}