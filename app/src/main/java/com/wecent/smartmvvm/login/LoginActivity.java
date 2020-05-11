package com.wecent.smartmvvm.login;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecent.common.base.BaseActivity;
import com.wecent.common.utils.StatusBarUtils;
import com.wecent.smartmvvm.BR;
import com.wecent.smartmvvm.R;
import com.wecent.smartmvvm.databinding.ActivityLoginBinding;

import androidx.databinding.DataBindingUtil;

/**
 * @desc: 登录界面
 * @author: wecent
 * @date: 202/4/13
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    public static void launch(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public ActivityLoginBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    public LoginViewModel getViewModel() {
        return getActivityViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public int getViewModelId() {
        return BR.ViewModel;
    }

    @Override
    public void bindData() {
        StatusBarUtils.setStatusBarColor(this, getResources().getColor(R.color.config_color_white));
        StatusBarUtils.setStatusBarLightMode(this, true);
    }

    @Override
    public void bindEvent() {
        mBinding.cbProtocol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mViewModel.isLogin.setValue(isChecked);
        });
    }

}
