package com.example.twoMountains.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.bean.QuitPlanBean;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.util.PreferenceUtil;
import com.example.twoMountains.util.SomeUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserSignInActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button btn_forgotPassword;
    private Button btn_signIn;
    private ImageButton wechatLoginBtn;
    private Button btn_signUp;
    private Button btn_administrator;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("WX_LOGIN_SUCCESS".equals(intent.getAction())) {
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        wechatLoginBtn = findViewById(R.id.wechatLoginBtn);
        accountEdit = findViewById(R.id.phoneEdit);
        passwordEdit = findViewById(R.id.pwdEdit);
        btn_forgotPassword = findViewById(R.id.btn_forgotPassword);
        btn_signIn = findViewById(R.id.loginBtn);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_administrator = findViewById(R.id.btn_administrator);
    }

    private void iniListener() {
        btn_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSignInActivity.this, ForgetPasswordActivity.class));
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        wechatLoginBtn.setOnClickListener(v -> {
            if (App.wxApi.isWXAppInstalled()) {
                sendWechatAuthRequest();
            } else {
                Toast.makeText(this, "Wechat not installed", Toast.LENGTH_SHORT).show();
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSignInActivity.this,SignUpActivity.class));
            }
        });
        btn_administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(UserSignInActivity.this,AdministratorSignInActivity.class));
            }
        });
    }

    private void initData() {
        // 注册广播接收器
        IntentFilter filter = new IntentFilter("WX_LOGIN_SUCCESS");
        registerReceiver(broadcastReceiver, filter);
    }

    private void login() {
        //获取用户输入的手机号和密码
        String account = accountEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (account.isEmpty()) {
            accountEdit.setError("Please enter your account");
            return;
        }
        //校验手机号是否合法
        if (!SomeUtil.isPhone(account)) {
            accountEdit.setError("Please enter the correct account");
            return;
        }

        //查询用户是否已经注册
        UserBean registeredUser = DBCreator.getUserDao().queryUserByAccount(account);
        if (registeredUser == null) {
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
            Toast.makeText(this, "The password must contain 8 to 16 characters of letters and numbers", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(registeredUser.password)) {
            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
            return;
        }else {
            //密码正确登录成功
            if (password.equals(registeredUser.password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                App.user = DBCreator.getUserDao().queryUserByAccount(account);
                PreferenceUtil.getInstance().save("logger", account);
                PreferenceUtil.getInstance().save("state",1);
                /*
                查询是否已有戒烟计划
                */
                if(DBCreator.getQuitPlanDao().queryQuitPlanByUser(App.user.id) == null){//还没有戒烟计划
                    QuitPlanBean quitPlanBean = new QuitPlanBean();
                    quitPlanBean.userId = App.user.id;
                    quitPlanBean.startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    DBCreator.getQuitPlanDao().addQuitPlan(quitPlanBean);
                }
                startActivity(new Intent(UserSignInActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Account or password error, please re-enter", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendWechatAuthRequest() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        App.wxApi.sendReq(req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播接收器
        unregisterReceiver(broadcastReceiver);
    }
}