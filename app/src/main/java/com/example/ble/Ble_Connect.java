package com.example.ble;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Ble_Connect extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetooth4Adapter;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 10;

    private Button button_cancel;
    private Button button_turnon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_connect);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
        TextView textView = findViewById(R.id.textView1);

        ImageButton button3 = findViewById(R.id.refresh);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        

        //判断是否有访问位置的权限，没有权限，直接申请位置权限
        isPermission();

        checkBle(getApplicationContext());



    }

    private void initView() {
        button_cancel = findViewById(R.id.button_cancel);
        button_turnon = findViewById(R.id.button_turnon);
        //侧边抽屉
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

    }

    private void iniListener() {

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_turnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断蓝牙是否打开
                if (!isEnable()) {
                    //如果蓝牙未开启，则申请打开蓝牙
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    if (ActivityCompat.checkSelfPermission(Ble_Connect.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                } else {
                    startActivity(new Intent(Ble_Connect.this, BleActivity.class));
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 处理菜单项点击事件
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(Ble_Connect.this, MainActivity.class));
                        break;
                    case R.id.navigation_item_share:
                        // 处理菜单项1的点击事件
                        startActivity(new Intent(Ble_Connect.this, ShareActivity.class));
                        break;
                    case R.id.navigation_item_graph:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(Ble_Connect.this, LineChartActivity.class));
                        break;
                    case R.id.navigation_item_calendar:
                        // 处理菜单项2的点击事件
                        startActivity(new Intent(Ble_Connect.this, CalendarActivity2.class));
                        break;
                    case R.id.navigation_item_devicesettings:
                        // 处理菜单项2的点击事件
                        break;
                    // 处理更多菜单项的点击事件
                }
                drawerLayout.closeDrawer(GravityCompat.START); // 点击菜单项后关闭侧边菜单栏
                return true;
            }
        });
    }
    private void initData() {
        /*
         * 侧面菜单栏
         * */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //动态获取位置权限
    @SuppressLint("NewApi")
    private void isPermission() {
        if ((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    //判断该设备是否支持蓝牙功能
    private Boolean isBluetoothSupport() {
        if (mBluetoothAdapter == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检测手机是否支持4.0蓝牙
     * @param context  上下文
     * @return true--支持4.0  false--不支持4.0
     */
    private boolean checkBle(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {  //API 18 Android 4.3
            bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            if(bluetoothManager == null){
                return false;
            }
            bluetooth4Adapter = bluetoothManager.getAdapter();  //BLUETOOTH权限
            if(bluetooth4Adapter == null){
                return false;
            }else{
                Log.d(TAG,"该设备支持蓝牙4.0");
                return true;
            }
        }else{
            return false;
        }
    }


    /**
     * 获取蓝牙状态
     */
    public boolean isEnable(){
        if(bluetooth4Adapter == null){
            return false;
        }
        return bluetooth4Adapter.isEnabled();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: enable bluetooth!!!!!!");
        }
    }

    /**
     * 打开蓝牙
     * @param isFast  true 直接打开蓝牙  false 提示用户打开
     */
    public void openBluetooth(Context context,boolean isFast){
        if(!isEnable()){
            if(isFast){
                Log.d(TAG,"直接打开手机蓝牙");
                bluetooth4Adapter.enable();  //BLUETOOTH_ADMIN权限
            }else{
                Log.d(TAG,"提示用户去打开手机蓝牙");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivity(enableBtIntent);
            }
        }else{
            Log.d(TAG,"手机蓝牙状态已开");
        }
    }



}