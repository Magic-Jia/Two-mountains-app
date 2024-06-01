package com.example.twoMountains.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.R;

public class TermsOfServiceActivity extends AppCompatActivity {

    private Button btn_cancel;
    private Button btn_agree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_agree=findViewById(R.id.btn_agree);
    }

    private void iniListener() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog1 = new AlertDialog.Builder(TermsOfServiceActivity.this)
                        .setMessage("Are you sure you want to exit?")//内容
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(TermsOfServiceActivity.this,StartAppActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alertDialog1.show();
            }
        });
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TermsOfServiceActivity.this,SignUpActivity.class));
            }
        });
    }

    private void initData() {
    }
}