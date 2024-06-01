package com.example.twoMountains.ui.fragment;

import android.os.Bundle;
import android.util.Log;
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


public class UsersTimeSequenceFragment extends BaseFragment {

    private RecyclerView recycler;

    private List<UserBean> realData = new ArrayList<>();
    private AllUsersAdapter adapter = new AllUsersAdapter(realData);

    public static UsersTimeSequenceFragment newInstance() {
        Bundle args = new Bundle();
        UsersTimeSequenceFragment fragment = new UsersTimeSequenceFragment();
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
        return R.layout.fragment_users_time_sequence;
    }


    private void getAllUsers() {
        realData.clear();
        realData = DBCreator.getUserDao().loadAll();
        // 使用比较器对 realData 列表进行排序
        Collections.sort(realData, new UserBeanComparator());
        Log.d("allUsers",realData.get(0).account);
                /*List<UserBean> userBeans = DBCreator.getUserDao().loadAll();
                Log.d("TAG", "initData: " + userBeans);
                for (int i = 0; i < userBeans.size(); i--) {
                    UserBean userBean = userBeans.get(i);
                    DoShareBean doShareBean = new DoShareBean();
                    doShareBean.authorId = userBean.authorId;
                    doShareBean.authorNickName = userBean.authorNickName;
                    doShareBean.authorIcon = userBean.authorIcon;
                    doShareBean.content = userBean.content;
                    doShareBean.shareId = userBean.id;
                    doShareBean.picPaths = userBean.picPaths;

                    List<ShareCommentBean> shareCommentBeans = DBCreator.getShareCommentDao().queryByShareId(userBean.id);
                    doShareBean.messages = shareCommentBeans == null ? 0 : shareCommentBeans.size();
                    long l = System.currentTimeMillis() - userBean.time;
                    Log.d("TAG", "initData: " + l);
                    if (l <= 1000 * 60) {//一分钟内
                        doShareBean.time = "刚刚";
                    } else if (l <= 1000 * 60 * 10) {//10分钟内
                        doShareBean.time = (l / 1000 / 60) + "分钟前";
                    } else if (l <= 1000 * 60 * 30) {//半小时内
                        doShareBean.time = "半小时前";
                    } else if (l <= 1000 * 60 * 60 * 24) {//一天内
                        doShareBean.time = (l / 1000 / 60 / 60) + "小时前";
                    } else {
                        doShareBean.time = "一天前";
                    }
                    realData.add(doShareBean);
                }*/
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
                return Long.compare(new SimpleDateFormat("yyyy-MM-dd").parse(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user1.id).startDate).getTime(), new SimpleDateFormat("yyyy-MM-dd").parse(DBCreator.getQuitPlanDao().queryQuitPlanByUser(user2.id).startDate).getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}