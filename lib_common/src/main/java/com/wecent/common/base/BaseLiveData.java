package com.wecent.common.base;

import com.wecent.common.bridge.SingleLiveData;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * @desc: BaseLiveData
 * @author: wecent
 * @date: 2020/5/8
 */
public final class BaseLiveData extends SingleLiveData {

    public SingleLiveData<String> showDialogEvent;
    public SingleLiveData<Void> hideDialogEvent;
    public SingleLiveData<Void> finishEvent;
    public SingleLiveData<Void> onBackEvent;

    public SingleLiveData<String> getShowDialogEvent() {
        return showDialogEvent = create(showDialogEvent);
    }

    public SingleLiveData<Void> getHideDialogEvent() {
        return hideDialogEvent = create(hideDialogEvent);
    }

    public SingleLiveData<Void> getFinishEvent() {
        return finishEvent = create(finishEvent);
    }

    public SingleLiveData<Void> getOnBackEvent() {
        return onBackEvent = create(onBackEvent);
    }

    private <T> SingleLiveData<T> create(SingleLiveData<T> liveData) {
        if (liveData == null) {
            liveData = new SingleLiveData<>();
        }
        return liveData;
    }

    @Override
    public void observe(LifecycleOwner owner, Observer observer) {
        super.observe(owner, observer);
    }
}