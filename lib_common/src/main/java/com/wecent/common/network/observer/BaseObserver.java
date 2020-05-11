package com.wecent.common.network.observer;

import com.wecent.common.network.exception.BaseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/4/12
 */
public abstract class BaseObserver<T> implements Observer<T> {

    /**
     * 请求成功
     *
     * @param t 泛型
     */
    public abstract void onSuccess(T t);

    /**
     * 请求失败
     *
     * @param e 异常
     */
    public abstract void onFailure(BaseException e);

    /**
     * 请求失败
     *
     * @param e 异常
     */
    public abstract void onError(BaseException e);

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if(((BaseException) e).isHttpException()) {
            // 处理网络异常
            onFailure((BaseException) e);
        } else {
            // 处理code异常
            onError((BaseException) e);
        }
    }

    @Override
    public void onComplete() {

    }
}
