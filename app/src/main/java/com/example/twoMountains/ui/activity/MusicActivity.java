package com.example.twoMountains.ui.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.twoMountains.R;
import com.example.twoMountains.base.BaseActivity;
import com.example.twoMountains.bean.FMBean;
import com.example.twoMountains.service.MusicService;
import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.blurry.Blurry;

public class MusicActivity extends BaseActivity {
    private SeekBar progressBar;
    private ImageView icon;
    private ImageView playBtn;
    private ImageView blurView;
    private TextView currentProgress;
    private TextView fmTitleTv;
    private TextView fmAnchorTv;
    private TextView maxProgress;
    private MusicService.MusicBinder musicBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicService.MusicBinder) service;

            musicBinder.setPreparedListener(new MusicService.OnPreparedListener() {
                @Override
                public void onPrepared() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setMax(musicBinder.getDuration());
                            maxProgress.setText(dateFormat.format(new Date(musicBinder.getDuration())));
                            handler.sendEmptyMessage(0);
                        }
                    });
                }
            });

            musicBinder.setOnCompletionListener(new MusicService.OnCompletionListener() {
                @Override
                public void onCompletion() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            isPlaying = false;
                            playBtn.setImageResource(R.drawable.ic_play);
                        }
                    });
                }
            });
            musicBinder.setDataSource(fmBean.fmFilePath);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int current = musicBinder.getCurrentProgress();
            String format = dateFormat.format(new Date(current));
            currentProgress.setText(format);
            progressBar.setProgress(current);
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    private boolean isPlaying = true;

    @Override
    protected void initListener() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                if (musicBinder != null) {
                    musicBinder.play();
                }
                if (isPlaying) {
                    playBtn.setImageResource(R.drawable.ic_pause);
                } else {
                    playBtn.setImageResource(R.drawable.ic_play);
                }
            }
        });

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (musicBinder != null) {
                    musicBinder.seekToProgress(seekBar.getProgress());
                }
            }
        });
    }

    private FMBean fmBean;

    @Override
    protected void initData() {
        fmBean = (FMBean) getIntent().getSerializableExtra("fmBean");

        StatusBarUtil.setTranslucentForImageView(this, 0, findViewById(R.id.titleView));
        bindService(new Intent(this, MusicService.class), connection, BIND_AUTO_CREATE);

        Glide.with(this).asBitmap().load(fmBean.faceFilePath).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                icon.setImageBitmap(resource);
                Blurry.with(MusicActivity.this).radius(6).sampling(10).async().from(resource).into(blurView);
            }
        });

        fmTitleTv.setText(fmBean.fmTitle);
        fmAnchorTv.setText(getResources().getString(R.string.author) + fmBean.fmAuthor);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
    }

    @RequiresApi(api = 31)
    @Override
    protected void findViewsById() {
        blurView = findViewById(R.id.blurView);
        fmTitleTv = findViewById(R.id.fmTitleTv);
        playBtn = findViewById(R.id.playBtn);
        icon = findViewById(R.id.icon);
        progressBar = findViewById(R.id.progressBar);
        fmAnchorTv = findViewById(R.id.fmAnchorTv);
        currentProgress = findViewById(R.id.currentProgress);
        maxProgress = findViewById(R.id.maxProgress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (musicBinder != null) {
            musicBinder.pause();
        }
        unbindService(connection);
        connection = null;
        musicBinder = null;
        handler.removeMessages(0);
    }
}