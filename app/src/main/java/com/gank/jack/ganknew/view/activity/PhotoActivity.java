package com.gank.jack.ganknew.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.gank.jack.ganknew.R;
import com.gank.jack.ganknew.adapter.PhotoFragmentAdapter;
import com.gank.jack.ganknew.base.ToolbarBaseActivity;
import com.gank.jack.ganknew.bean.FemaleCurrent;
import com.gank.jack.ganknew.bean.Gank;
import com.gank.jack.ganknew.presenter.PhotoPersenter;
import com.gank.jack.ganknew.utils.ToastUtil;
import com.gank.jack.ganknew.utils.widget.PhotoViewPager;
import com.gank.jack.ganknew.view.fragment.PhotoFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author 谢汉杰
 */

public class PhotoActivity extends ToolbarBaseActivity
                implements ViewPager.OnPageChangeListener{

    @Bind(R.id.photo_viewpager)
    public PhotoViewPager photoViewpager;

    private PhotoFragmentAdapter photoFragmentAdapter;
    private PhotoPersenter photoPersenter;
    private List<Gank> listGank;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Color.parseColor("#000000"));
        supportPostponeEnterTransition();
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        photoToolbar=(Toolbar)findViewById(R.id.photo_toolbar);
        init();
        initView();
    }

    public void init(){
        setBaseSupportActionBar(photoToolbar);
        photoPersenter=new PhotoPersenter(this);
        listGank=(List<Gank>) getIntent().getSerializableExtra("listGank");
        position=getIntent().getIntExtra("position",0);
        photoViewpager.setOnPageChangeListener(this);
        initToolbarTitle();
    }

    public void initView(){
        photoFragmentAdapter=new PhotoFragmentAdapter(getSupportFragmentManager(),listGank,position,this);
        photoViewpager.setAdapter(photoFragmentAdapter);
        photoViewpager.setCurrentItem(position);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementsUseOverlay(false);
        }
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.clear();
                sharedElements.put(listGank.get(photoViewpager.getCurrentItem())._id,
                        ((PhotoFragment)photoFragmentAdapter.instantiateItem(
                        photoViewpager,photoViewpager.getCurrentItem())).getSharedElement());
            }
        });

    }

    @Override
    public void supportFinishAfterTransition() {
        Intent data = new Intent();
        data.putExtra("INDEX",photoViewpager.getCurrentItem());
        setResult(RESULT_OK,data);
        super.supportFinishAfterTransition();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageScrollStateChanged(int state) {}
    @Override
    public void onPageSelected(int position){
        photoToolbar.setTitle(listGank.get(position).desc);
        FemaleCurrent femaleCurrent=new FemaleCurrent();
        femaleCurrent.current=position;
        EventBus.getDefault().post(femaleCurrent);
        if(position==1||position==0){
            hideOrShowToolbar();
        }
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void imageTouch(PhotoViewAttacher attacher){
        hideOrShowToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photomenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_saveimg:
                ToastUtil.showToast(this,"save");
//                photoPersenter.saveImage();
                break;
            case R.id.action_shareimg:
                ToastUtil.showToast(this,"share");
//                photoPersenter.sharedImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initToolbarTitle(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                photoToolbar.setTitle(listGank.get(position).desc);
            }
        },300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

}
