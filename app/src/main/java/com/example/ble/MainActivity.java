package com.example.ble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    //侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;//顶部栏
    //滑动切换页面
    private GestureDetector gestureDetector;
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
                Intent intent =new Intent(MainActivity.this,LineChartActivity.class);
                intent.putExtra("motivation", motivation);
                intent.putExtra("quantity_cigarettes", quantity_cigarettes);
                startActivity(intent);//启动Activity
            }
        });

        LinearLayout_Smoke_Free_Days.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CalendarActivity2.class));
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
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(MainActivity.this, ShareActivity.class));
                        break;
                    case R.id.navigation_item_graph:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(MainActivity.this, LineChartActivity.class));
                        break;
                    case R.id.navigation_item_calendar:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(MainActivity.this, CalendarActivity2.class));
                        break;
                    case R.id.navigation_item_devicesettings:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(MainActivity.this, Ble_Connect.class));
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
        /*
        滑动切换页面
        */
        gestureDetector = new GestureDetector(this, this);
    }





    public void showSuccessMessage(View view) {
        Snackbar.make(view, "提交成功", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        if (deltaX < 0) {
            // 滑动
            startActivity(new Intent(this, ShareActivity.class));
            finish();
        }
        return true;
    }

}