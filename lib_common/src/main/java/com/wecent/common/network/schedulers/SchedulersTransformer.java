package com.wecent.common.network.schedulers;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @desc: 通用的线程转换类
 * @author: wecent
 * @date: 2020/4/12
 */
public class SchedulersTransformer {

    public static <T> ObservableTransformer<T, T> observableSchedulers(String name) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io(name))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleTransformer<T, T> singleSchedulers(String name) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io(name))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T,T> flowableSchedulers(String name){
        return upstream -> upstream
                .subscribeOn(Schedulers.io(name))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
