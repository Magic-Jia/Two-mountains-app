package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.ui.fragment.UsersRankingListFragment;
import com.example.twoMountains.ui.fragment.UsersTimeSequenceFragment;
import com.example.twoMountains.util.PreferenceUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class AdministratorMainActivity extends AppCompatActivity{
    //侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;//顶部栏

    private CommonTabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_main);
        if (!App.isLogin()) {
            startActivity(new Intent(this, UserSignInActivity.class));
            finish();
            return;
        }

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

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }
    private void iniListener() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new MyEntry("Time sequence"));
        tabEntities.add(new MyEntry("Ranking list"));
        fragments.add(UsersTimeSequenceFragment.newInstance());
        fragments.add(UsersRankingListFragment.newInstance());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MyViewPagerAdapter(tabLayout,fragments, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                fragments.get(position).onResume();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setTabData(tabEntities);
        tabLayout.setCurrentTab(0);
        viewPager.setCurrentItem(0);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                if(position == 0){
                    fragments.get(0).onResume();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 处理菜单项点击事件
                switch (item.getItemId()) {
                    case R.id.navigation_item_graph:
                        startActivity(new Intent(AdministratorMainActivity.this, LineChartActivity.class));
                        break;
                    case R.id.navigation_item_calendar:
                        startActivity(new Intent(AdministratorMainActivity.this, CalendarActivity2.class));
                        break;
                    case R.id.navigation_item_deviceConnection:
                        startActivity(new Intent(AdministratorMainActivity.this, Ble_ConnectActivity.class));
                        break;
                    case R.id.navigation_item_changepassword:
                        startActivity(new Intent(AdministratorMainActivity.this, ForgotPasswordActivity.class));
                        break;
                    case R.id.navigation_item_help:
                        Intent intent1 = new Intent(AdministratorMainActivity.this, HelpActivity.class);
                        intent1.putExtra("position",0);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_item_faqs:
                        Intent intent2 = new Intent(AdministratorMainActivity.this, HelpActivity.class);
                        intent2.putExtra("position",1);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_item_contactus:
                        Intent intent3 = new Intent(AdministratorMainActivity.this, HelpActivity.class);
                        intent3.putExtra("position",2);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_item_logout:
                        PreferenceUtil.getInstance().save("logger", "");
                        App.user = null;
                        startActivity(new Intent(AdministratorMainActivity.this, UserSignInActivity.class));
                        finish();
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

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        private CommonTabLayout tabLayout;

        public MyViewPagerAdapter(CommonTabLayout tabLayout,List<Fragment> fragments, @NonNull FragmentManager fm) {
            super(fm);
            this.tabLayout = tabLayout;
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    private class MyEntry implements CustomTabEntity {
        private String title;

        private MyEntry(String title) {
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }

    public void onResume() {
        super.onResume();
        /*initListener();
        // 更新数据和视图*/
    }
}


