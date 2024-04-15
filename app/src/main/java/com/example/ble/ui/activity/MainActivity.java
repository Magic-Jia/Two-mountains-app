package com.example.ble.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ble.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity{
    //侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;//顶部栏
    private AutoCompleteTextView autoCompleteTextView;
    private String[] options = {"0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
    private ArrayAdapter<String> adapter;
    private Button btn_submit;
    private AutoCompleteTextView textview_motivation;
    private EditText edittext_quantity_cigarettes;
    private LinearLayoutCompat LinearLayout_Smoke_Free_Days;

    private String motivation;
    private float quantity_cigarettes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();

    }
    private void initView() {
        textview_motivation = findViewById(R.id.motivation2);
        edittext_quantity_cigarettes = findViewById(R.id.quantity_cigarettes);
        btn_submit = findViewById(R.id.button_submit);
        LinearLayout_Smoke_Free_Days = findViewById(R.id.LinearLayout);
        autoCompleteTextView = findViewById(R.id.motivation2);
        //侧边抽屉
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

    }
    private void iniListener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSuccessMessage(view);
                motivation = textview_motivation.getText().toString();
                quantity_cigarettes = Float.parseFloat(edittext_quantity_cigarettes.getText().toString());
                Intent intent =new Intent(MainActivity.this, LineChartActivity.class);
                intent.putExtra("motivation", motivation);
                intent.putExtra("quantity_cigarettes", quantity_cigarettes);
                startActivity(intent);//启动Activity
            }
        });

        LinearLayout_Smoke_Free_Days.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalendarActivity2.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 处理菜单项点击事件
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        // 处理菜单项1的点击事件
                        break;
                    case R.id.navigation_item_share:
                        startActivity(new Intent(MainActivity.this, ShareActivity.class));
                        break;
                    case R.id.navigation_item_graph:
                        startActivity(new Intent(MainActivity.this, LineChartActivity.class));
                        break;
                    case R.id.navigation_item_calendar:
                        startActivity(new Intent(MainActivity.this, CalendarActivity2.class));
                        break;
                    case R.id.navigation_item_dailyReadings:
                        startActivity(new Intent(MainActivity.this, DailyReadingsActivity.class));
                        break;
                    case R.id.navigation_item_devicesettings:
                        startActivity(new Intent(MainActivity.this, Ble_ConnectActivity.class));
                        break;
                    case R.id.navigation_item_help:
                        Intent intent1 = new Intent(MainActivity.this, HelpActivity.class);
                        intent1.putExtra("position",0);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_item_faqs:
                        Intent intent2 = new Intent(MainActivity.this, HelpActivity.class);
                        intent2.putExtra("position",1);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_item_contactus:
                        Intent intent3 = new Intent(MainActivity.this, HelpActivity.class);
                        intent3.putExtra("position",2);
                        startActivity(intent3);
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
        * 戒烟动机输入框
        * */
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, options);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1); // 设置触发下拉提示的最小字符数
        /*
        * 侧面菜单栏
        * */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }





    public void showSuccessMessage(View view) {
        Snackbar.make(view, "提交成功", Snackbar.LENGTH_SHORT).show();
    }


}


