package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.twoMountains.R;
import com.example.twoMountains.adapter.CommonAdapter;
import com.example.twoMountains.adapter.ViewHolder;
import com.example.twoMountains.base.BaseActivity;
import com.example.twoMountains.bean.FMBean;
import com.example.twoMountains.db.DBCreator;

import java.util.ArrayList;
import java.util.List;

public class FmListActivity extends BaseActivity {
    private TextView txt_title;
    private RecyclerView fmRecycler;
    private List<FMBean> fmData = new ArrayList<>();
    private CommonAdapter<FMBean> fmBeanCommonAdapter = new CommonAdapter<FMBean>(R.layout.item_fm, fmData) {
        @Override
        public void bind(ViewHolder holder, FMBean fmBean, int position) {
            holder.setText(R.id.titleTv, fmBean.fmTitle);
            holder.setText(R.id.secTitleTv, fmBean.fmSecTitle);
            holder.setText(R.id.fmAnchorTv, fmBean.fmAuthor);
            holder.setText(R.id.uperTv, "Uploader：" + fmBean.up);
            ImageView faceView = holder.getView(R.id.faceIv);
            Glide.with(faceView).load(fmBean.faceFilePath).into(faceView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FmListActivity.this, MusicActivity.class)
                            .putExtra("fmBean", fmBean));
                }
            });
        }
    };
    private View emptyView;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        int type = getIntent().getIntExtra("type", -1);
        if (type == -1) {
            finish();
            toast("Parameter error");
            return;
        }
        switch (type){
            case 1:
                txt_title.setText("Emotion");
            case 2:
                txt_title.setText("Music");
            case 3:
                txt_title.setText("Science");
            case 4:
                txt_title.setText("Others");
        }
        List<FMBean> fmQueryData = DBCreator.getFMDao().queryByType(type);
        if (fmQueryData != null && !fmQueryData.isEmpty()) {
            fmData.addAll(fmQueryData);
        }
        fmRecycler.setAdapter(fmBeanCommonAdapter);
        if (fmData.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            fmRecycler.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            fmRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fm_list;
    }

    @Override
    protected void findViewsById() {
        txt_title = findViewById(R.id.title);
        fmRecycler = findViewById(R.id.fmRecycler);
        emptyView = findViewById(R.id.emptyView);
    }
}