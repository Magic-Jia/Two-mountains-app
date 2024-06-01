package com.example.twoMountains.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.R;
import com.example.twoMountains.util.PreferenceUtil;

import java.util.Locale;

public class StartAppActivity extends AppCompatActivity {
    private Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);
        boolean firstIn = PreferenceUtil.getInstance().get("flag",true);
        Log.d("TAG", "onCreate: "+firstIn);
        if (!firstIn){
            if(!PreferenceUtil.getInstance().get("logger", "").isEmpty()){
                if(PreferenceUtil.getInstance().get("state",1) == 2){
                    startActivity(new Intent(StartAppActivity.this, AdministratorMainActivity.class));
                }else {
                    startActivity(new Intent(StartAppActivity.this, MainActivity.class));
                }
            }else {
                startActivity(new Intent(StartAppActivity.this, UserSignInActivity.class));
            }
            finish();
            return;
        }
        PreferenceUtil.getInstance().save("flag", false);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
        showLanguageSelectionDialog();
    }

    private void initView() {
        btn_start=findViewById(R.id.btn_startSetup);
    }

    private void iniListener() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartAppActivity.this,TermsOfServiceActivity.class));
            }
        });
    }

    private void initData() {
    }

    private void showLanguageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.language_selection)
                .setItems(new String[]{getString(R.string.english), getString(R.string.chinese)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                setLocale("en");
                                break;
                            case 1:
                                setLocale("zh");
                                break;
                        }
                        recreate(); // 重新创建Activity以应用新的语言环境
                    }
                });
        builder.create().show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}