package com.example.twoMountains.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.ui.activity.CalendarActivity2;
import com.example.twoMountains.ui.activity.LineChartActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersViewHolder> {
    private List<UserBean> data;

    public AllUsersAdapter(List<UserBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        AllUsersViewHolder viewHolder = new AllUsersViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllUsersViewHolder holder, int position) {
        UserBean user = data.get(position);
        holder.name.setText(user.name);
        holder.account.setText(user.account);
        holder.startDate.setText(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user.id).startDate);
        if(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user.id).quitDate != null){
            holder.quitDate.setText(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user.id).quitDate);
            // 计算两个日期之间的差
            try {
                long smokeFreeDays = ChronoUnit.DAYS.between(new SimpleDateFormat("yyyy-MM-dd").parse(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user.id).quitDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                holder.smokeFreeDays.setText(Long.toString(smokeFreeDays));
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
        } else {
            holder.quitDate.setText("Not yet");
            holder.smokeFreeDays.setText("0");
        }

        holder.graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LineChartActivity.class);
                intent.putExtra("userId",user.id);
                v.getContext().startActivity(intent);
            }
        });

        holder.calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarActivity2.class);
                intent.putExtra("userId",user.id);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
