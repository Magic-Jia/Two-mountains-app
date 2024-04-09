package com.example.ble.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ble.LineChartActivity;
import com.example.ble.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class WeekFragment extends Fragment {

    private LineChart lineChart1;
    private LineChart lineChart2;
    List<Entry> entries = new ArrayList<>();
    List<Entry> entriesVaping = new ArrayList<>();
    List<Entry> entriesSmoking = new ArrayList<>();
    public String today_motivation;
    public float yesterday_quantity_cigarettes=-1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*entries.add(new Entry(1, 83f));
        entries.add(new Entry(2, 68.8f));
        entries.add(new Entry(3, 81f));
        entriesSmoking.add(new Entry(1, 38f));
        entriesSmoking.add(new Entry(2, 34f));
        entriesVaping.add(new Entry(1, 0f));
        entriesVaping.add(new Entry(2, 5f));
        entriesVaping.add(new Entry(3, 7f));
        //通过Activity.getIntent()获取当前页面接收到的Intent。
        Intent intent =getActivity().getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        today_motivation=intent.getStringExtra("motivation");
        yesterday_quantity_cigarettes = intent.getFloatExtra("quantity_cigarettes",0);
        if(today_motivation != null){
            entries.add(new Entry(4, Float.parseFloat(today_motivation.substring(0,today_motivation.length()-1))));
        }
        if(yesterday_quantity_cigarettes!=-1)
            entriesSmoking.add(new Entry(3, yesterday_quantity_cigarettes));*/

        entries.addAll(((LineChartActivity)getActivity()).entries);
        entriesVaping.addAll(((LineChartActivity)getActivity()).entriesVaping);
        entriesSmoking.addAll(((LineChartActivity)getActivity()).entriesSmoking);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        lineChart1 = view.findViewById(R.id.linechart1);
        lineChart1.setBackgroundColor(getResources().getColor(R.color.green));



        /*entries.add(new Entry(4, 79.5f));
        entries.add(new Entry(5, 75.1f));
        entries.add(new Entry(6, 85f));
        entries.add(new Entry(7, 78f));*/

        LineDataSet dataSet = new LineDataSet(entries, "Motivation");
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueFormatter(new PercentFormatter());

        LineData lineData = new LineData(dataSet);
        lineChart1.setData(lineData);

        XAxis xAxis = lineChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        YAxis leftAxis = lineChart1.getAxisLeft();
        leftAxis.setValueFormatter(new PercentFormatter());
        leftAxis.setDrawGridLines(false);

        lineChart1.getAxisRight().setEnabled(false);
        lineChart1.getDescription().setEnabled(false);
        lineChart1.setScaleEnabled(false);

        lineChart1.invalidate();

        /*
        第二个图
        */
        lineChart2 = view.findViewById(R.id.linechart2);
        lineChart2.setBackgroundColor(Color.WHITE);



        /*entriesSmoking.add(new Entry(3, 32f));
        entriesSmoking.add(new Entry(4, 29f));
        entriesSmoking.add(new Entry(5, 23f));
        entriesSmoking.add(new Entry(6, 22f));
        entriesSmoking.add(new Entry(7, 15f));*/

        LineDataSet dataSetVaping = new LineDataSet(entriesSmoking, "Smoking");
        dataSetVaping.setColor(Color.BLUE);



        /*entriesVaping.add(new Entry(4, 13f));
        entriesVaping.add(new Entry(5, 19f));
        entriesVaping.add(new Entry(6, 22f));
        entriesVaping.add(new Entry(7, 26f));*/

        LineDataSet dataSetSmoking = new LineDataSet(entriesVaping, "Vaping");
        dataSetSmoking.setColor(Color.RED);

        LineData lineData2 = new LineData(dataSetVaping, dataSetSmoking);
        lineChart2.setData(lineData2);

        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1f);

        YAxis leftAxis2 = lineChart2.getAxisLeft();
        leftAxis2.setAxisMinimum(0f);
        leftAxis2.setAxisMaximum(40f);
        leftAxis2.setLabelCount(9, true);
        leftAxis2.setGranularity(5f);

        lineChart2.getAxisRight().setEnabled(false);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.setScaleEnabled(false);

        lineChart2.invalidate();

        return view;
    }
}

