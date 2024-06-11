package com.example.twoMountains.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;


public class AllUsersViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView account;
    public TextView startDate;
    public TextView quitDate;
    public TextView smokeFreeDays;
    public Button graph;
    public Button calender;
    public Button profile;

    public AllUsersViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.userName);
        account = itemView.findViewById(R.id.userAccount);
        startDate = itemView.findViewById(R.id.tv_startDate2);
        quitDate = itemView.findViewById(R.id.tv_quitDate2);
        smokeFreeDays = itemView.findViewById(R.id.tv_smokeFreeDays2);
        graph = itemView.findViewById(R.id.btn_graph);
        calender = itemView.findViewById(R.id.btn_calender);
        profile = itemView.findViewById(R.id.btn_profile);
    }
}
