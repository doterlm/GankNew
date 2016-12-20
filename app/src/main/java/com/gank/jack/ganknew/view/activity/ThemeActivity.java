package com.gank.jack.ganknew.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import com.gank.jack.ganknew.R;
import com.gank.jack.ganknew.adapter.ThemeAdapter;
import com.gank.jack.ganknew.base.BaseActivity;
import com.gank.jack.ganknew.bean.ThemeModel;
import com.gank.jack.ganknew.interfaces.OnClickLintener;
import com.gank.jack.ganknew.presenter.ThemePersenter;
import com.gank.jack.ganknew.theme.Theme;
import com.gank.jack.ganknew.utils.PreUtils;
import com.gank.jack.ganknew.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jack on 2016/12/18.
 */


public class ThemeActivity extends BaseActivity implements OnClickLintener {

    @Bind(R.id.about_toolbar)
    public Toolbar aboutToolbar;
    @Bind(R.id.theme_recyclerview)
    public RecyclerView themeRecyclerview;
    @Bind(R.id.theme_header)
    public RelativeLayout themeHeader;
    @Bind(R.id.theme_statusbar)
    public RelativeLayout themeStatusbar;
    @Bind(R.id.fab_theme_bg)
    public RelativeLayout fabThemeBg;

    public ThemeAdapter themeAdapter;
    private ThemePersenter themePersenter;
    private List<ThemeModel> listTheme=new ArrayList<>();

    private int[] themeColor=new int[]{R.color.colorBluePrimary,R.color.colorRedPrimary
        ,R.color.colorBrownPrimary,R.color.colorGreenPrimary,R.color.colorPurplePrimary
        ,R.color.colorTealPrimary,R.color.colorPinkPrimary,R.color.colorDeepPurplePrimary
        ,R.color.colorOrangePrimary,R.color.colorIndigoPrimary,R.color.colorCyanPrimary
        ,R.color.colorLightGreenPrimary,R.color.colorLimePrimary,R.color.colorDeepOrangePrimary
        ,R.color.colorBlueGreyPrimary};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);
        setBaseSupportActionBar(aboutToolbar);
        init();
        initView();
    }

    public void init(){
        themePersenter=new ThemePersenter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        themeRecyclerview.setLayoutManager(linearLayoutManager);
        themeAdapter=new ThemeAdapter(this,listTheme);
        themeAdapter.setOnClickLintener(this);
        themeRecyclerview.setAdapter(themeAdapter);
    }

    public void initView(){
        themePersenter.getListColor(themeColor,listTheme);
        themeAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.theme_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_sure:
                ToastUtil.showToast(this,"sdfasd");
                PreUtils.changeTheme(ThemeActivity.this,R.style.BlueTheme, Theme.Blue.toString());
                PreUtils.changeColorImpl(ThemeActivity.this,ThemeActivity.this.getTheme());
                finish();
//                collapsingToolbarLayout.setStatusBarScrimColor(Color.BLUE);
//                collapsingToolbarLayout.setContentScrimColor(Color.BLUE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v, int position) {
        themeHeader.setBackgroundColor(listTheme.get(position).color);
        themeStatusbar.setBackgroundColor(listTheme.get(position).color);
        fabThemeBg.setBackgroundColor(listTheme.get(position).color);
        themePersenter.selectListColor(position,listTheme);
        themeAdapter.notifyDataSetChanged();
    }


}
