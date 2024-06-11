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
import com.example.twoMountains.bean.CigaretteDataBean;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProgramFragment extends Fragment {

    private LineChart lineChart1;
    private LineChart lineChart2;
    List<CigaretteDataBean> cigaretteDataBeans = new ArrayList<>();
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

        // 创建SimpleDateFormat对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cigaretteDataBeans = DBCreator.getCigaretteDataDao().loadAllByUser(userBean.id);
        Log.d("ProgramFragment", "Total data points: " + cigaretteDataBeans.size());
        if (!cigaretteDataBeans.isEmpty()) {
            Log.d("ProgramFragment", "Sample data point: " + cigaretteDataBeans.get(0).date);
        }

        // 使用比较器对 realData 列表进行排序
        Collections.sort(cigaretteDataBeans, new cigaretteDataBeanComparator());
        Log.d("ProgramFragment", "Total data points: " + cigaretteDataBeans.size());
        if (!cigaretteDataBeans.isEmpty()) {
            Log.d("ProgramFragment", "Sample data point: " + cigaretteDataBeans.get(0).date);
        }
        //去掉startDate前的数据
        if(cigaretteDataBeans.size()>1){
            cigaretteDataBeans = cigaretteDataBeans.subList(1,cigaretteDataBeans.size());
        }

        for(int i = 0;i < cigaretteDataBeans.size();i++){
            if(cigaretteDataBeans.get(i) == null) continue;
            if(cigaretteDataBeans.get(i).motivation != -1)
                entries.add(new Entry(i+1,cigaretteDataBeans.get(i).motivation));
            if(cigaretteDataBeans.get(i).cigaretteQuantity != -1)
                entriesSmoking.add(new Entry(i+1,cigaretteDataBeans.get(i).cigaretteQuantity));
            if(cigaretteDataBeans.get(i).vapeQuantity != -1)
                entriesVaping.add(new Entry(i+1,cigaretteDataBeans.get(i).vapeQuantity));
        }
        Log.d("ProgramFragment", "Total entriesSmoking points: " + entriesSmoking.size());
        if (!entriesSmoking.isEmpty()) {
            Log.d("ProgramFragment", "Sample entriesSmoking point: " + entriesSmoking.get(0).toString());
        }

        View view = inflater.inflate(R.layout.fragment_program, container, false);
        lineChart1 = view.findViewById(R.id.linechart1);
        // 取消第一个图表的图例
        lineChart1.getLegend().setEnabled(false);
        // 设置图表1的顶部额外偏移量（间距）
        lineChart1.setExtraTopOffset(20f); // 设置一个10f的额外顶部偏移量
        // 设置图表1的底部额外偏移量（间距）
        lineChart1.setExtraBottomOffset(20f); // 设置一个10f的额外顶部偏移量
        lineChart1.setBackgroundColor(getResources().getColor(R.color.green));

        LineDataSet dataSet = new LineDataSet(entries,null);
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
        // 设置横坐标的日期标签
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value - 1; // 将横坐标值转换为列表索引
                if (index >= 0 && index < cigaretteDataBeans.size()) {
                    return cigaretteDataBeans.get(index).date; // 获取对应索引的日期并返回
                }
                return ""; // 如果索引无效，则返回空字符串
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        // 设置标签的旋转角度
        /*xAxis.setLabelRotationAngle(45); // 设置标签旋转角度为45度*/
        // 设置标签的间距和边距
        xAxis.setSpaceMin(0.5f); // 设置标签之间的最小间距
        xAxis.setSpaceMax(0.5f); // 设置标签之间的最大间距

        YAxis leftAxis = lineChart1.getAxisLeft();
        leftAxis.setValueFormatter(new PercentFormatter());
        leftAxis.setDrawGridLines(false);

        lineChart1.getAxisRight().setEnabled(false);
        lineChart1.getDescription().setEnabled(false);
        lineChart1.setScaleEnabled(true);

        // 添加黄线
        LimitLine limitLine1 = new LimitLine(70f, "70%");
        limitLine1.setLineWidth(2f);
        limitLine1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine1.setTextSize(10f);
        limitLine1.setLineColor(Color.YELLOW);

        // 获取 Y 轴，添加限制线
        leftAxis.addLimitLine(limitLine1);

        lineChart1.invalidate();

        /*
        第二个图
        */
        lineChart2 = view.findViewById(R.id.linechart2);
        lineChart2.setBackgroundColor(Color.WHITE);

        LineDataSet dataSetVaping = new LineDataSet(entriesVaping, "Vaping");
        dataSetVaping.setColor(Color.BLUE);
        // 设置数据点的颜色
        dataSetVaping.setCircleColor(Color.BLUE);

        LineDataSet dataSetSmoking = new LineDataSet(entriesSmoking, "Smoking");
        dataSetSmoking.setColor(Color.RED);
        // 设置数据点的颜色
        dataSetSmoking.setCircleColor(Color.RED);

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
        // 设置横坐标的日期标签
        xAxis2.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value - 1; // 将横坐标值转换为列表索引
                if (index >= 0 && index < cigaretteDataBeans.size()) {
                    return cigaretteDataBeans.get(index).date; // 获取对应索引的日期并返回
                }
                return ""; // 如果索引无效，则返回空字符串
            }
        });
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1f);
        // 设置标签的旋转角度
        /*xAxis.setLabelRotationAngle(45); // 设置标签旋转角度为45度*/
        // 设置标签的间距和边距
        xAxis2.setSpaceMin(0.5f); // 设置标签之间的最小间距
        xAxis2.setSpaceMax(0.5f); // 设置标签之间的最大间距

        YAxis leftAxis2 = lineChart2.getAxisLeft();
        leftAxis2.setAxisMinimum(0f);
        /*leftAxis2.setAxisMaximum(40f);
        leftAxis2.setLabelCount(9, true);*/
        /*leftAxis2.setGranularity(5f);*/

        lineChart2.getAxisRight().setEnabled(false);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.setScaleEnabled(true);

        lineChart2.invalidate();

        return view;
    }

    // 在 UsersTimeSequenceFragment 类中添加一个比较器类
    private static class cigaretteDataBeanComparator implements Comparator<CigaretteDataBean> {
        @Override
        public int compare(CigaretteDataBean cigaretteDataBean1, CigaretteDataBean cigaretteDataBean2) {
            // 按照 quitDate 升序排序
            try {
                return Long.compare(new SimpleDateFormat("yyyy-MM-dd").parse(cigaretteDataBean1.date).getTime(), new SimpleDateFormat("yyyy-MM-dd").parse(cigaretteDataBean2.date).getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

