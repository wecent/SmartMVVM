package com.wecent.common.base;

import android.app.Application;

import com.wecent.common.R;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * @desc: BaseViewModel
 * @author: wecent
 * @date: 2020/5/8
 */
public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {

    private BaseLiveData mBaseLiveData;
    /**
     * 弱引用持有避免造成内存泄漏
     */
    private WeakReference<LifecycleOwner> mLifecycleOwner;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleOwner(LifecycleOwner lifecycle) {
        this.mLifecycleOwner = new WeakReference<>(lifecycle);
    }

    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner.get();
    }

    public BaseLiveData getBaseLiveData() {
        if (mBaseLiveData == null) {
            mBaseLiveData = new BaseLiveData();
        }
        return mBaseLiveData;
    }

    public void showLoadingDialog() {
        showLoadingDialog(getApplication().getApplicationContext().getString(R.string.state_loading));
    }

    public void showLoadingDialog(String title) {
        mBaseLiveData.showDialogEvent.postValue(title);
    }

    public void hideLoadingDialog() {
        mBaseLiveData.hideDialogEvent.postValue(null);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        mBaseLiveData.finishEvent.postValue(null);
    }

    /**
     * 返回上一层
     */
    public void onBack() {
        mBaseLiveData.onBackEvent.postValue(null);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void injectRxBus() {
    }

    @Override
    public void removeRxBus() {
    }

}
