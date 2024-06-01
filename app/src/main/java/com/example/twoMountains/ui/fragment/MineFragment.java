package com.example.twoMountains.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.twoMountains.App;
import com.example.twoMountains.R;
import com.example.twoMountains.base.BaseFragment;
import com.example.twoMountains.db.DBCreator;
import com.example.twoMountains.ui.activity.Ble_ConnectActivity;
import com.example.twoMountains.ui.activity.DeviceSettingsActivity;
import com.example.twoMountains.ui.activity.FMActivity;
import com.example.twoMountains.ui.activity.LineChartActivity;
import com.example.twoMountains.ui.activity.ProfileActivity;
import com.example.twoMountains.ui.activity.UserSignInActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class MineFragment extends BaseFragment {

    private TextView nameTv;
    private TextView phoneTv;
    private TextView logout;
    private CircleImageView headView;
    private ImageButton imBtn_profile;
    private TextView txt_profile;

    private LinearLayout graph;
    private LinearLayout share;
    private LinearLayout deviceSetting;
    private LinearLayout bluetooth;

    private LinearLayout listen;


    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        /*EventBus.getDefault().register(this);*/
        if (App.isLogin()) {
            nameTv.setText(App.user.name);
            phoneTv.setText(App.user.account);
            if(App.user.iconPath != null){
                Glide.with(this).load(App.user.iconPath).into(headView);
            }

        }
        nameTv.setText(App.user.name);
    }

    @Override
    protected void initListener() {
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null);

                new AlertDialog.Builder(getContext()).setView(editText).setPositiveButton("取消", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editText.getText().toString().trim();
                        if (name.isEmpty()) {
                            Toast.makeText(getContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        App.user.name = name;
                        DBCreator.getUserDao().updateUser(App.user);
                        nameTv.setText(name);
                        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
            }
        });
        imBtn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(),ProfileActivity.class));
            }
        });
        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(),ProfileActivity.class));
            }
        });
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), LineChartActivity.class));
            }
        });
        /*share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.putExtra("position",1);
                startActivity(intent);
            }
        });*/

        deviceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), DeviceSettingsActivity.class));
            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), Ble_ConnectActivity.class));
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), FMActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.logout();
                startActivity(new Intent(requireActivity(), UserSignInActivity.class));
                requireActivity().finish();
            }
        });
        /*nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null);

                new AlertDialog.Builder(getContext()).setView(editText).setPositiveButton("取消", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editText.getText().toString().trim();
                        if (name.isEmpty()) {
                            Toast.makeText(getContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        App.user.name = name;
                        DBCreator.getUserDao().updateUser(App.user);
                        nameTv.setText(name);
                        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
            }
        });*/
    }


    @Override
    protected void findViewsById(View view) {
        nameTv = view.findViewById(R.id.nameTv);
        phoneTv = view.findViewById(R.id.phoneTv);
        imBtn_profile = view.findViewById(R.id.imBtn_profile);
        txt_profile = view.findViewById(R.id.txt_profile);
        graph = view.findViewById(R.id.graph);
        /*share = view.findViewById(R.id.share);*/
        deviceSetting = view.findViewById(R.id.deviceSetting);
        headView = view.findViewById(R.id.headView);
        bluetooth = view.findViewById(R.id.bluetooth);
        listen = view.findViewById(R.id.layout_listen);

        logout = view.findViewById(R.id.logout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onResume() {
        super.onResume();
        //从数据库更新App.user
        App.user = DBCreator.getUserDao().queryUserById(App.user.id);
        initData();
    }
}
