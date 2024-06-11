package com.example.twoMountains.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.twoMountains.App;
import com.example.twoMountains.bean.QuitPlanBean;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.ui.activity.MainActivity;
import com.example.twoMountains.util.PreferenceUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI wxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wxApi = WXAPIFactory.createWXAPI(this, "wx68c3d9bac60154ed", false);
        wxApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {
        // Handle WeChat requests here
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            if (authResp.errCode == BaseResp.ErrCode.ERR_OK) {
                String code = authResp.code;
                // Get access token here
                getAccessToken(code);
            } else {
                // Handle failure
                runOnUiThread(() -> Toast.makeText(WXEntryActivity.this, "Login Failed", Toast.LENGTH_SHORT).show());
                finish();
            }
        }
    }

    private void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + "wx68c3d9bac60154ed" +
                "&secret=" + "68d0adb0a20a80be1ca8e1058af53429" +
                "&code=" + code +
                "&grant_type=authorization_code";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(WXEntryActivity.this, "Failed to get access token", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String accessToken = jsonObject.getString("access_token");
                        String openId = jsonObject.getString("openid");
                        getUserInfo(accessToken, openId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(WXEntryActivity.this, "Failed to get access token", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getUserInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("weixin","登录失败");
                runOnUiThread(() -> Toast.makeText(WXEntryActivity.this, "Failed to get user info", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String nickname = jsonObject.getString("nickname");
                        String headimgurl = jsonObject.getString("headimgurl");
                        //查询用户是否已经注册
                        if (DBCreator.getUserDao().queryUserByAccount(openId) == null) {//未注册
                            // 保存用户信息到数据库
                            UserBean registeredUser = new UserBean();
                            registeredUser.account = openId;
                            registeredUser.name = nickname;
                            registeredUser.iconPath = headimgurl;
                            DBCreator.getUserDao().registerUser(registeredUser);
                        }
                        runOnUiThread(() -> Toast.makeText(WXEntryActivity.this, "Login successful" + nickname, Toast.LENGTH_SHORT).show());
                        App.user = DBCreator.getUserDao().queryUserByAccount(openId);
                        Log.d("name",App.user.name);
                        PreferenceUtil.getInstance().save("logger", App.user.account);
                        PreferenceUtil.getInstance().save("state",1);
                        //查询是否已有戒烟计划
                        if(DBCreator.getQuitPlanDao().queryQuitPlanByUser(App.user.id) == null){//还没有戒烟计划
                            QuitPlanBean quitPlanBean = new QuitPlanBean();
                            quitPlanBean.userId = App.user.id;
                            quitPlanBean.startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                            DBCreator.getQuitPlanDao().addQuitPlan(quitPlanBean);
                        }
                        // 发送广播关闭UserSignInActivity和SignUpActivity
                        Intent intent = new Intent("WX_LOGIN_SUCCESS");
                        sendBroadcast(intent);
                        startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                        finish();
                        Log.d("weixin","登录成功");
                        runOnUiThread(() -> Toast.makeText(WXEntryActivity.this, "Welcome " + nickname, Toast.LENGTH_SHORT).show());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(WXEntryActivity.this, "Failed to get user info", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}