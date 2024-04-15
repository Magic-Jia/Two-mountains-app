package com.example.ble.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ble.R;

public class SignInActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    private Button btn_signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        account = findViewById(R.id.phoneEdit);
        password = findViewById(R.id.pwdEdit);
        btn_signIn = findViewById(R.id.loginBtn);
    }

    private void iniListener() {
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getText().toString().equals("15210669565")&&password.getText().toString().equals("123456")){
                    startActivity(new Intent(SignInActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void initData() {

    }
}