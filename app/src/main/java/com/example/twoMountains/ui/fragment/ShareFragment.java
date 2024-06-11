package com.example.twoMountains.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.WeChatShare;
import com.example.twoMountains.base.BaseFragment;
import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.bean.ArticleStarBean;
import com.example.twoMountains.bean.CigaretteDataBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.ui.activity.ArticleActivity;
import com.example.twoMountains.ui.activity.CalendarActivity2;
import com.example.twoMountains.util.BooleanWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ShareFragment extends BaseFragment {
    private TextView tv_startDate;
    private TextView tv_quitDate;
    private LinearLayoutCompat LinearLayout_Smoke_Free_Days;
    private TextView tv_smokeFreeDays;
    private long smokeFreeDays = 0;
    private Button btn_share;
    private Button btn_articleTitle1;
    private Button btn_articleTitle2;
    private Button btn_articleTitle3;
    private ImageButton imbtn_star1;
    private ImageButton imbtn_star2;
    private ImageButton imbtn_star3;
    private List<ArticleBean> articleBeans = new ArrayList<>();

    private static final String PREFS_NAME = "ArticlePrefs";
    private static final String KEY_LAST_PUSH_DATE = "lastPushDate";
    private static final String KEY_LAST_ARTICLE_ID1 = "lastArticleId1";
    private static final String KEY_LAST_ARTICLE_ID2 = "lastArticleId2";
    private static final String KEY_LAST_ARTICLE_ID3 = "lastArticleId3";
    private int currentArticleId1 = 1;
    private int currentArticleId2 = 2;
    private int currentArticleId3 = 3;

    private FrameLayout rootView; // Variable to hold the root view

    public static ShareFragment newInstance() {
        Bundle args = new Bundle();
        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Bitmap getScreenBitmap(View view) {
        // Create a bitmap of the appropriate size
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // Bind a canvas to the bitmap
        Canvas canvas = new Canvas(bitmap);
        // Get the background of the view and draw it on the canvas
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            // If there is no background, fill the canvas with white
            canvas.drawColor(Color.WHITE);
        }
        // Draw the view on the canvas
        view.draw(canvas);
        return bitmap;
    }

    protected void initListener() {
        LinearLayout_Smoke_Free_Days.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalendarActivity2.class));
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a screenshot of the current view
                Bitmap bitmap = getScreenBitmap(rootView);
                if (bitmap != null) {
                    // Share the screenshot to WeChat
                    WeChatShare weChatShare = new WeChatShare(getContext());
                    weChatShare.shareImage(bitmap, "双峰戒烟");

                    // Optionally, handle the web page sharing as commented
                    // weChatShare.shareWebPage("http://www.baidu.com", "百度", "baidu", BitmapFactory.decodeResource(getResources(), R.drawable.icon));
                }
            }
        });
        btn_articleTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("articleId",articleBeans.get(0).id);
                startActivity(intent);
            }
        });
        btn_articleTitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("articleId",articleBeans.get(1).id);
                startActivity(intent);
            }
        });
        btn_articleTitle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("articleId",articleBeans.get(2).id);
                startActivity(intent);
            }
        });

        imbtn_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id,articleBeans.get(0).id) != null){//该文章被用户收藏
                    DBCreator.getArticleStarDao().deleteArticleStarByUserAndArticle(App.user.id,articleBeans.get(0).id);
                    imbtn_star1.setImageResource(R.drawable.star_grey);
                }else{//没被收藏
                    ArticleStarBean articleStarBean = new ArticleStarBean();
                    articleStarBean.userId = App.user.id;
                    articleStarBean.articleId = articleBeans.get(0).id;
                    DBCreator.getArticleStarDao().addArticleStar(articleStarBean);
                    imbtn_star1.setImageResource(R.drawable.star_yellow);
                }
                /*for(int i=0;i<articleBeans.size();i++){
                    if(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id,articleBeans.get(i).id) != null){//该文章被用户收藏
                        DBCreator.getArticleStarDao().deleteArticleStarByUserAndArticle(App.user.id,articleBeans.get(i).id);
                        if(i == 0){
                            imbtn_star1.setImageResource(R.drawable.star_grey);
                        }
                        if(i == 1){
                            imbtn_star2.setImageResource(R.drawable.star_grey);
                        }
                        if(i == 2){
                            imbtn_star3.setImageResource(R.drawable.star_grey);
                        }
                    }else{//没被收藏
                        ArticleStarBean articleStarBean = new ArticleStarBean();
                        articleStarBean.userId = App.user.id;
                        articleStarBean.articleId = articleBeans.get(i).id;
                        DBCreator.getArticleStarDao().addArticleStar(articleStarBean);
                        if(i == 0){
                            imbtn_star1.setImageResource(R.drawable.star_yellow);
                        }
                        if(i == 1){
                            imbtn_star2.setImageResource(R.drawable.star_yellow);
                        }
                        if(i == 2){
                            imbtn_star3.setImageResource(R.drawable.star_yellow);
                        }
                    }
                }*/
            }
        });
        imbtn_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id,articleBeans.get(1).id) != null){//该文章被用户收藏
                    DBCreator.getArticleStarDao().deleteArticleStarByUserAndArticle(App.user.id,articleBeans.get(1).id);
                    imbtn_star2.setImageResource(R.drawable.star_grey);
                }else{//没被收藏
                    ArticleStarBean articleStarBean = new ArticleStarBean();
                    articleStarBean.userId = App.user.id;
                    articleStarBean.articleId = articleBeans.get(1).id;
                    DBCreator.getArticleStarDao().addArticleStar(articleStarBean);
                    imbtn_star2.setImageResource(R.drawable.star_yellow);
                }

            }
        });
        imbtn_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id,articleBeans.get(2).id) != null){//该文章被用户收藏
                    DBCreator.getArticleStarDao().deleteArticleStarByUserAndArticle(App.user.id,articleBeans.get(2).id);
                    imbtn_star3.setImageResource(R.drawable.star_grey);
                }else{//没被收藏
                    ArticleStarBean articleStarBean = new ArticleStarBean();
                    articleStarBean.userId = App.user.id;
                    articleStarBean.articleId = articleBeans.get(2).id;
                    DBCreator.getArticleStarDao().addArticleStar(articleStarBean);
                    imbtn_star3.setImageResource(R.drawable.star_yellow);
                }
            }
        });
    }

    @Override
    protected void initData() {
        tv_startDate.setText(DBCreator.getQuitPlanDao().queryQuitPlanByUser(App.user.id).startDate);

        // 计算quitDate和smokeFreeDays
        calculateQuitDateAndSmokeFreeDays();

        // 获取当前日期
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lastPushDate = prefs.getString(KEY_LAST_PUSH_DATE, "");
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        List<ArticleBean> articles = new ArrayList<>();
        if (!todayDate.equals(lastPushDate)) {
            // 更新文章 ID
            currentArticleId1 = prefs.getInt(KEY_LAST_ARTICLE_ID1, 0) + 1;
            currentArticleId2 = currentArticleId1 + 1;
            currentArticleId3 = currentArticleId2 + 1;

            // 获取新文章，如果文章 ID 超过数据库中现有文章数量, 重置到开始
            articles.add(loadArticleWithFallback(currentArticleId1, 1));
            articles.add(loadArticleWithFallback(currentArticleId2, 2));
            articles.add(loadArticleWithFallback(currentArticleId3, 3));

            // 更新 SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_LAST_ARTICLE_ID1, currentArticleId1);
            editor.putInt(KEY_LAST_ARTICLE_ID2, currentArticleId2);
            editor.putInt(KEY_LAST_ARTICLE_ID3, currentArticleId3);
            editor.putString(KEY_LAST_PUSH_DATE, todayDate);
            editor.apply();
        } else {
            // 加载上次已存的文章
            currentArticleId1 = prefs.getInt(KEY_LAST_ARTICLE_ID1, 1);
            currentArticleId2 = prefs.getInt(KEY_LAST_ARTICLE_ID2, 2);
            currentArticleId3 = prefs.getInt(KEY_LAST_ARTICLE_ID3, 3);

            articles.add(DBCreator.getArticleDao().queryArticleById(currentArticleId1));
            articles.add(DBCreator.getArticleDao().queryArticleById(currentArticleId2));
            articles.add(DBCreator.getArticleDao().queryArticleById(currentArticleId3));
        }

        // 更新 UI 显示
        displayArticles(articles);

        // 更新星标状态
        updateAllStarStatuses();
    }

    private ArticleBean loadArticleWithFallback(int articleId, int fallbackId) {
        ArticleBean article = DBCreator.getArticleDao().queryArticleById(articleId);
        if (article == null) {
            article = DBCreator.getArticleDao().queryArticleById(fallbackId);
        }
        return article;
    }

    private void displayArticles(List<ArticleBean> articles) {
        articleBeans.clear();
        articleBeans.addAll(articles);

        btn_articleTitle1.setText(articles.get(0).title);
        btn_articleTitle2.setText(articles.get(1).title);
        btn_articleTitle3.setText(articles.get(2).title);
    }

    private void updateAllStarStatuses() {
        updateStarStatus(imbtn_star1, articleBeans.get(0).id);
        updateStarStatus(imbtn_star2, articleBeans.get(1).id);
        updateStarStatus(imbtn_star3, articleBeans.get(2).id);
    }

    private void updateStarStatus(ImageButton starButton, int articleId) {
        BooleanWrapper isFavoritedWrapper = new BooleanWrapper(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id, articleId) != null);
        starButton.setImageResource(isFavoritedWrapper.value ? R.drawable.star_yellow : R.drawable.star_grey);

        // 为星标按钮设置点击事件
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavoritedWrapper.value) {
                    DBCreator.getArticleStarDao().deleteArticleStarByUserAndArticle(App.user.id, articleId);
                    starButton.setImageResource(R.drawable.star_grey);
                } else {
                    ArticleStarBean articleStarBean = new ArticleStarBean();
                    articleStarBean.userId = App.user.id;
                    articleStarBean.articleId = articleId;
                    DBCreator.getArticleStarDao().addArticleStar(articleStarBean);
                    starButton.setImageResource(R.drawable.star_yellow);
                }
                isFavoritedWrapper.value = !isFavoritedWrapper.value; // 反转收藏状态
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void findViewsById(View view) {
        rootView = view.findViewById(R.id.framelayout);
        btn_share = view.findViewById(R.id.btn_share);
        tv_startDate = view.findViewById(R.id.tv_StartDate);
        tv_quitDate = view.findViewById(R.id.tv_QuitDate);
        LinearLayout_Smoke_Free_Days = view.findViewById(R.id.LinearLayout);
        tv_smokeFreeDays = view.findViewById(R.id.tv_smokeFreeDays);
        btn_articleTitle1 = view.findViewById(R.id.btn_article1);
        btn_articleTitle2 = view.findViewById(R.id.btn_article2);
        btn_articleTitle3 = view.findViewById(R.id.btn_article3);
        imbtn_star1 = view.findViewById(R.id.imBtn_star1);
        imbtn_star2 = view.findViewById(R.id.imBtn_star2);
        imbtn_star3 = view.findViewById(R.id.imBtn_star3);
    }

    /*
     * 计算quitDate和smokeFreeDays
     * */
    public void calculateQuitDateAndSmokeFreeDays(){
        List<CigaretteDataBean> cigaretteDataBeans = new ArrayList<>();
        cigaretteDataBeans = DBCreator.getCigaretteDataDao().loadAllByUser(App.user.id);
        // 对 cigaretteDataBeans 按日期进行排序
        Collections.sort(cigaretteDataBeans, new Comparator<CigaretteDataBean>() {
            @Override
            public int compare(CigaretteDataBean o1, CigaretteDataBean o2) {
                return o1.date.compareTo(o2.date);
            }
        });

        // 找到第一个满足条件的元素（元素及其后面的元素的 cigaretteQuantity 均为 0）
        CigaretteDataBean firstZeroCigarette = null;
        boolean foundZero = false;

        for (CigaretteDataBean bean : cigaretteDataBeans) {
            if (foundZero) {
                if (bean.cigaretteQuantity > 0) {
                    foundZero = false;
                    break;
                }
            } else {
                if (bean.cigaretteQuantity <= 0) {
                    firstZeroCigarette = bean;
                    foundZero = true;
                }
            }
        }

        if(foundZero){
            tv_quitDate.setText(firstZeroCigarette.date);
            DBCreator.getQuitPlanDao().updateQuitDateByUser(App.user.id,firstZeroCigarette.date);
            // 计算两个日期之间的差
            try {
                smokeFreeDays = ChronoUnit.DAYS.between(new SimpleDateFormat("yyyy-MM-dd").parse(firstZeroCigarette.date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                tv_smokeFreeDays.setText(Long.toString(smokeFreeDays));
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
        }else{
            tv_quitDate.setText(getResources().getString(R.string.Has_to_be_determined));
            tv_smokeFreeDays.setText("0");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
    public void reloadData() {
        // 在这里重新加载数据
        initData();
    }
}