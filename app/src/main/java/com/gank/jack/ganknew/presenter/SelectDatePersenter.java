package com.gank.jack.ganknew.presenter;

import android.content.Context;

import com.gank.jack.ganknew.bean.SelectDate;
import com.gank.jack.ganknew.interfaces.SelectDateInterface;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 谢汉杰
 */

public class SelectDatePersenter extends BasePresenter{

    public SelectDatePersenter(Context context) {
        super(context);
    }

    public void getSelectDate(final SelectDateInterface selectDateInterface){
        gankApi.getAllSelectDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SelectDate>() {
                    @Override
                    public void onCompleted(){}
                    @Override
                    public void onError(Throwable e) {
                        selectDateInterface.onError(e);
                    }
                    @Override
                    public void onNext(SelectDate selectDate){
                        selectDateInterface.getSelectDate(selectDate);
                    }
                });
    }


}
