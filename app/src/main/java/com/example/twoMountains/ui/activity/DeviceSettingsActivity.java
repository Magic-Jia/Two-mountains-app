package com.example.twoMountains.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.adapter.CustomSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DeviceSettingsActivity extends AppCompatActivity {
    private ImageView imBtn_back;
    private TextView deviceTime;
    private EditText deviceName;
    private Spinner onTimer;
    private Spinner offTimer;
    private EditText dailyUses;
    private Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_settings);
        //初始化视图
        initView();
        //初始化监听
        iniListener();
        //初始化数据
        initData();
    }

    private void initView() {
        imBtn_back = findViewById(R.id.backBtn);
        deviceTime = findViewById(R.id.deviceTime);
        deviceName = findViewById(R.id.deviceName);
        onTimer = findViewById(R.id.onTimer);
        offTimer = findViewById(R.id.offTimer);
        dailyUses = findViewById(R.id.dailyUses);
        btn_update = findViewById(R.id.btn_update);
    }

    private void initData() {
        /*
        每秒钟更新一次时间
        */
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                deviceTime.setText(currentTime);

                handler.postDelayed(this, 1000); // 1秒钟更新一次
            }
        });

        // 创建一个数组或列表来存储下拉列表中的数据
        List<String> OnTimerItems = Arrays.asList("3:00", "3:10", "3:20", "3:30", "3:40", "3:50", "4:00", "4:10", "4:20", "4:30", "4:40", "4:50", "5:00", "5:10", "5:20", "5:30", "5:40", "5:50", "6:00");

        // 创建一个CustomSpinnerAdapter来绑定数组
        CustomSpinnerAdapter OnTimerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, OnTimerItems);
        OnTimerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 设置适配器到Spinner
        onTimer.setAdapter(OnTimerAdapter);

        List<String> OffTimerItems = Arrays.asList("10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100", "105", "110", "115", "120", "125", "130", "135", "140", "145", "150", "155", "160", "165", "170", "175", "180");
        CustomSpinnerAdapter OffTimerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, OffTimerItems);
        OffTimerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offTimer.setAdapter(OffTimerAdapter);
    }

    private void iniListener() {
        imBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceName == null) {
                    Toast.makeText(DeviceSettingsActivity.this, "Device Name is empty!", Toast.LENGTH_SHORT).show();
                } else if (!isInteger(dailyUses.getText().toString())
                        || !(Integer.parseInt(dailyUses.getText().toString()) >= 1 && Integer.parseInt(dailyUses.getText().toString()) <= 99)) {
                    Toast.makeText(DeviceSettingsActivity.this, "Daily uses should be between 1 and 99", Toast.LENGTH_SHORT).show();
                } else {
                    if (App.bleConnectState) {
                        // 发送时间
                        sendTimeToBLEDevice(deviceTime.getText().toString());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // 发送设备名称
                        sendDeviceNameToBLEDevice(deviceName.getText().toString());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // 发送onTimer
                        Log.d("deviceOnTimer",onTimer.getSelectedItem().toString());
                        sendOnTimerToBLEDevice(onTimer.getSelectedItem().toString());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // 发送offTimer
                        Log.d("deviceOffTimer",offTimer.getSelectedItem().toString());
                        sendOffTimerToBLEDevice(offTimer.getSelectedItem().toString());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // 发送每日使用次数
                        sendDailyUsesToBLEDevice(dailyUses.getText().toString());
                    } else {
                        Toast.makeText(DeviceSettingsActivity.this, "Please connect to the current device first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendTimeToBLEDevice(String deviceTime) {
        StringBuilder sendMsg = new StringBuilder();
        sendMsg.append("A008");  //命令前缀
        sendMsg.append(formatTimeForBLE(deviceTime));
        Log.d("deviceTime",sendMsg.toString());

        if (App.bleManager != null) {
            App.bleManager.sendMessage(sendMsg.toString());
        }
    }

    private String formatTimeForBLE(String time) {
        time = time.replace("-", "").replace(":", "").replace(" ", "");

        String year = time.substring(0,4);
        String month = time.substring(4,6);
        String day = time.substring(6,8);
        String hour = time.substring(8,10);
        String minute = time.substring(10,12);
        String second = "00";
        Log.d("formatTimeForBLE", "year: " + year + ", month: " + month + ", day: " + day + ", hour: " + hour + ", minute: " + minute);
        String weekDay = getWeekDay(year,month,day);

        String hexYear = convertYearToHex(Integer.parseInt(year));
        return hexYear+String.format("%02X%02X%02X%02X%02X",Integer.parseInt(month),Integer.parseInt(day),Integer.parseInt(hour),Integer.parseInt(minute),Integer.parseInt(second))+weekDay;
    }

    private String getWeekDay(String year, String month, String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day)); // 月份从0开始
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 将星期几数字转为十六进制表示
        return String.format("%02X", dayOfWeek - 1); // 星期天是1，减1以便星期天是0
    }

    public static String convertYearToHex(int year) {
        if (year < 0 || year > 65535) {
            throw new IllegalArgumentException("Year must be between 0 and 65535");
        }

        // 将年份转换为16位无符号整数
        int lowByte = year & 0xFF;          // 取低字节
        int highByte = (year >> 8) & 0xFF;  // 取高字节

        // 将低字节和高字节按低字节在前高字节在后的顺序拼接成16进制字符串
        return String.format("%02X%02X", lowByte, highByte);
    }

    private void sendDeviceNameToBLEDevice(String deviceName) {
        StringBuilder sendMsg = new StringBuilder();
        sendMsg.append("A1");// 命令前缀
        sendMsg.append(String.format("%02X",convertStringToAscii(deviceName).length()/2));
        sendMsg.append(convertStringToAscii(deviceName));
        Log.d("deviceName",sendMsg.toString());

        if (App.bleManager != null) {
            App.bleManager.sendMessage(sendMsg.toString());
        }
    }

    public String convertStringToAscii(String input) {
        // 获取字符串的字节数组
        byte[] bytes = input.getBytes();

        // 创建一个 StringBuilder 来构建 ASCII 码字符串
        StringBuilder asciiString = new StringBuilder();

        // 逐字节处理
        for (byte b : bytes) {
            // 将字节转换为 ASCII 码，并添加到 StringBuilder 中
            asciiString.append(String.format("%02X", b));
        }

        // 返回最终的 ASCII 码字符串
        return asciiString.toString();
    }

    private void sendOnTimerToBLEDevice(String onTimer) {
        StringBuilder sendMsg = new StringBuilder();
        sendMsg.append("A304");// 命令前缀
        onTimer = onTimer.replace(":", "");
        int time = Integer.parseInt(onTimer.substring(0,1))*60*1000 + Integer.parseInt(onTimer.substring(1,3))*1000;
        sendMsg.append(String.format("%08X",time));// 将时间格式转换为所需格式
        Log.d("deviceOnTimer",sendMsg.toString());

        if (App.bleManager != null) {
            App.bleManager.sendMessage(sendMsg.toString());
        }
    }

    private void sendOffTimerToBLEDevice(String offTimer) {
        StringBuilder sendMsg = new StringBuilder();
        sendMsg.append("A404");  // 命令前缀
        int time = Integer.parseInt(offTimer)*60*1000;
        sendMsg.append(String.format("%08X",time));// 将时间格式转换为所需格式
        Log.d("deviceOffTimer",sendMsg.toString());

        if (App.bleManager != null) {
            App.bleManager.sendMessage(sendMsg.toString());
        }
    }

    private void sendDailyUsesToBLEDevice(String dailyUses) {
        StringBuilder sendMsg = new StringBuilder();
        sendMsg.append("A201");  // 命令前缀
        sendMsg.append(String.format("%02X",Integer.parseInt(dailyUses)));

        if (App.bleManager != null) {
            App.bleManager.sendMessage(sendMsg.toString());
        }
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}