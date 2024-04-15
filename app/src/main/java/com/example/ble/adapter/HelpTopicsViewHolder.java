package com.example.ble.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ble.R;


public class HelpTopicsViewHolder extends RecyclerView.ViewHolder {
    public ImageView img1;
    public TextView title;
    public TextView content;

    public HelpTopicsViewHolder(@NonNull View itemView) {
        super(itemView);
        img1 = itemView.findViewById(R.id.arrow_right);
        title = itemView.findViewById(R.id.txt_helpTitle);
        content = itemView.findViewById(R.id.txt_helpContent);
    }
}
