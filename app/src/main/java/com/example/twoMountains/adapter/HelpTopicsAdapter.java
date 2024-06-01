package com.example.twoMountains.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;
import com.example.twoMountains.bean.HelpTopicsBean;

import java.util.List;

public class HelpTopicsAdapter extends RecyclerView.Adapter<HelpTopicsViewHolder>{
    private List<HelpTopicsBean> data;

    public HelpTopicsAdapter(List<HelpTopicsBean> data) {
        this.data = data;
    }

    private boolean isCamDel = false;

    public void setCanDel(boolean isCamDel) {
        this.isCamDel = isCamDel;
    }

    @NonNull
    @Override
    public HelpTopicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help_topics, parent, false);
        HelpTopicsViewHolder viewHolder = new HelpTopicsViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpTopicsViewHolder holder, int position) {
        HelpTopicsBean helpTopics = data.get(position);
        holder.img1.setImageResource(R.drawable.ic_arrow_right2);
        holder.content.setText(helpTopics.helpContent);
        holder.content.setText(helpTopics.helpContent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
