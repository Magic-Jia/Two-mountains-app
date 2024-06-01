package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.bean.ArticleStarBean;
import com.example.twoMountains.db.DBCreator;

public class ArticleActivity extends AppCompatActivity {
    private ImageView imBtn_back;
    private TextView txt_title;
    private TextView txt_content;
    private TextView txt_viewCount;
    private ArticleBean articleBean = new ArticleBean();
    private ImageButton imbtn_star;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        imBtn_back = findViewById(R.id.backBtn);
        txt_title = findViewById(R.id.titleTextView);
        txt_content = findViewById(R.id.articleTextView);
        txt_viewCount = findViewById(R.id.viewCountTextView);
        imbtn_star = findViewById(R.id.star);
    }

    private void iniListener() {
        imBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imbtn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id,articleBean.id) != null){//该文章被用户收藏
                    DBCreator.getArticleStarDao().deleteArticleStarByUserAndArticle(App.user.id,articleBean.id);
                    imbtn_star.setImageResource(R.drawable.star_grey);
                }else{//没被收藏
                    ArticleStarBean articleStarBean = new ArticleStarBean();
                    articleStarBean.userId = App.user.id;
                    articleStarBean.articleId = articleBean.id;
                    DBCreator.getArticleStarDao().addArticleStar(articleStarBean);
                    imbtn_star.setImageResource(R.drawable.star_yellow);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在这里执行销毁操作，比如释放资源、停止动画等
        // 清空数据，以免泄漏
        articleBean = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void initListener() {

    }

    protected void initData() {

        Intent intent = getIntent();
        articleBean = DBCreator.getArticleDao().queryArticleById(intent.getIntExtra("articleId",1));
        txt_title.setText(articleBean.title);
        txt_content.setText(articleBean.content);
        txt_viewCount.setText(Integer.toString(articleBean.watchQuantity+1));

        if(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id,articleBean.id) != null){//该文章被用户收藏
            imbtn_star.setImageResource(R.drawable.star_yellow);
        }

        //观看数加一
        DBCreator.getArticleDao().updateWatchQuantityById(articleBean.id,articleBean.watchQuantity+1);
        Log.d("articleWatchQuantity",Integer.toString(articleBean.watchQuantity+1));
    }
}