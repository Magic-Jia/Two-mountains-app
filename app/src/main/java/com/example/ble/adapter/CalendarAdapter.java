package com.example.ble.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ble.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private List<Date> dates;
    private Calendar currentDate;

    public CalendarAdapter(Context context, List<Date> dates, Calendar currentDate) {
        this.context = context;
        this.dates = dates;
        this.currentDate = currentDate;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.calendar_item, null);
        }

        Date date = dates.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        TextView dayText = view.findViewById(R.id.day_text);
        dayText.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        // 根据日期状态设置样式
        if (calendar.before(currentDate)) {
            dayText.setTextColor(Color.BLACK);
            dayText.setBackgroundResource(R.drawable.shape_gray_circle);
        } else if (calendar.equals(currentDate)) {
            dayText.setTextColor(Color.BLACK);
            dayText.setBackgroundResource(R.drawable.shape_green_circle);
        } else {
            dayText.setTextColor(Color.BLACK);
            dayText.setBackgroundResource(R.drawable.shape_red_circle);
        }

        return view;
    }
    public void showPreviousMonth() {
        currentDate.add(Calendar.MONTH, -1);
        notifyDataSetChanged();
    }

    public void showNextMonth() {
        currentDate.add(Calendar.MONTH, 1);
        notifyDataSetChanged();
    }
}

