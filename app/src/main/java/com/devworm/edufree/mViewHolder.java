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
class viewHolder extends RecyclerView.ViewHolder{
    TextView userName,Title,BlogText,time;
    ImageView profileimage,thumb;
    public viewHolder(@NonNull View itemView) {
        super(itemView);
        userName=itemView.findViewById(R.id.userName);
        Title=itemView.findViewById(R.id.Title);
        BlogText=itemView.findViewById(R.id.BlogText);
        time=itemView.findViewById(R.id.time);
        thumb=itemView.findViewById(R.id.thumbB);
        profileimage=itemView.findViewById(R.id.profileimage);
    }
}
