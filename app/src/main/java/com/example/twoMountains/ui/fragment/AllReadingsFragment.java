package com.example.twoMountains.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;
import com.example.twoMountains.adapter.ArticleAdapter;
import com.example.twoMountains.base.BaseFragment;
import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.ui.activity.ArticleActivity;

import java.util.ArrayList;
import java.util.List;

public class AllReadingsFragment extends BaseFragment implements ArticleAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<ArticleBean> articleBeans = new ArrayList<>();

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 从数据库加载文章数据
        articleBeans.clear();
        articleBeans = DBCreator.getArticleDao().loadAll();

        // 初始化适配器
        articleAdapter = new ArticleAdapter(articleBeans);
        articleAdapter.setOnItemClickListener(this); // 设置点击事件监听器
        recyclerView.setAdapter(articleAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void findViewsById(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_readings;
    }

    @Override
    public void onItemClick(ArticleBean article) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("articleId", article.id);
        startActivity(intent);
    }

    public void reloadData() {
        onResume();
    }
}
