package com.example.ble.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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

public class MonthFragment extends Fragment {

    private LineChart lineChart1;
    private LineChart lineChart2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        lineChart1 = view.findViewById(R.id.linechart1);
        lineChart1.setBackgroundColor(getResources().getColor(R.color.green));

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 83f));
        entries.add(new Entry(2, 68.8f));
        entries.add(new Entry(3, 81f));
        entries.add(new Entry(4, 79.5f));
        entries.add(new Entry(5, 75.1f));
        entries.add(new Entry(6, 85f));
        entries.add(new Entry(7, 78f));
        entries.add(new Entry(8, 75f));
        entries.add(new Entry(9, 68.8f));
        entries.add(new Entry(10, 81f));
        entries.add(new Entry(11, 79.5f));
        entries.add(new Entry(12, 75.1f));
        entries.add(new Entry(13, 85f));
        entries.add(new Entry(14, 74f));
        entries.add(new Entry(15, 86f));
        entries.add(new Entry(16, 68.8f));
        entries.add(new Entry(17, 81f));
        entries.add(new Entry(18, 79.5f));
        entries.add(new Entry(19, 75.1f));
        entries.add(new Entry(20, 85f));
        entries.add(new Entry(21, 78f));
        entries.add(new Entry(22, 85f));
        entries.add(new Entry(23, 68.8f));
        entries.add(new Entry(24, 79f));
        entries.add(new Entry(25, 79.5f));
        entries.add(new Entry(26, 75.1f));
        entries.add(new Entry(27, 84f));
        entries.add(new Entry(28, 78f));
        entries.add(new Entry(29, 81f));
        entries.add(new Entry(30, 78f));

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

        List<Entry> entriesVaping = new ArrayList<>();
        entriesVaping.add(new Entry(1, 39f));
        entriesVaping.add(new Entry(2, 38f));
        entriesVaping.add(new Entry(3, 36f));
        entriesVaping.add(new Entry(4, 37f));
        entriesVaping.add(new Entry(5, 35f));
        entriesVaping.add(new Entry(6, 33f));
        entriesVaping.add(new Entry(7, 31f));
        entriesVaping.add(new Entry(8, 34f));
        entriesVaping.add(new Entry(9, 31f));
        entriesVaping.add(new Entry(10, 32f));
        entriesVaping.add(new Entry(11, 29f));
        entriesVaping.add(new Entry(12, 27f));
        entriesVaping.add(new Entry(13, 26f));
        entriesVaping.add(new Entry(14, 27f));
        entriesVaping.add(new Entry(15, 26f));
        entriesVaping.add(new Entry(16, 25f));
        entriesVaping.add(new Entry(17, 23f));
        entriesVaping.add(new Entry(18, 20f));
        entriesVaping.add(new Entry(19, 21f));
        entriesVaping.add(new Entry(20, 19f));
        entriesVaping.add(new Entry(21, 17f));
        entriesVaping.add(new Entry(22, 16f));
        entriesVaping.add(new Entry(23, 16f));
        entriesVaping.add(new Entry(24, 15f));
        entriesVaping.add(new Entry(25, 11f));
        entriesVaping.add(new Entry(26, 9f));
        entriesVaping.add(new Entry(27, 13f));
        entriesVaping.add(new Entry(28, 8f));
        entriesVaping.add(new Entry(29, 8f));
        entriesVaping.add(new Entry(30, 5f));
        LineDataSet dataSetVaping = new LineDataSet(entriesVaping, "Smoking");
        dataSetVaping.setColor(Color.BLUE);

        List<Entry> entriesSmoking = new ArrayList<>();
        entriesSmoking.add(new Entry(1, 0f));
        entriesSmoking.add(new Entry(2, 2f));
        entriesSmoking.add(new Entry(3, 3f));
        entriesSmoking.add(new Entry(4, 5f));
        entriesSmoking.add(new Entry(5, 6f));
        entriesSmoking.add(new Entry(6, 9f));
        entriesSmoking.add(new Entry(7, 10f));
        entriesSmoking.add(new Entry(8, 11f));
        entriesSmoking.add(new Entry(9, 13f));
        entriesSmoking.add(new Entry(10, 14f));
        entriesSmoking.add(new Entry(11, 13f));
        entriesSmoking.add(new Entry(12, 15f));
        entriesSmoking.add(new Entry(13, 16f));
        entriesSmoking.add(new Entry(14, 18f));
        entriesSmoking.add(new Entry(15, 20f));
        entriesSmoking.add(new Entry(16, 19f));
        entriesSmoking.add(new Entry(17, 21f));
        entriesSmoking.add(new Entry(18, 23f));
        entriesSmoking.add(new Entry(19, 25f));
        entriesSmoking.add(new Entry(20, 26f));
        entriesSmoking.add(new Entry(21, 25f));
        entriesSmoking.add(new Entry(22, 27f));
        entriesSmoking.add(new Entry(23, 30f));
        entriesSmoking.add(new Entry(24, 31f));
        entriesSmoking.add(new Entry(25, 32f));
        entriesSmoking.add(new Entry(26, 34f));
        entriesSmoking.add(new Entry(27, 37f));
        entriesSmoking.add(new Entry(28, 36f));
        entriesSmoking.add(new Entry(29, 38f));
        entriesSmoking.add(new Entry(30, 40f));

        LineDataSet dataSetSmoking = new LineDataSet(entriesSmoking, "Vaping");
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

