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
import com.example.twoMountains.util.SomeUtil;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ImageView imBtn_back;
    private EditText accountEdit;
    private EditText emailEdit;

    private EditText newPasswordEdit;
    private Button btn_changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        // 初始化视图
        initView();
        // 初始化监听
        iniListener();
        // 初始化数据
        initData();
    }

    private void initView() {
        imBtn_back = findViewById(R.id.backBtn);
        accountEdit = findViewById(R.id.phoneEdit);
        newPasswordEdit = findViewById(R.id.newPwdEdit);
        emailEdit = findViewById(R.id.emailEdit);
        btn_changePassword = findViewById(R.id.btn_changePassword);
    }

    private void iniListener() {
        imBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*btn_sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                sendSms(account);
                startCountDown();
            }
        });*/
        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String newPassword = newPasswordEdit.getText().toString();

                UserBean userBean = DBCreator.getUserDao().queryUserByAccount(account);
                if (userBean == null) {
                    Toast.makeText(ForgetPasswordActivity.this, "Account or email error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userBean.email != null && userBean.email.equals(email)) {
                    if (newPassword.isEmpty()) {
                        newPasswordEdit.setError("Please enter the password");
                        return;
                    }
                    if (!SomeUtil.isTruePwd(newPassword)) {
                        newPasswordEdit.setError("Please enter the correct password format");
                        Toast.makeText(ForgetPasswordActivity.this,"The password must contain 8 to 16 characters of letters and numbers",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    userBean.password = newPassword;
                    DBCreator.getUserDao().updateUser(userBean);
                    Toast.makeText(ForgetPasswordActivity.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Account or email error", Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
            }
        });
    }

    private void initData() {
    }

    /*private void sendSms(String phoneNumber) {
        // 替换为您的实际应用ID和AppKey
        int appId = 1400914806;  // 替换为你的SDK AppID
        String appKey = "551c9bb785c75ef5db6493fc001a2ece";  // 替换为你的AppKey
        // 短信模板ID和签名内容应该在腾讯云短信控制台中进行配置
        int templateId = your_template_id;  // 替换为你的短信模板ID
        String smsSign = "your_sms_sign";  // 替换为您的短信签名

        try {
            SmsMultiSender msender = new SmsMultiSender(appId, appKey);
            ArrayList<String> params = new ArrayList<>();
            String code = generateVerificationCode();  // 使用随机生成的验证码
            params.add(code);
            SmsMultiSenderResult result = msender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
            if (result.result == 0) {
                // 发送成功
                Toast.makeText(ForgetPasswordActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
            } else {
                // 发送失败
                Toast.makeText(ForgetPasswordActivity.this, "发送失败：" + result.errMsg, Toast.LENGTH_SHORT).show();
            }
        } catch (HTTPException | JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    // 随机生成验证码
    public static String generateVerificationCode() {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }

    private void startCountDown() {
        btn_sendSms.setEnabled(false);  // 禁用发送按钮
        new CountDownTimer(30000, 1000) {  // 创建30秒倒计时

            public void onTick(long millisUntilFinished) {
                btn_sendSms.setText("重新发送 (" + millisUntilFinished / 1000 + "s)");  // 更新按钮文字
            }

            public void onFinish() {
                btn_sendSms.setText("发送验证码");
                btn_sendSms.setEnabled(true);  // 重新启用发送按钮
            }
        }.start();
    }*/
}