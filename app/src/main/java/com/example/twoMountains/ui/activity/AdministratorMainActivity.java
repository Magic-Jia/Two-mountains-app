package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

public class AdministratorMainActivity extends AppCompatActivity{
    private ImageView btn_back;
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
        btn_back = findViewById(R.id.backBtn);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }
    private void iniListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.getInstance().save("logger", "");
                App.user = null;
                startActivity(new Intent(AdministratorMainActivity.this, UserSignInActivity.class));
                finish();
            }
        });

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
    }
    private void initData() {
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


