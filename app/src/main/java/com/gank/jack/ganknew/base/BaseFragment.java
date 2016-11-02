package com.gank.jack.ganknew.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.jack.ganknew.utils.ToastUtil;

/**
 * Created by Jack on 2016/11/3.
 */

public class BaseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showToast(String msg){
        ToastUtil.showToast(getActivity(),msg);
    }

    public void startNewActiviy(Class<?> mClass){
        Intent intent=new Intent();
        intent.setClass(getActivity(),mClass);
        startActivity(intent);
    }

    public void startNewActivityByIntent(Intent intent){
        startActivity(intent);
    }





}
