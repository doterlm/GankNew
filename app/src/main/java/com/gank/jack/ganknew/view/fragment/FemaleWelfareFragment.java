package com.gank.jack.ganknew.view.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gank.jack.ganknew.R;
import com.gank.jack.ganknew.adapter.WelfareAdapter;
import com.gank.jack.ganknew.base.BaseActivity;
import com.gank.jack.ganknew.base.BaseFragment;
import com.gank.jack.ganknew.bean.Gank;
import com.gank.jack.ganknew.interfaces.OnClickLintener;
import com.gank.jack.ganknew.interfaces.RefreshInterface;
import com.gank.jack.ganknew.interfaces.WelfaceInterface;
import com.gank.jack.ganknew.presenter.WelfarePresenter;
import com.gank.jack.ganknew.view.activity.PhotoActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jack on 2016/10/31.
 */

public class FemaleWelfareFragment extends BaseFragment implements
        WelfaceInterface, RefreshInterface, SwipeRefreshLayout.OnRefreshListener, OnClickLintener {

    @Bind(R.id.welfare_toolbar)
    public Toolbar welfare_toolbar;
    @Bind(R.id.welfare_recyclerview)
    public RecyclerView welfareRecyclerview;
    @Bind(R.id.welfare_refresh)
    public SwipeRefreshLayout welfareRefresh;

    public WelfarePresenter welfarePresenter;
    public WelfareAdapter welfareAdapter;
    public List<Gank> listGank;
    public int page=1;
    private boolean isRefresh=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welfare,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    public void init(){
        listGank=new ArrayList<>();
        ((BaseActivity)getActivity()).setSupportActionBar(welfare_toolbar);
        welfare_toolbar.setTitle(getString(R.string.meizifuli));
        welfareRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        welfarePresenter=new WelfarePresenter(getActivity());
        welfareAdapter=new WelfareAdapter(getActivity(),this.listGank);
        welfareAdapter.setOnClickLintener(this);
        welfareRecyclerview.setAdapter(welfareAdapter);
        welfareAdapter.setRefreshInterface(this);
        welfareRefresh.setOnRefreshListener(this);

        welfarePresenter.getMeizi(this,page);
    }

    @Override
    public void getWelfaceData(List<Gank> listGank, boolean isLast) {
        page++;
        welfareAdapter.addListData(listGank);
        if(isRefresh){
            welfareAdapter.notifyDataSetChanged();
        }else {
            welfareAdapter.stopLoadMore();
        }
        welfareRefresh.setRefreshing(false);
    }

    @Override
    public void error(Throwable e) {
        showToast(getActivity().getString(R.string.net_error));
    }

    @Override
    public void loadMore() {
        welfareRefresh.setRefreshing(true);
        welfarePresenter.getMeizi(this,page);
    }

    @Override
    public void onRefresh() {
        isRefresh=true;
        listGank.clear();
        welfarePresenter.getMeizi(this,page=1);
    }

    @Override
    public void onClick(final View v, final int position) {
        Glide.with(this).load(listGank.get(position).url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Intent intent=new Intent(getActivity(), PhotoActivity.class);
                        intent.putExtra("listGank",(Serializable)listGank);
                        intent.putExtra("position",position);
                        ActivityOptionsCompat compat=ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                (ImageView)v,getString(R.string.transitionAnimator));
                        ActivityCompat.startActivity(getActivity(),intent,compat.toBundle());
                    }
                });
    }

}
