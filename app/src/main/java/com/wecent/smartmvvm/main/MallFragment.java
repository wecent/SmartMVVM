package com.wecent.smartmvvm.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecent.common.base.BaseFragment;
import com.wecent.common.base.BaseViewModel;
import com.wecent.smartmvvm.R;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

/**
 * @desc: Tab2Fragment
 * @author: wecent
 * @date: 2020/4/17
 */
public class MallFragment extends BaseFragment {

    @Override
    public ViewDataBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_find, container, false);
    }

    @Override
    public BaseViewModel getViewModel() {
        return getFragmentViewModelProvider(this).get(BaseViewModel.class);
    }

    @Override
    public int getViewModelId() {
        return BR.ViewModel;
    }

    @Override
    public void bindData() {

    }

    @Override
    public void bindEvent() {

    }

}
