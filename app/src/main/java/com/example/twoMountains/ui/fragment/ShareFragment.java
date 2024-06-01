package com.example.twoMountains.ui.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    public static ShareFragment newInstance() {
        Bundle args = new Bundle();
        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(args);
        return fragment;
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
                WeChatShare weChatShare = new WeChatShare(getContext());
                weChatShare.shareImage(BitmapFactory.decodeResource(getResources(),R.drawable.two_mountains2),"双峰戒烟");
                /*weChatShare.shareWebPage("http://www.baidu.com","百度","baidu", BitmapFactory.decodeResource(getResources(),R.drawable.two_mountains2));*/
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

        /*
         * 计算quitDate和smokeFreeDays
         * */
        calculateQuitDateAndSmokeFreeDays();

        /*
         * 显示推送文章
         * */
        for(int i=0;i<3;i++){
            articleBeans.add(DBCreator.getArticleDao().queryArticleById(i+1));
            if(i==0){
                btn_articleTitle1.setText(articleBeans.get(i).title);
            }
            if(i==1){
                btn_articleTitle2.setText(articleBeans.get(i).title);
            }
            if(i==2){
                btn_articleTitle3.setText(articleBeans.get(i).title);
            }
            if(DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id,articleBeans.get(i).id) != null){//该文章被用户收藏
                if(i==0){
                    imbtn_star1.setImageResource(R.drawable.star_yellow);
                }
                if(i==1){
                    imbtn_star2.setImageResource(R.drawable.star_yellow);
                }
                if(i==2){
                    imbtn_star3.setImageResource(R.drawable.star_yellow);
                }
            }else{
                if(i==0){
                    imbtn_star1.setImageResource(R.drawable.star_grey);
                }
                if(i==1){
                    imbtn_star2.setImageResource(R.drawable.star_grey);
                }
                if(i==2){
                    imbtn_star3.setImageResource(R.drawable.star_grey);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void findViewsById(View view) {
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
            tv_quitDate.setText("Has to be determined");
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