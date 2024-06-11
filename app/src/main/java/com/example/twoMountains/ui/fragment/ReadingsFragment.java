package com.example.twoMountains.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.twoMountains.R;
import com.example.twoMountains.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ReadingsFragment extends BaseFragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int currentPosition = 0;

    public static ReadingsFragment newInstance() {
        Bundle args = new Bundle();
        ReadingsFragment fragment = new ReadingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initListener() {



    }

    @Override
    protected void initData() {
        // 创建一个 FragmentPagerAdapter 实例
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FavoriteReadingsFragment(), getResources().getString(R.string.Favorite_Readings));
        adapter.addFragment(new AllReadingsFragment(), getResources().getString(R.string.All_Readings));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition=position;
                // 选中特定 Fragment 时的操作
                if (position == 0) {
                    // 重新加载 FavoriteReadingsFragment 的数据
                    ((FavoriteReadingsFragment) adapter.getItem(0)).reloadData();
                    Log.d("articleStar","重新加载fragment0了");
                } else if (position == 1) {
                    // 重新加载 AllReadingsFragment 的数据
                    ((AllReadingsFragment) adapter.getItem(1)).reloadData();
                    Log.d("articleStar","重新加载fragment1了");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 关联 ViewPager 和 TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_readings;
    }

    @Override
    protected void findViewsById(View view) {
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    public void reloadData() {
        // 在这里重新加载数据
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        viewPager.setCurrentItem(currentPosition);
        Log.d("articleStar","onResume执行了");
    }
}