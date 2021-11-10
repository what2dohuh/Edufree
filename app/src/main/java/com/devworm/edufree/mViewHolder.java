package com.devworm.edufree;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class mViewHolder extends RecyclerView.ViewHolder {
    ImageView ThubnailCourse;
    LinearLayout linearLayout;
    TextView coursename,like,aboutthecourse;
    ImageButton likebtn,comment,savecourse;
    public mViewHolder(@NonNull View itemView) {
        super(itemView);
        ThubnailCourse = itemView.findViewById(R.id.imageView3);
        coursename = itemView.findViewById(R.id.coursename);
        linearLayout = itemView.findViewById(R.id.linearLayout);
        aboutthecourse = itemView.findViewById(R.id.aboutthecourse);
        comment = itemView.findViewById(R.id.comment);
        savecourse = itemView.findViewById(R.id.savecourse);
    }
}
