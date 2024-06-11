package com.example.twoMountains.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.base.BaseFragment;
import com.example.twoMountains.bean.ArticleBean;
import com.example.twoMountains.bean.ArticleStarBean;
import com.example.twoMountains.bean.CigaretteDataBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.ui.activity.ArticleActivity;
import com.example.twoMountains.ui.activity.CalendarActivity2;
import com.example.twoMountains.ui.activity.LineChartActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private AutoCompleteTextView autoCompleteTextView;
    private String[] options = {"0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView textview_motivation;
    private EditText edittext_quantity_cigarettes;
    private Button btn_submit;
    private TextView tv_startDate;
    private TextView tv_quitDate;
    private LinearLayoutCompat LinearLayout_Smoke_Free_Days;
    private TextView tv_smokeFreeDays;
    private long smokeFreeDays = 0;

    private String motivation;
    private float quantity_cigarettes;
    private TextView tv_articleTitle;
    private TextView tv_articleContent;
    private ImageButton imbtn_star;

    private ArticleBean articleBean = new ArticleBean();

    private static final String PREFS_NAME = "ArticlePrefs";
    private static final String KEY_LAST_ARTICLE_ID = "lastArticleId";
    private static final String KEY_LAST_PUSH_DATE = "lastPushDate";
    private int currentArticleId = 1;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initListener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断输入数据是否为空
                if(textview_motivation.getText().toString().isEmpty() || edittext_quantity_cigarettes.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Data cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断输入数据格式是否正确
                if(!isInteger(edittext_quantity_cigarettes.getText().toString()) || !textview_motivation.getText().toString().substring(textview_motivation.getText().toString().length()-1).equals("%")){
                    Toast.makeText(getActivity(),"Incorrect data format",Toast.LENGTH_SHORT).show();
                    return;
                }
                motivation = textview_motivation.getText().toString();
                quantity_cigarettes = Float.parseFloat(edittext_quantity_cigarettes.getText().toString());

                startActivity(new Intent(getActivity(), LineChartActivity.class));//启动Activity
                //写入数据库
                // 创建SimpleDateFormat对象，指定日期格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // 获取当前日期
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                // 减去一天
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                // 使用SimpleDateFormat将Date对象转换为只包含年月日的字符串
                String yesterdayDate = sdf.format(calendar.getTime());

                //今天戒烟数据
                CigaretteDataBean todayCigaretteData = new CigaretteDataBean();
                todayCigaretteData.userId = App.user.id;
                todayCigaretteData.date = sdf.format(new Date());
                todayCigaretteData.motivation = Float.parseFloat(motivation.substring(0,motivation.length()-1));//去掉百分号
                if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(App.user.id, yesterdayDate)!=null){
                    //更新前一天
                    DBCreator.getCigaretteDataDao().updateCigaretteQuantityByUserAndDate((int)quantity_cigarettes,App.user.id,yesterdayDate);
                }else{//新增前一天
                    CigaretteDataBean yesterdayCigaretteData = new CigaretteDataBean();
                    yesterdayCigaretteData.userId = App.user.id;
                    yesterdayCigaretteData.date = yesterdayDate;
                    yesterdayCigaretteData.cigaretteQuantity = (int)quantity_cigarettes;
                    DBCreator.getCigaretteDataDao().addCigaretteData(yesterdayCigaretteData);
                }
                if(DBCreator.getCigaretteDataDao().queryCigaretteDataByUserAndDate(todayCigaretteData.userId, todayCigaretteData.date)!=null){
                    //更新今天
                    DBCreator.getCigaretteDataDao().updateMotivationByUserAndDate(todayCigaretteData.motivation,App.user.id,todayCigaretteData.date);
                    Log.d("Cigarette","更新今天数据");
                }else{//新增今天
                    DBCreator.getCigaretteDataDao().addCigaretteData(todayCigaretteData);
                }
                Toast.makeText(getActivity(),"Submitted successfully",Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout_Smoke_Free_Days.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalendarActivity2.class));
            }
        });

        tv_articleContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("articleId",articleBean.id);
                startActivity(intent);
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
    protected void initData() {
        /*
         * 戒烟动机输入框
         * */
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, options);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1); // 设置触发下拉提示的最小字符数

        tv_startDate.setText(DBCreator.getQuitPlanDao().queryQuitPlanByUser(App.user.id).startDate);

        /*
         * 计算quitDate和smokeFreeDays
         * */
        calculateQuitDateAndSmokeFreeDays();

        /*
         * 检查是否需要推送新文章
         * */
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lastPushDate = prefs.getString(KEY_LAST_PUSH_DATE, "");
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (!todayDate.equals(lastPushDate)) {
            // 获取新的文章编号
            currentArticleId = prefs.getInt(KEY_LAST_ARTICLE_ID, 0) + 1;
            // 尝试加载文章，如果没有文章则重置为第一篇
            articleBean = DBCreator.getArticleDao().queryArticleById(currentArticleId);
            if (articleBean == null) {
                currentArticleId = 1;
                articleBean = DBCreator.getArticleDao().queryArticleById(currentArticleId);
            }

            // 更新文章内容
            tv_articleTitle.setText(articleBean.title);
            tv_articleContent.setText(articleBean.content);

            if (DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id, articleBean.id) != null) {
                imbtn_star.setImageResource(R.drawable.star_yellow);
            } else {
                imbtn_star.setImageResource(R.drawable.star_grey);
            }

            // 更新SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_LAST_ARTICLE_ID, currentArticleId);
            editor.putString(KEY_LAST_PUSH_DATE, todayDate);
            editor.apply();
        } else {
            // 日期没有改变，加载最后推送的文章
            currentArticleId = prefs.getInt(KEY_LAST_ARTICLE_ID, 1);
            articleBean = DBCreator.getArticleDao().queryArticleById(currentArticleId);
            tv_articleTitle.setText(articleBean.title);
            tv_articleContent.setText(articleBean.content);

            if (DBCreator.getArticleStarDao().queryArticleStarByUserAndArticle(App.user.id, articleBean.id) != null) {
                imbtn_star.setImageResource(R.drawable.star_yellow);
            } else {
                imbtn_star.setImageResource(R.drawable.star_grey);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void findViewsById(View view) {
        textview_motivation = view.findViewById(R.id.motivation2);
        edittext_quantity_cigarettes = view.findViewById(R.id.quantity_cigarettes);
        btn_submit = view.findViewById(R.id.button_submit);
        tv_startDate = view.findViewById(R.id.tv_StartDate);
        tv_quitDate = view.findViewById(R.id.tv_QuitDate);
        LinearLayout_Smoke_Free_Days = view.findViewById(R.id.LinearLayout);
        tv_smokeFreeDays = view.findViewById(R.id.tv_smokeFreeDays);
        autoCompleteTextView = view.findViewById(R.id.motivation2);
        tv_articleTitle = view.findViewById(R.id.tv_articleTitle);
        tv_articleContent = view.findViewById(R.id.tv_articleContent);
        imbtn_star = view.findViewById(R.id.star);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
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
                }
            } else {
                if (bean.cigaretteQuantity == 0) {
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

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void reloadData() {
        // 在这里重新加载数据
        initData();
    }
}