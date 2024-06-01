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

import com.example.twoMountains.R;
import com.example.twoMountains.ui.fragment.ContactUsFragment;
import com.example.twoMountains.ui.fragment.FAQFragment;
import com.example.twoMountains.ui.fragment.HelpTopicsFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {
    private CommonTabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private ImageView btn_back;
    private int startPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        startPosition=intent.getIntExtra("position",1);
        //初始化视图
        initView();
        //初始化监听
        initListener();
        //初始化数据
        initData();
    }

    private void initView() {
        btn_back = findViewById(R.id.backBtn);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void initListener() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new MyEntry("HELP TOPICS"));
        tabEntities.add(new MyEntry("FAQ'S"));
        tabEntities.add(new MyEntry("CONTACT US"));
        fragments.add(HelpTopicsFragment.newInstance());
        fragments.add(FAQFragment.newInstance());
        fragments.add(ContactUsFragment.newInstance());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyViewPagerAdapter(tabLayout,fragments, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                if(position == 0){
                    fragments.get(0).onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setTabData(tabEntities);
        tabLayout.setCurrentTab(startPosition);
        viewPager.setCurrentItem(startPosition);
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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        initListener();
        // 更新数据和视图
    }
}