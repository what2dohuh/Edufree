package com.devworm.edufree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BlogReadMore extends AppCompatActivity {
TextView title,theBlog,date;
ImageView thumbnail;
Button visitOurWebsite;
ImageButton backbtninviewactivity,savecoursebtn;
Intent intent;
CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_read_more);
        initial();
        intent=getIntent();
        final String titlestr=intent.getStringExtra("title");
        String theBlogstr=intent.getStringExtra("theBlog");
        String imagestr=intent.getStringExtra("image");
        String Timestampstr =intent.getStringExtra("Timestamp");
        title.setText(titlestr);
        theBlog.setText(theBlogstr);
        if (imagestr == null){
            cardView.setVisibility(View.GONE);
        }
        else {
            Picasso.get().load(imagestr).into(thumbnail);
        }
        savecoursebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Comment.class);
                intent.putExtra("nameofthecourse",titlestr);
                intent.putExtra("Type","BlogComt");
                startActivity(intent);
            }
        });backbtninviewactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        date.setText(Timestampstr);
        visitOurWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://edufreeteam1.web.app"));
                startActivity(i);
            }
        });
    }

    private void initial() {
        title = findViewById(R.id.titleB);
        cardView = findViewById(R.id.cardView);
        theBlog = findViewById(R.id.theBlog);
        date = findViewById(R.id.date);
        thumbnail = findViewById(R.id.thumbnail);
        savecoursebtn = findViewById(R.id.savecoursebtn);
        visitOurWebsite = findViewById(R.id.visitOurWebsite);
        backbtninviewactivity = findViewById(R.id.backbtninviewactivity);
    }
}