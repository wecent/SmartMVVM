package com.wecent.smartmvvm.hybrid;

import android.app.Application;

import com.wecent.common.base.BaseViewModel;
import com.wecent.common.binding.command.BindingCommand;
import com.wecent.smartmvvm.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * @desc: HybridViewModel
 * @author: wecent
 * @date: 202/4/13
 */
public class HybridViewModel extends BaseViewModel {

    public MutableLiveData<Integer> leftDrawable = new MutableLiveData<>(R.drawable.ic_title_back);
    public MutableLiveData<String> title = new MutableLiveData<>("百度一下");

    public HybridViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand onBackClick = new BindingCommand(() -> finish());
}
