package com.example.ble.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ble.Article;
import com.example.ble.R;
import com.example.ble.adapter.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteReadingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_readings, container, false);

        // 初始化RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 初始化文章数据
        initData();

        // 初始化适配器
        articleAdapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(articleAdapter);

        return rootView;
    }

    private void initData() {
        articleList = new ArrayList<>();
        // 添加示例文章数据
        for (int i = 1; i <= 20; i++) {
            Article article = new Article("文章标题 " + i, i);
            articleList.add(article);
        }
    }
}