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
import com.example.twoMountains.ui.fragment.HomeFragment;
import com.example.twoMountains.ui.fragment.MineFragment;
import com.example.twoMountains.ui.fragment.ReadingsFragment;
import com.example.twoMountains.ui.fragment.ShareFragment;
import com.example.twoMountains.util.PreferenceUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    //侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;//顶部栏
    //底部栏
    private ViewPager viewPager;
    private CommonTabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!App.isLogin()) {
            startActivity(new Intent(this, UserSignInActivity.class));
            finish();
            return;
        }
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();

    }
    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        //侧边抽屉
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

    }
    private void iniListener() {
        ArrayList<CustomTabEntity> tabs = new ArrayList<>();
        tabs.add(new Tab("Home", R.drawable.icon_home, R.drawable.icon_home_un));
        tabs.add(new Tab("Share", R.drawable.ic_share, R.drawable.ic_share_un));
        tabs.add(new Tab("Readings", R.drawable.ic_wz, R.drawable.ic_wz_un));
        tabs.add(new Tab("Mine", R.drawable.icon_mine, R.drawable.icon_mine_un));
        fragments.add(HomeFragment.newInstance());
        fragments.add(ShareFragment.newInstance());
        fragments.add(ReadingsFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyViewPagerAdapter(fragments, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                // 切换到第一页时重新加载第一个 Fragment
                if (position == 0) {
                    // 获取第一个 Fragment
                    Fragment fragment = fragments.get(position);
                    if (fragment instanceof HomeFragment) {
                        ((HomeFragment) fragment).reloadData(); // 调用重新加载数据的方法
                    }
                }
                // 切换到第二页时重新加载第二个 Fragment
                if (position == 1) {
                    // 获取第二个 Fragment
                    Fragment fragment = fragments.get(position);
                    if (fragment instanceof ShareFragment) {
                        ((ShareFragment) fragment).reloadData(); // 调用重新加载数据的方法
                    }
                }
                // 切换到第三页时重新加载第三个 Fragment
                if (position == 2) {
                    // 获取第三个 Fragment
                    Fragment fragment = fragments.get(position);
                    if (fragment instanceof ReadingsFragment) {
                        ((ReadingsFragment) fragment).reloadData(); // 调用重新加载数据的方法
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setTabData(tabs);
        tabLayout.setCurrentTab(position);
        viewPager.setCurrentItem(position);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
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
                        startActivity(new Intent(MainActivity.this, LineChartActivity.class));
                        break;
                    case R.id.navigation_item_calendar:
                        startActivity(new Intent(MainActivity.this, CalendarActivity2.class));
                        break;
                    case R.id.navigation_item_deviceConnection:
                        startActivity(new Intent(MainActivity.this, Ble_ConnectActivity.class));
                        break;
                    case R.id.navigation_item_changepassword:
                        startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
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
                    case R.id.navigation_item_logout:
                        PreferenceUtil.getInstance().save("logger", "");
                        App.user = null;
                        startActivity(new Intent(MainActivity.this, UserSignInActivity.class));
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

    public static boolean isInteger(String str) {
        int number = Integer.parseInt(str);
        if (number > 0 && number % 1 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyViewPagerAdapter(List<Fragment> fragments, @NonNull FragmentManager fm) {
            super(fm); // 在这里调用父类的构造函数
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

    private class Tab implements CustomTabEntity {
        private String title;
        private int select;
        private int unSelect;

        public Tab(String title, int select, int unSelect) {
            this.title = title;
            this.select = select;
            this.unSelect = unSelect;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return select;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelect;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


