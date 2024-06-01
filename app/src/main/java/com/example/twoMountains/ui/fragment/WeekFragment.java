package com.example.twoMountains.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekFragment extends Fragment {
    private LineChart lineChart1;
    private LineChart lineChart2;
    List<Calendar> calendars = new ArrayList<>();//一周的日期
    List<Entry> entries = new ArrayList<>();
    List<Entry> entriesVaping = new ArrayList<>();
    List<Entry> entriesSmoking = new ArrayList<>();
    private UserBean userBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userBean = DBCreator.getUserDao().queryUserById(getActivity().getIntent().getIntExtra("userId", App.user.id));

// 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 设置日期为周一
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendars.add((Calendar) calendar.clone());

        // 设置日期为周二
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        calendars.add((Calendar) calendar.clone());

        // 设置日期为周三
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        calendars.add((Calendar) calendar.clone());

        // 设置日期为周四
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        calendars.add((Calendar) calendar.clone());

        // 设置日期为周五
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        calendars.add((Calendar) calendar.clone());

        // 设置日期为周六
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendars.add((Calendar) calendar.clone());

        // 获取本周的最后一天（周日）
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        calendars.add((Calendar) calendar.clone()); // 添加本周日到列表

        // 创建SimpleDateFormat对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //读取motivation数据
        for(int i=0;i<7;i++){
            if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).motivation != -1)
                entries.add(new Entry(i+1, DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).motivation));
        }
        //读取vape数据
        for(int i=0;i<7;i++){
            if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).vapeQuantity != -1)
                entriesVaping.add(new Entry(i+1, DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).vapeQuantity));
        }
        //读取cigarette数据
        for(int i=0;i<7;i++){
            if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).cigaretteQuantity != -1)
                entriesSmoking.add(new Entry(i+1, DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).cigaretteQuantity));
        }


        View view = inflater.inflate(R.layout.fragment_week, container, false);
        lineChart1 = view.findViewById(R.id.linechart1);
        // 取消第一个图表的图例
        lineChart1.getLegend().setEnabled(false);
        // 设置图表1的顶部额外偏移量（间距）
        lineChart1.setExtraTopOffset(20f); // 设置一个10f的额外顶部偏移量
        // 设置图表1的底部额外偏移量（间距）
        lineChart1.setExtraBottomOffset(20f); // 设置一个10f的额外顶部偏移量
        lineChart1.setBackgroundColor(getResources().getColor(R.color.green));



        /*entries.add(new Entry(4, 79.5f));
        entries.add(new Entry(5, 75.1f));
        entries.add(new Entry(6, 85f));
        entries.add(new Entry(7, 78f));*/

        LineDataSet dataSet = new LineDataSet(entries, "Motivation");
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);
        // 设置数据点的颜色
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setValueFormatter(new PercentFormatter());

        //判断dataSet是否为空
        if(dataSet.getEntryCount() == 0){
            LineData emptyLineData = new LineData();
            lineChart1.setData(emptyLineData);
        }else{
            LineData lineData = new LineData(dataSet);
            lineChart1.setData(lineData);
        }

        XAxis xAxis = lineChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);

        YAxis leftAxis = lineChart1.getAxisLeft();
        leftAxis.setValueFormatter(new PercentFormatter());
        leftAxis.setDrawGridLines(false);

        lineChart1.getAxisRight().setEnabled(false);
        lineChart1.getDescription().setEnabled(false);
        lineChart1.setScaleEnabled(true);

        lineChart1.invalidate();

        /*
        第二个图
        */
        lineChart2 = view.findViewById(R.id.linechart2);
        lineChart2.setBackgroundColor(Color.WHITE);



        /*entriesSmoking.add(new Entry(3, 32f));
        entriesSmoking.add(new Entry(4, 29f));
        entriesSmoking.add(new Entry(5, 23f));
        entriesSmoking.add(new Entry(6, 22f));*/
        /*entriesSmoking.add(new Entry(7, 15f));*/

        LineDataSet dataSetVaping = new LineDataSet(entriesSmoking, "Smoking");
        dataSetVaping.setColor(Color.RED);
        // 设置数据点的颜色
        dataSetVaping.setCircleColor(Color.RED); // 设置数据点的颜色为红色


        /*entriesVaping.add(new Entry(4, 13f));
        entriesVaping.add(new Entry(5, 19f));
        entriesVaping.add(new Entry(6, 22f));*/
        /*entriesVaping.add(new Entry(7, 26f));*/

        LineDataSet dataSetSmoking = new LineDataSet(entriesVaping, "Vaping");
        dataSetSmoking.setColor(Color.BLUE);
        // 设置数据点的颜色
        dataSetSmoking.setCircleColor(Color.BLUE); // 设置数据点的颜色为蓝色

        //判断dataSetVaping, dataSetSmoking是否为空
        if(dataSetVaping.getEntryCount() == 0 && dataSetSmoking.getEntryCount() == 0){
            LineData emptyLineData = new LineData();
            lineChart2.setData(emptyLineData);
        }else if(dataSetVaping.getEntryCount() == 0){
            LineData LineData1 = new LineData(dataSetSmoking);
            lineChart2.setData(LineData1);
            /*Log.d("ProgramFragment", "空");*/
        }else if(dataSetSmoking.getEntryCount() == 0){
            LineData lineData2 = new LineData(dataSetVaping);
            lineChart2.setData(lineData2);
            Log.d("ProgramFragment", "空");
        }else{
            LineData lineData2 = new LineData(dataSetVaping, dataSetSmoking);
            lineChart2.setData(lineData2);
        }

        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1f);
        xAxis2.setLabelCount(7);

        YAxis leftAxis2 = lineChart2.getAxisLeft();
        leftAxis2.setAxisMinimum(0f);
        /*leftAxis2.setAxisMaximum(40f);
        leftAxis2.setLabelCount(9, true);
        leftAxis2.setGranularity(5f);*/

        lineChart2.getAxisRight().setEnabled(false);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.setScaleEnabled(true);

        lineChart2.invalidate();

        return view;
    }
}

