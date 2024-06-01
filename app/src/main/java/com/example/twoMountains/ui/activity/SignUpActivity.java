package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.R;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.util.SomeUtil;

public class SignUpActivity extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button btn_signIn;
    private Button btn_signUp;
    private boolean isRegister = true;
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
    }

    private void initData() {

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
            user.password = password;
            DBCreator.getUserDao().registerUser(user);
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(SignUpActivity.this, UserSignInActivity.class));
        }
    }
}