package com.example.ble.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ble.R;
import com.example.ble.adapter.CalendarAdapter;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    //侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;//顶部栏
    private GridView calendarGrid;
    private CalendarAdapter calendarAdapter;
    private List<Date> dates;
    private Calendar todayDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();

        calendarGrid = findViewById(R.id.calendar_grid);

        // 初始化dates列表
        dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 42; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // 初始化currentDate为当天日期
        todayDate = Calendar.getInstance();

        TextView year_month = findViewById(R.id.text_month_year);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String formattedDate = sdf.format(todayDate.getTime());
        year_month.setText(formattedDate);

        calendarAdapter = new CalendarAdapter(getApplicationContext(), dates, todayDate);
        calendarGrid.setAdapter((android.widget.ListAdapter) calendarAdapter);

        // 设置点击事件监听器
        calendarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 处理日期点击事件
                // 可以根据日期状态执行不同的操作
            }
        });

        // 设置上个月按钮点击事件
        ImageView btnPrevMonth = findViewById(R.id.btn_prev_month);
        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换到上一个月
                calendarAdapter.showPreviousMonth();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String formattedDate = sdf.format(todayDate.getTime());
                year_month.setText(formattedDate);
            }
        });

        // 设置下个月按钮点击事件
        ImageView btnNextMonth = findViewById(R.id.btn_next_month);
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换到下一个月
                calendarAdapter.showNextMonth();
            }
        });
    }

    private void initView() {
        //侧边抽屉
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }

    private void iniListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 处理菜单项点击事件
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(CalendarActivity.this, MainActivity.class));
                        break;
                    case R.id.navigation_item_share:
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(CalendarActivity.this, ShareActivity.class));
                        break;
                    case R.id.navigation_item_graph:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(CalendarActivity.this, LineChartActivity.class));
                        break;
                    case R.id.navigation_item_calendar:
                        // 处理菜单项2的点击事件

                        break;
                    case R.id.navigation_item_devicesettings:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(CalendarActivity.this, Ble_ConnectActivity.class));
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
}