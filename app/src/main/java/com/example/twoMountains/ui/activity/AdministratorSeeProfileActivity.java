package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdministratorSeeProfileActivity extends AppCompatActivity {
    private CircleImageView headView;
    private UserBean userBean;
    private TextView name;
    private TextView age;
    private TextView email;
    private TextView phone;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_see_profile);
        Intent intent = getIntent();
        userBean = DBCreator.getUserDao().queryUserById(intent.getIntExtra("userId",App.user.id));
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        headView = findViewById(R.id.headView);
        name = findViewById(R.id.nameEdit);
        age = findViewById(R.id.ageEdit);
        email = findViewById(R.id.emailEdit);
        phone = findViewById(R.id.phoneEdit);
        address = findViewById(R.id.adressEdit);
    }

    private void initData() {
        if(userBean.iconPath != null){
            Glide.with(this).load(userBean.iconPath).into(headView);
        }
        if(userBean.name == null){
            name.setText("");
        }else {
            name.setText(userBean.name);
        }
        if(userBean.age == null){
            age.setText("");
        }else {
            age.setText(userBean.age.toString());
        }
        if(userBean.email == null){
            email.setText("");
        }else {
            email.setText(userBean.email);
        }
        if(userBean.phone == null){
            phone.setText("");
        }else {
            phone.setText(userBean.phone);
        }
        if(userBean.address == null){
            address.setText("");
        }else {
            address.setText(userBean.address);
        }
    }

    private void iniListener() {
    }

}