package com.example.ble.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ble.R;
import com.example.ble.base.BaseActivity;

public class ArticleActivity extends BaseActivity {
    private int articleNumber;
    private TextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        articleNumber=intent.getIntExtra("articleNumber",1);
        initData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        txt_title.setText("文章标题"+articleNumber);
        System.out.println(articleNumber);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void findViewsById() {
        txt_title = findViewById(R.id.titleTextView);
    }
}