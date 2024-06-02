package com.example.twoMountains.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.R;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView imBtn_back;
    private EditText accountEdit;
    private EditText oldPasswordEdit;
    private EditText newPasswordEdit;
    private Button btn_changePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        imBtn_back = findViewById(R.id.backBtn);
        accountEdit=findViewById(R.id.phoneEdit);
        oldPasswordEdit=findViewById(R.id.oldPwdEdit);
        newPasswordEdit=findViewById(R.id.newPwdEdit);
        btn_changePassword=findViewById(R.id.btn_changePassword);
    }

    private void iniListener() {
        imBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String oldPassword = oldPasswordEdit.getText().toString();
                String newPassword = newPasswordEdit.getText().toString();
                UserBean userBean = DBCreator.getUserDao().queryUserByAccount(account);
                if(DBCreator.getUserDao().queryUserByAccount(account) == null){
                    Toast.makeText(ChangePasswordActivity.this,"Account or password error",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userBean.password.equals(oldPassword)){
                    userBean.password=newPassword;
                    DBCreator.getUserDao().updateUser(userBean);
                    Toast.makeText(ChangePasswordActivity.this,"Password has been changed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChangePasswordActivity.this,"Account or password error",Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
            }
        });
    }

    private void initData() {

    }
}