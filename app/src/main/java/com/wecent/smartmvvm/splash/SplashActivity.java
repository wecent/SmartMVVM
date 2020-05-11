package com.wecent.smartmvvm.splash;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecent.common.base.BaseActivity;
import com.wecent.common.base.BaseViewModel;
import com.wecent.smartmvvm.R;
import com.wecent.smartmvvm.login.LoginActivity;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

/**
 * @desc:
 * @author: wecent
 * @date: 202/4/13
 */
public class SplashActivity extends BaseActivity {

    @Override
    public ViewDataBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    public BaseViewModel getViewModel() {
        return getActivityViewModelProvider(this).get(BaseViewModel.class);
    }

    @Override
    public int getViewModelId() {
        return BR.ViewModel;
    }

    @Override
    public void bindData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.launch(SplashActivity.this);
                finish();
            }
        }, 1000);
    }

    @Override
    public void bindEvent() {

    }

}
