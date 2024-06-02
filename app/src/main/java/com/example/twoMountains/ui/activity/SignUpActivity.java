package com.example.twoMountains.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.util.SomeUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button btn_signIn;
    private Button btn_signUp;
    private ImageButton wechatLoginBtn;
    private boolean isRegister = true;
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
        setContentView(R.layout.activity_sign_up);
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
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_signUp = findViewById(R.id.btn_signUp);
    }

    private void iniListener() {
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUpActivity.this, UserSignInActivity.class));
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        wechatLoginBtn.setOnClickListener(v -> {
            if (App.wxApi.isWXAppInstalled()) {
                sendWechatAuthRequest();
            } else {
                Toast.makeText(this, "Wechat not installed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        // 注册广播接收器
        IntentFilter filter = new IntentFilter("WX_LOGIN_SUCCESS");
        registerReceiver(broadcastReceiver, filter);
    }

    private void register() {
        String account = accountEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (account.isEmpty()) {
            accountEdit.setError("Please enter your account");
            return;
        }
        if (!SomeUtil.isPhone(account)) {
            accountEdit.setError("The account format is incorrect");
            return;
        }
        UserBean registeredUser = null;
        if (isRegister) {
            registeredUser = DBCreator.getUserDao().queryUserByAccount(account);
        }
        Log.d("TAG", "register: " + (registeredUser == null));
        if (registeredUser != null) {
            Toast.makeText(this, "The account has been registered", Toast.LENGTH_SHORT).show();
        } else {
            if (password.isEmpty()) {
                passwordEdit.setError("Please enter the password");
                return;
            }
            if (!SomeUtil.isTruePwd(password)) {
                passwordEdit.setError("Please enter the correct password format");
                Toast.makeText(this, "The password must contain 8 to 16 characters of letters and numbers", Toast.LENGTH_SHORT).show();
                return;
            }
            UserBean user = new UserBean();
            user.account = account;
            user.phone = account;
            user.password = password;
            DBCreator.getUserDao().registerUser(user);
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(SignUpActivity.this, UserSignInActivity.class));
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