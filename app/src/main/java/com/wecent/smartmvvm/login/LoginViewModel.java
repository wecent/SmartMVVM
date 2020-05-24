package com.wecent.smartmvvm.login;

import android.app.Application;
import android.text.TextUtils;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wecent.common.base.BaseViewModel;
import com.wecent.common.binding.command.BindingCommand;
import com.wecent.common.network.exception.BaseException;
import com.wecent.common.network.observer.BaseObserver;
import com.wecent.common.network.response.ResponseTransformer;
import com.wecent.common.network.schedulers.SchedulersTransformer;
import com.wecent.common.utils.ActivityUtils;
import com.wecent.common.utils.LogUtils;
import com.wecent.common.utils.ToastUtils;
import com.wecent.smartmvvm.api.ApiManager;
import com.wecent.smartmvvm.hybrid.HybridActivity;
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
    public MutableLiveData<Boolean> isCheck = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> isLogin = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand<String> onUsernameChange = new BindingCommand<>(s -> {
        userName.setValue(s);
        if (!TextUtils.isEmpty(userName.getValue()) && !TextUtils.isEmpty(password.getValue()) && isCheck.getValue()) {
            isLogin.setValue(true);
        } else {
            isLogin.setValue(false);
        }
    });

    public BindingCommand<String> onPasswordChange = new BindingCommand<>(s -> {
        password.setValue(s);
        if (!TextUtils.isEmpty(userName.getValue()) && !TextUtils.isEmpty(password.getValue()) && isCheck.getValue()) {
            isLogin.setValue(true);
        } else {
            isLogin.setValue(false);
        }
    });

    public BindingCommand<Boolean> onCheckedChange = new BindingCommand<>(b -> {
        isCheck.setValue(b);
        if (!TextUtils.isEmpty(userName.getValue()) && !TextUtils.isEmpty(password.getValue()) && isCheck.getValue()) {
            isLogin.setValue(true);
        } else {
            isLogin.setValue(false);
        }
    });

    public BindingCommand onLoginClick = new BindingCommand(() -> login());

    public BindingCommand onRegisterClick = new BindingCommand(() -> HybridActivity.launch(ActivityUtils.getTopActivity(), "https://wwww.baidu.com"));

    public BindingCommand onForgetClick = new BindingCommand(() -> HybridActivity.launch(ActivityUtils.getTopActivity(), "https://wwww.baidu.com"));

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
                        LogUtils.d(e);
                    }

                });
    }
}
