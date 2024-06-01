package com.example.twoMountains.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;
import com.example.twoMountains.adapter.AllUsersAdapter;
import com.example.twoMountains.base.BaseFragment;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UsersRankingListFragment extends BaseFragment {

    private RecyclerView recycler;

    private List<UserBean> realData = new ArrayList<>();
    private AllUsersAdapter adapter = new AllUsersAdapter(realData);

    public static UsersRankingListFragment newInstance() {
        Bundle args = new Bundle();
        UsersRankingListFragment fragment = new UsersRankingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initData() {
        getAllUsers();
        adapter = new AllUsersAdapter(realData);
        recycler.setAdapter(adapter);
    }

    protected void initListener() {

    }

    protected void findViewsById(View view) {
        recycler = view.findViewById(R.id.recycler);
    }

    protected int getLayoutId() {
        return R.layout.fragment_users_ranking_list;
    }


    private void getAllUsers() {
        realData.clear();
        realData = DBCreator.getUserDao().loadAll();
        // 使用比较器对 realData 列表进行排序
        Collections.sort(realData, new UserBeanComparator());
    }
    public void onResume() {
        super.onResume();
        if (this.view != null) {
            findViewsById(this.view);
            initData();
        }
        // 更新数据和视图
    }

    // 在 UsersTimeSequenceFragment 类中添加一个比较器类
    private static class UserBeanComparator implements Comparator<UserBean> {
        @Override
        public int compare(UserBean user1, UserBean user2) {
            // 按照 quitDate 升序排序
            try {
                if(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user1.id).quitDate == null)
                    return 1;
                else if(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user2.id).quitDate == null)
                    return -1;
                return Long.compare(new SimpleDateFormat("yyyy-MM-dd").parse(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user1.id).quitDate).getTime(), new SimpleDateFormat("yyyy-MM-dd").parse(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user2.id).quitDate).getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}