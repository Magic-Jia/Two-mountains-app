package com.example.twoMountains.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;
import com.example.twoMountains.adapter.CommonAdapter;
import com.example.twoMountains.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TermsOfServiceActivity extends AppCompatActivity {

    private class TermsOfService {
        public TermsOfService(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String title;
        public String content;
    }


    private RecyclerView recycler;
    private List<TermsOfService> termsOfServices = new ArrayList<>();
    private CommonAdapter<TermsOfService> adapter = new CommonAdapter<TermsOfService>(R.layout.item_terms_of_service, termsOfServices) {
        @Override
        public void bind(ViewHolder holder, TermsOfService termsOfService, int position) {
            holder.setText(R.id.txt_title, termsOfService.title);
            holder.setText(R.id.txt_content, termsOfService.content);
        }
    };

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
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
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
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.Terms), getResources().getString(R.string.Terms_Content)));
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.UseLicense), getResources().getString(R.string.UseLicense_Content)));
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.Disclaimer), getResources().getString(R.string.Disclaimer_Content)));
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.Limitations), getResources().getString(R.string.Limitations_Content)));
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.Accuracyofmaterials), getResources().getString(R.string.Accuracyofmaterials_Content)));
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.Links), getResources().getString(R.string.Links_Content)));
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.Modifications), getResources().getString(R.string.Modifications_Content)));
        termsOfServices.add(new TermsOfService(getResources().getString(R.string.GoverningLaw), getResources().getString(R.string.GoverningLaw_Content)));
        recycler.setAdapter(adapter);
    }
}