package com.wecent.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.trello.rxlifecycle3.LifecycleTransformer;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/4/12
 */
public interface IBaseView<DB extends ViewDataBinding, VM extends BaseViewModel> {

    /**
     * 获取页面的DataBinding
     *
     * @return DataBinding
     */
    DB getBinding(LayoutInflater inflater, ViewGroup container);

    /**
     * 获取页面的ViewModel
     *
     * @return ViewModel
     */
    VM getViewModel();

    /**
     * 获取页面的ViewModelId
     *
     * @return xml
     */
    int getViewModelId();

    /**
     * 绑定数据
     */
    void bindData();

    /**
     * 绑定事件
     */
    void bindEvent();

    /**
     * 绑定Lifecycle
     *
     * @return LifecycleTransformer
     */
    <T> LifecycleTransformer<T> bindLifecycle();

}
