package com.example.twoMountains.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.twoMountains.R;
import com.example.twoMountains.adapter.CommonAdapter;
import com.example.twoMountains.adapter.ViewHolder;
import com.example.twoMountains.base.BaseActivity;
import com.example.twoMountains.bean.FMBean;
import com.example.twoMountains.bean.FMMenuItem;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.event.FMUpSuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FMActivity extends BaseActivity {
    private String fileParentPath;
    private List<FMMenuItem> items = new ArrayList<>();
    private RecyclerView menuRecycler;
    private RecyclerView fmRecycler;

    private TextView uploadTv;
    private static final int REQUEST_STORAGE_PERMISSION = 100;

    private CommonAdapter<FMMenuItem> menuItemCommonAdapter = new CommonAdapter<FMMenuItem>(R.layout.item_fm_menu, items) {
        @Override
        public void bind(ViewHolder holder, FMMenuItem fmMenuItem, int position) {
            holder.setText(R.id.actionTv, fmMenuItem.getName());
            ImageView view = holder.getView(R.id.iconView);
            Glide.with(view).load(fmMenuItem.getIcon()).into(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FMActivity.this, FmListActivity.class)
                            .putExtra("type", position+1));
                }
            });
        }
    };

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
                    startActivity(new Intent(FMActivity.this, MusicActivity.class)
                            .putExtra("fmBean", fmBean));
                }
            });
        }
    };
    private View emptyView;
    @Override
    protected void initListener() {
        /*uploadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FMActivity.this, FmUpActivity.class));
            }
        });*/
    }

    @Override
    protected void initData() {
        //请求存储权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // 已授权，执行文件读取操作
        }

        EventBus.getDefault().register(this);
        items.add(new FMMenuItem(getResources().getString(R.string.Emotion), R.drawable.ic_qxgl));
        items.add(new FMMenuItem(getResources().getString(R.string.Music), R.drawable.ic_rjgt));
        items.add(new FMMenuItem(getResources().getString(R.string.Science), R.drawable.ic_jz));
        items.add(new FMMenuItem(getResources().getString(R.string.Others), R.drawable.ic_kp));
        menuRecycler.setAdapter(menuItemCommonAdapter);
        List<FMBean> fmBeans = DBCreator.getFMDao().queryAll();
        fmData.addAll(fmBeans);
        fmRecycler.setAdapter(fmBeanCommonAdapter);
        if (fmData.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            fmRecycler.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.GONE);
            fmRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpSuccessEvent(FMUpSuccessEvent event) {
        fmData.clear();
        List<FMBean> fmBeans = DBCreator.getFMDao().queryAll();
        fmData.addAll(fmBeans);
        fmBeanCommonAdapter.notifyDataSetChanged();
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
        return R.layout.activity_fm;
    }

    @Override
    protected void findViewsById() {
        menuRecycler = findViewById(R.id.menuRecycler);
        fmRecycler = findViewById(R.id.fmRecycler);
        /*uploadTv = findViewById(R.id.uploadTv);*/
        emptyView = findViewById(R.id.emptyView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}