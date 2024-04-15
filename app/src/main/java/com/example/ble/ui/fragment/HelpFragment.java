package com.example.ble.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ble.R;
import com.example.ble.base.BaseFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends BaseFragment {
    private CommonTabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private ImageView btn_back;

    public static HelpFragment newInstance() {
        Bundle args = new Bundle();
        HelpFragment fragment = new HelpFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new MyEntry("HELP TOPICS"));
        tabEntities.add(new MyEntry("FAQ'S"));
        tabEntities.add(new MyEntry("CONTACT US"));
        fragments.add(HelpTopicsFragment.newInstance());
        fragments.add(HelpTopicsFragment.newInstance());
        fragments.add(HelpTopicsFragment.newInstance());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyViewPagerAdapter(tabLayout,fragments, getChildFragmentManager()));
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
        tabLayout.setCurrentTab(1);
        viewPager.setCurrentItem(1);
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

        /*btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    @Override
    protected void findViewsById(View view) {
        btn_back = view.findViewById(R.id.backBtn);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help;
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