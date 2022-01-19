package com.devworm.edufree;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BlogFragment extends Fragment {
    RecyclerView blogReac;
    FloatingActionButton addBlog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_blog, container, false);
        blogReac = v.findViewById(R.id.blogReac);
        addBlog = v.findViewById(R.id.addBlog);
        addBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),UploadBlog.class));
            }
        });
        return v;
    }
}