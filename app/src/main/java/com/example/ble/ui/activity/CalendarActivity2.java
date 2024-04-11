package com.example.ble.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ble.Day;
import com.example.ble.R;
import com.example.ble.view.CalendarView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity2 extends AppCompatActivity implements View.OnClickListener {
    //侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;//顶部栏
    private TextView tv_pre;
    private TextView tv_month;
    private TextView tv_next;
    /**
     * 日历控件
     */
    private CalendarView calendar;
    /**
     * 日历对象
     */
    private Calendar cal;
    /**
     * 格式化工具
     */
    private SimpleDateFormat formatter;
    /**
     * 日期
     */
    private Date curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar2);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }
    private void initView() {
        //侧边抽屉
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        tv_pre = (TextView) findViewById(R.id.tv_pre);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_next = (TextView) findViewById(R.id.tv_next);
        calendar = (CalendarView) findViewById(R.id.calendar);
        cal = Calendar.getInstance();
        //初始化界面
        init();

        calendar.setOnDrawDays(new CalendarView.OnDrawDays() {
            @Override
            public boolean drawDay(Day day, Canvas canvas, Context context, Paint paint) {
//                paint.setStrokeWidth(1);
//                paint.setStyle(Paint.Style.STROKE);
//                canvas.drawRect(0,0,day.width,day.height,paint);
////               给加上五角星
//                if (day.dateText.equals("-1")) {
//                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.wujiaoxing), 20, 20, paint);
//                } else if (day.dateText.endsWith("1") || day.dateText.endsWith("4") || day.dateText.endsWith("8")) {
//                    //加上西瓜
//                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.xigua), 20, 20, paint);
//                }


                return false;
            }

            @Override
            public void drawDayAbove(Day day, Canvas canvas, Context context, Paint paint) {
                if (day.dateText.endsWith("1") || day.dateText.endsWith("4") || day.dateText.endsWith("8")) {
                    //加上西瓜
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_cha), 20, 20, paint);
                }
                else{
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dui), 20, 20, paint);
                }
            }
        });
        //给左右月份设置点击监听事件
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);


    }
    private void iniListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 处理菜单项点击事件
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(CalendarActivity2.this, MainActivity.class));
                        break;
                    case R.id.navigation_item_share:
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(CalendarActivity2.this, ShareActivity.class));
                        break;
                    case R.id.navigation_item_graph:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(CalendarActivity2.this, LineChartActivity.class));
                        break;
                    case R.id.navigation_item_calendar:
                        // 处理菜单项2的点击事件

                        break;
                    case R.id.navigation_item_devicesettings:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(CalendarActivity2.this, Ble_ConnectActivity.class));
                        break;
                    // 处理更多菜单项的点击事件
                }
                drawerLayout.closeDrawer(GravityCompat.START); // 点击菜单项后关闭侧边菜单栏
                return true;
            }
        });
    }

    private void initData() {
        /*
         * 侧面菜单栏
         * */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }




    /**
     * 初始化界面
     */
    private void init() {
        formatter = new SimpleDateFormat("yyyy年MM月");
        //获取当前时间
        curDate = cal.getTime();
        String str = formatter.format(curDate);
        tv_month.setText(str);
        String strPre = (cal.get(Calendar.MONTH)) + "月";
        if (strPre.equals("0月")) {
            strPre = "12月";
        }
        tv_pre.setText(strPre);
        String strNext = (cal.get(Calendar.MONTH) + 2) + "月";
        if (strNext.equals("13月")) {
            strNext = "1月";
        }
        tv_next.setText(strNext);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pre:
                cal.add(Calendar.MONTH, -1);
                init();
                calendar.setCalendar(cal);
                break;
            case R.id.tv_next:
                cal.add(Calendar.MONTH, +1);
                init();
                calendar.setCalendar(cal);
                break;
        }

    }
}