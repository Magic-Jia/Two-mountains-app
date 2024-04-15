package com.example.ble.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.ble.R;
import com.example.ble.base.BaseFragment;

public class ContactUsFragment extends BaseFragment {

    public ContactUsFragment() {
    }

    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void findViewsById(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_us;
    }
}