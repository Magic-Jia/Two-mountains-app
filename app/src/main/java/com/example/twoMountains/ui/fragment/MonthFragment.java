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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class MonthFragment extends Fragment {

    private LineChart lineChart1;
    private LineChart lineChart2;
    List<Entry> entries = new ArrayList<>();
    List<Entry> entriesVaping = new ArrayList<>();
    List<Entry> entriesSmoking = new ArrayList<>();
    // 创建一个Calendar对象，表示当前日期
    Calendar currentCalendar = Calendar.getInstance();
    // 创建一个空的列表来存储本月的日期
    List<Calendar> calendars = new ArrayList<>();
    private UserBean userBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userBean = DBCreator.getUserDao().queryUserById(getActivity().getIntent().getIntExtra("userId", App.user.id));


        // 设置当前日期为本月的第一天
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // 循环遍历本月的每一天，将每一天的Calendar对象添加到calendars列表中
        while (currentCalendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) {
            Calendar day = (Calendar) currentCalendar.clone(); // 克隆当前日期对象，以避免引用问题
            calendars.add(day);
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1); // 将日期增加一天
        }

        // 创建SimpleDateFormat对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //读取motivation数据
        for(int i=0;i<calendars.size();i++){
            if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).motivation != -1)
                entries.add(new Entry(i+1, DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).motivation));
        }
        //读取vape数据
        for(int i=0;i<calendars.size();i++){
            if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).vapeQuantity != -1)
                entriesVaping.add(new Entry(i+1, DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).vapeQuantity));
        }
        //读取cigarette数据
        for(int i=0;i<calendars.size();i++){
            if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())) != null && DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).cigaretteQuantity != -1)
                entriesSmoking.add(new Entry(i+1, DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(userBean.id, sdf.format(calendars.get(i).getTime())).cigaretteQuantity));
        }

        View view = inflater.inflate(R.layout.fragment_month, container, false);
        lineChart1 = view.findViewById(R.id.linechart1);
        // 取消第一个图表的图例
        lineChart1.getLegend().setEnabled(false);
        // 设置图表1的顶部额外偏移量（间距）
        lineChart1.setExtraTopOffset(20f); // 设置一个10f的额外顶部偏移量
        // 设置图表1的底部额外偏移量（间距）
        lineChart1.setExtraBottomOffset(20f); // 设置一个10f的额外顶部偏移量
        lineChart1.setBackgroundColor(getResources().getColor(R.color.green));

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
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1f);

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

    // 在 UsersTimeSequenceFragment 类中添加一个比较器类
    private static class UserBeanComparator implements Comparator<UserBean> {
        @Override
        public int compare(UserBean user1, UserBean user2) {
            // 按照 quitDate 升序排序
            try {
                if(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user1.id).quitDate == null)
                    return 1;
                return Long.compare(new SimpleDateFormat("yyyy-MM-dd").parse(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user1.id).quitDate).getTime(), new SimpleDateFormat("yyyy-MM-dd").parse(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user2.id).quitDate).getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

