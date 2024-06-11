package com.example.twoMountains.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    private static final String LANGUAGE_KEY = "language_pref";

    // 侧面菜单栏
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    // 底部栏
    private ViewPager viewPager;
    private CommonTabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置语言
        setLocale(PreferenceUtil.getInstance().get(LANGUAGE_KEY, "en"));

        setContentView(R.layout.activity_main);

        if (!App.isLogin()) {
            startActivity(new Intent(this, UserSignInActivity.class));
            finish();
            return;
        }

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);

        // 初始化视图
        initView();
        // 初始化监听
        iniListener();
        // 初始化数据
        initData();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        // 侧边抽屉
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }

    private void iniListener() {
        ArrayList<CustomTabEntity> tabs = new ArrayList<>();
        tabs.add(new Tab(getString(R.string.Home), R.drawable.icon_home, R.drawable.icon_home_un));
        tabs.add(new Tab(getString(R.string.Share), R.drawable.ic_share, R.drawable.ic_share_un));
        tabs.add(new Tab(getString(R.string.Readings), R.drawable.ic_wz, R.drawable.ic_wz_un));
        tabs.add(new Tab(getString(R.string.Mine), R.drawable.icon_mine, R.drawable.icon_mine_un));

        fragments.add(HomeFragment.newInstance());
        fragments.add(ShareFragment.newInstance());
        fragments.add(ReadingsFragment.newInstance());
        fragments.add(MineFragment.newInstance());

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyViewPagerAdapter(fragments, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                Fragment fragment = fragments.get(position);
                if (fragment instanceof HomeFragment) {
                    ((HomeFragment) fragment).reloadData();
                } else if (fragment instanceof ShareFragment) {
                    ((ShareFragment) fragment).reloadData();
                } else if (fragment instanceof ReadingsFragment) {
                    ((ReadingsFragment) fragment).reloadData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
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
            public void onTabReselect(int position) {}
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    case R.id.navigation_item_deviceSettings:
                        startActivity(new Intent(MainActivity.this, DeviceSettingsActivity.class));
                        break;
                    case R.id.navigation_item_changepassword:
                        startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
                        break;
                    case R.id.navigation_item_help:
                        startActivity(new IntentWithPosition(MainActivity.this, HelpActivity.class, 0));
                        break;
                    case R.id.navigation_item_faqs:
                        startActivity(new IntentWithPosition(MainActivity.this, HelpActivity.class, 1));
                        break;
                    case R.id.navigation_item_contactus:
                        startActivity(new IntentWithPosition(MainActivity.this, HelpActivity.class, 2));
                        break;
                    case R.id.navigation_item_language:
                        // 切换语言
                        showLanguageSelectionDialog();
                        break;
                    case R.id.navigation_item_logout:
                        PreferenceUtil.getInstance().save("logger", "");
                        App.user = null;
                        startActivity(new Intent(MainActivity.this, UserSignInActivity.class));
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initData() {
        // 侧面菜单栏初始化
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void showLanguageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.language_selection);
        builder.setItems(new String[]{"English", "中文"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String lang = which == 0 ? "en" : "zh";
                setLocale(lang);
                recreate(); // 重启活动应用新语言设定
            }
        });
        builder.create().show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());

        // 保存语言偏好
        PreferenceUtil.getInstance().save(LANGUAGE_KEY, lang);
    }

    private static class IntentWithPosition extends Intent {
        public IntentWithPosition(Context context, Class<?> cls, int position) {
            super(context, cls);
            putExtra("position", position);
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyViewPagerAdapter(List<Fragment> fragments, @NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
}