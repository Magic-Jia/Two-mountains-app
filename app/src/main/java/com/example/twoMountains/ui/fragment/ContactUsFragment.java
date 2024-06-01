package com.example.twoMountains.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.twoMountains.R;
import com.example.twoMountains.base.BaseFragment;

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