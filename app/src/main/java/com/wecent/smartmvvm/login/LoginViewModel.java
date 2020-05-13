package com.wecent.smartmvvm.login;

import android.app.Application;
import android.text.TextUtils;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wecent.common.base.BaseViewModel;
import com.wecent.common.network.exception.BaseException;
import com.wecent.common.network.observer.BaseObserver;
import com.wecent.common.network.response.ResponseTransformer;
import com.wecent.common.network.schedulers.SchedulersTransformer;
import com.wecent.common.utils.ActivityUtils;
import com.wecent.common.utils.LogUtils;
import com.wecent.common.utils.ToastUtils;
import com.wecent.smartmvvm.api.ApiManager;
import com.wecent.smartmvvm.main.MainActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * @desc: LoginViewModel
 * @author: wecent
 * @date: 202/4/13
 */
public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<String> userName = new MutableLiveData<>("18310081820");
    public MutableLiveData<String> password = new MutableLiveData<>("123456");
    public MutableLiveData<Boolean> isLogin = new MutableLiveData<>(true);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login() {
        if (TextUtils.isEmpty(userName.getValue())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.getValue())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }

        ApiManager.getInstance()
                .login(userName.getValue(), password.getValue())
                .doOnSubscribe(disposable -> {
                    showLoadingDialog();
                })
                .compose(ResponseTransformer.convertResponse())
                .compose(SchedulersTransformer.observableSchedulers("LoginViewModel"))
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner())))
                .subscribe(new BaseObserver<LoginEntity>() {
                    @Override
                    public void onSuccess(LoginEntity entity) {
                        finish();
                        hideLoadingDialog();
                        ToastUtils.showShort("请求成功");
                        MainActivity.launch(ActivityUtils.getTopActivity());
                    }

                    @Override
                    public void onFailure(BaseException e) {
                        hideLoadingDialog();
                        ToastUtils.showShort(e.display);
                    }

                    @Override
                    public void onError(BaseException e) {
                        LogUtils.d(e);
                    }
                });
    }
}
