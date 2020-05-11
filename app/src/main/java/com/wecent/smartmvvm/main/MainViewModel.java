package com.wecent.smartmvvm.main;

import android.app.Application;

import com.wecent.common.base.BaseViewModel;
import com.wecent.common.bus.RxBus;
import com.wecent.common.bus.RxSubscriptions;
import com.wecent.common.bus.event.BaseEvent;
import com.wecent.common.bus.event.EventType;
import com.wecent.common.utils.ActivityUtils;
import com.wecent.smartmvvm.login.LoginActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.Disposable;

/**
 * @desc:
 * @author: wecent
 * @date: 202/4/13
 */
public class MainViewModel extends BaseViewModel {

    private Disposable mSubscription;

    public MutableLiveData<Boolean> enabled = new MutableLiveData<>(true);

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void startLogin() {
        LoginActivity.launch(ActivityUtils.getTopActivity());
    }

    @Override
    public void injectRxBus() {
        super.injectRxBus();
        mSubscription = RxBus.getDefault()
                .observable(BaseEvent.class)
                .subscribe(data -> {
                    if (data.type == EventType.LOGOUT) {
                        LoginActivity.launch(ActivityUtils.getTopActivity());
                    }
                });
        RxSubscriptions.add(mSubscription);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mSubscription);
    }
}
