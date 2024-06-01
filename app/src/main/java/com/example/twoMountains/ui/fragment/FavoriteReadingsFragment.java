package com.example.twoMountains.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.adapter.ArticleAdapter;
import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.ui.activity.ArticleActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteReadingsFragment extends Fragment implements ArticleAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<ArticleBean> articleBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_readings, container, false);

        // 初始化RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        articleBeans = DBCreator.getArticleStarDao().loadStarredArticlesByUser(App.user.id);

        // 初始化适配器
        articleAdapter = new ArticleAdapter(articleBeans);
        articleAdapter.setOnItemClickListener(this); // 设置点击事件监听器
        recyclerView.setAdapter(articleAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(ArticleBean article) {
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra("articleId",article.id);
        startActivity(intent);
    }

    public void reloadData() {
        onResume();
    }
}