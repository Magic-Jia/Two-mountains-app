package com.example.ble.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.ble.R;
import com.example.ble.adapter.MFragmentPagerAdapter;
import com.example.ble.ui.fragment.MonthFragment;
import com.example.ble.ui.fragment.ProgramFragment;
import com.example.ble.ui.fragment.WeekFragment;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    //侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;//顶部栏
    private TextView WeekTextView;

    private TextView MonthTextView;

    private TextView ProgramTextView;

    //实现Tab滑动效果
    private ViewPager mViewPager;


//    private ImageView LineChart;

    //动画图片偏移量
    private int offset = 0;
    private int position_one;
    private int position_two;

    //动画图片宽度
    private int bmpW;

    //当前页卡编号
    private int currIndex = 0;

    //存放Fragment
    private ArrayList<Fragment> fragmentArrayList;

    //管理Fragment
    private FragmentManager fragmentManager;

    public Context context;

    public static final String TAG = "LineChartActivity";

    public List<Entry> entries = new ArrayList<>();
    public List<Entry> entriesVaping = new ArrayList<>();
    public List<Entry> entriesSmoking = new ArrayList<>();
    public String today_motivation;
    public float yesterday_quantity_cigarettes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_line_chart);

        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();

        context = this;

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //初始化TextView
        InitTextView();

        //初始化ImageView
        InitImageView();

        //初始化Fragment
        InitFragment();

        //初始化ViewPager
        InitViewPager();

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
                        startActivity(new Intent(LineChartActivity.this, MainActivity.class));
                        break;
                    case R.id.navigation_item_share:
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(LineChartActivity.this, ShareActivity.class));
                        break;
                    case R.id.navigation_item_graph:
                        // 处理菜单项2的点击事件

                        break;
                    case R.id.navigation_item_calendar:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(LineChartActivity.this, CalendarActivity2.class));
                        break;
                    case R.id.navigation_item_devicesettings:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(LineChartActivity.this, Ble_ConnectActivity.class));
                        break;
                    // 处理更多菜单项的点击事件
                }
                drawerLayout.closeDrawer(GravityCompat.START); // 点击菜单项后关闭侧边菜单栏
                return true;
            }
        });
    }

    private void initData() {
        entries.add(new Entry(1, 83f));
        entries.add(new Entry(2, 68.8f));
        entries.add(new Entry(3, 81f));
        entriesSmoking.add(new Entry(1, 38f));
        entriesSmoking.add(new Entry(2, 34f));
        entriesVaping.add(new Entry(1, 0f));
        entriesVaping.add(new Entry(2, 5f));
        entriesVaping.add(new Entry(3, 7f));
        //通过Activity.getIntent()获取当前页面接收到的Intent。
        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        today_motivation=intent.getStringExtra("motivation");
        yesterday_quantity_cigarettes = intent.getFloatExtra("quantity_cigarettes",-1);
        if(today_motivation != null){
            entries.add(new Entry(4, Float.parseFloat(today_motivation.substring(0,today_motivation.length()-1))));//去掉百分号
        }
        if(yesterday_quantity_cigarettes!=-1)
            entriesSmoking.add(new Entry(3, yesterday_quantity_cigarettes));
        /*
         * 侧面菜单栏
         * */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
    }

    /**
     * 初始化头标
     */
    private void InitTextView(){


        WeekTextView = (TextView)findViewById(R.id.week_text);

        MonthTextView = (TextView) findViewById(R.id.month_text);

        ProgramTextView = (TextView)findViewById(R.id.program_text);

        //添加点击事件
        WeekTextView.setOnClickListener(new MyOnClickListener(0));
        MonthTextView.setOnClickListener(new MyOnClickListener(1));
        ProgramTextView.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * 初始化页卡内容区
     */
    private void InitViewPager() {

        mViewPager = (ViewPager) findViewById(R.id.vPager);
        mViewPager.setAdapter(new MFragmentPagerAdapter(fragmentManager, fragmentArrayList));

        //让ViewPager缓存2个页面
        mViewPager.setOffscreenPageLimit(2);

        //设置默认打开第一页
        mViewPager.setCurrentItem(0);

        //将顶部文字恢复默认值
        resetTextViewTextColor();
        WeekTextView.setTextColor(getResources().getColor(R.color.black));

        //设置viewpager页面滑动监听事件
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
//        LineChart = (ImageView) findViewById(R.id.cursor);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        // 获取分辨率宽度
//        int screenW = dm.widthPixels;
//
//        bmpW = (screenW/3);
//
//        //设置动画图片宽度
//        setBmpW(LineChart, bmpW);
//        offset = 0;
//
//        //动画图片偏移量赋值
//        position_one = (int) (screenW / 3.0);
//        position_two = position_one * 2;

    }

    /**
     * 初始化Fragment，并添加到ArrayList中
     */
    private void InitFragment(){
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new WeekFragment());
        fragmentArrayList.add(new MonthFragment());
        fragmentArrayList.add(new ProgramFragment());

        fragmentManager = getSupportFragmentManager();

    }

    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener{
        private int index = 0 ;
        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageSelected(int position) {
            Animation animation = null ;
            switch (position){

                //当前为页卡1
                case 0:
                    //从页卡1跳转转到页卡2
                    if(currIndex == 1){
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        resetTextViewTextColor();
                        WeekTextView.setTextColor(getResources().getColor(R.color.green));
                    }else if(currIndex == 2){//从页卡1跳转转到页卡3
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                        resetTextViewTextColor();
                        WeekTextView.setTextColor(getResources().getColor(R.color.green));
                    }
                    break;

                //当前为页卡2
                case 1:
                    //从页卡1跳转转到页卡2
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        resetTextViewTextColor();
                        MonthTextView.setTextColor(getResources().getColor(R.color.green));
                    } else if (currIndex == 2) { //从页卡1跳转转到页卡2
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        resetTextViewTextColor();
                        MonthTextView.setTextColor(getResources().getColor(R.color.green));
                    }
                    break;

                //当前为页卡3
                case 2:
                    //从页卡1跳转转到页卡2
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                        resetTextViewTextColor();
                        ProgramTextView.setTextColor(getResources().getColor(R.color.green));
                    } else if (currIndex == 1) {//从页卡1跳转转到页卡2
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        resetTextViewTextColor();
                        ProgramTextView.setTextColor(getResources().getColor(R.color.green));
                    }
                    break;
            }
            currIndex = position;

            animation.setFillAfter(true);// true:图片停在动画结束位置
            animation.setDuration(300);
//            LineChart.startAnimation(animation);

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 设置动画图片宽度
     */
    private void setBmpW(ImageView imageView,int mWidth){
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        para.width = mWidth;
        imageView.setLayoutParams(para);
    }

    /**
     * 将顶部文字恢复默认值
     */
    private void resetTextViewTextColor(){

        WeekTextView.setTextColor(getResources().getColor(R.color.black));
        MonthTextView.setTextColor(getResources().getColor(R.color.black));
        ProgramTextView.setTextColor(getResources().getColor(R.color.black));
    }


}