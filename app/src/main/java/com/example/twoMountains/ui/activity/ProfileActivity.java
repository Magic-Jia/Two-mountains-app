package com.example.twoMountains.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.bean.UserBean;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.util.SomeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView headView;
    private String iconPath = "";
    private UserBean userBean;
    private EditText name;
    private EditText age;
    private EditText email;
    private EditText phone;
    private EditText address;
    private Button btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        btn_update = findViewById(R.id.btn_update);
    }

    private void initData() {
        userBean = App.user;
        if(userBean.iconPath != null){
            Glide.with(this).load(App.user.iconPath).into(headView);
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
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iconPath != ""){
                    userBean.iconPath = iconPath;
                }
                userBean.name = name.getText().toString();
                if(isInteger(age.getText().toString()) && Integer.parseInt(age.getText().toString())>=0 && Integer.parseInt(age.getText().toString())<=200){
                    userBean.age = Integer.parseInt(age.getText().toString());
                }else {
                    Toast.makeText(ProfileActivity.this,"Age should be between 0 and 200",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(SomeUtil.isEmail(email.getText().toString())){
                    userBean.email = email.getText().toString();
                }else {
                    Toast.makeText(ProfileActivity.this,"Email format is incorrect",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(SomeUtil.isPhone(phone.getText().toString())){
                    userBean.phone = phone.getText().toString();
                }else {
                    Toast.makeText(ProfileActivity.this,"Phone format is incorrect",Toast.LENGTH_SHORT).show();
                    return;
                }
                userBean.address = address.getText().toString();
                DBCreator.getUserDao().updateUser(userBean);
                Toast.makeText(ProfileActivity.this,"Update successful",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getData();
                if (uri != null) {
                    try {
                        headView.setImageURI(uri);
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        byte[] array = new byte[inputStream.available()];
                        inputStream.read(array);
                        String fileName = System.currentTimeMillis() + "_img_head.jpg";
                        File file = new File(getExternalCacheDir(), fileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(array);
                        fos.flush();
                        fos.close();
                        inputStream.close();
                        iconPath = file.getPath();
                        Log.d("TAG", "onActivityResult: " + file.getPath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}