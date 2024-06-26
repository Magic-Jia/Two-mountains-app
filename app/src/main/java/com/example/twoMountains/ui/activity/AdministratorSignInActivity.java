package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.bean.AdministratorBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.util.PreferenceUtil;
import com.example.twoMountains.util.SomeUtil;

public class AdministratorSignInActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_sign_in);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        accountEdit = findViewById(R.id.phoneEdit);
        passwordEdit = findViewById(R.id.pwdEdit);
        btn_signIn = findViewById(R.id.loginBtn);
    }

    private void iniListener() {
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void initData() {

    }

    private void login() {
        //获取用户输入的手机号和密码
        String account = accountEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        //校验手机号是否合法
        if (account.isEmpty()) {
            accountEdit.setError("Please enter your account");
            return;
        }
        if (!SomeUtil.isPhone(account)) {
            accountEdit.setError("Please enter the correct account");
            return;
        }

        //查询用户是否已经注册
        AdministratorBean registeredAdministrator = DBCreator.getAdministratorDao().queryAdministratorByAccount(account);
        if (registeredAdministrator == null) {
            Toast.makeText(this, "This account is not registered, please register first", Toast.LENGTH_SHORT).show();
            return;
        }

        //校验密码是否正确
        if (password.isEmpty()) {
            passwordEdit.setError("Please enter the password");
            return;
        }
        if (!SomeUtil.isTruePwd(password)) {
            passwordEdit.setError("Please enter the correct password format");
            Toast.makeText(this, "The password format must be a 6-digit number in length", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(registeredAdministrator.password)) {
            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
            return;
        }else {
            //密码正确登录成功
            if (password.equals(registeredAdministrator.password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                App.user = DBCreator.getAdministratorDao().queryAdministratorByAccount(account);
                PreferenceUtil.getInstance().save("logger", account);
                PreferenceUtil.getInstance().save("state",2);
                /*
                查询是否已有戒烟计划
                *//*
                if(DBCreator.getQuitPlanDao().queryQuitPlanByUser(App.user.id) == null){//还没有戒烟计划
                    QuitPlanBean quitPlanBean = new QuitPlanBean();
                    quitPlanBean.userId = App.user.id;
                    quitPlanBean.startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    DBCreator.getQuitPlanDao().addQuitPlan(quitPlanBean);
                }*/
                startActivity(new Intent(AdministratorSignInActivity.this, AdministratorMainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Account or password error, please re-enter", Toast.LENGTH_SHORT).show();
            }
        }
    }
}