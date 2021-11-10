package com.devworm.edufree;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class commentViewHolder extends RecyclerView.ViewHolder {
    TextView othersname,Date,othercomments;
    ImageView othersprofilepic;
    public commentViewHolder(@NonNull View itemView) {
        super(itemView);
        othercomments = itemView.findViewById(R.id.othercomments);
        othersname = itemView.findViewById(R.id.othersname);
        Date = itemView.findViewById(R.id.Date);
        othersprofilepic = itemView.findViewById(R.id.othersprofilepic);
    }
}
