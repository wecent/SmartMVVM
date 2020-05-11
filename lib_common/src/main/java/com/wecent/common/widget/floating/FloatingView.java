package com.wecent.common.widget.floating;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;

import com.wecent.common.R;
import com.wecent.common.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.core.view.ViewCompat;

/**
 * @desc: 悬浮窗
 * @author: wecent
 * @date: 2020/4/20
 */
public class FloatingView implements IFloatingView {

    private SmartFloatingView mFloatingView;
    private static volatile FloatingView mInstance;
    @LayoutRes
    private int mLayoutId = R.layout.layout_dialog_remind;
    private List<FloatingData> mFloatingData;
    private boolean isFloatingShow = false;

    private FloatingView() {

    }

    public static FloatingView get() {
        if (mInstance == null) {
            synchronized (FloatingView.class) {
                if (mInstance == null) {
                    mInstance = new FloatingView();
                }
            }
        }
        return mInstance;
    }

    @Override
    public FloatingView remove() {
        if (mFloatingData != null) {
            if (mFloatingData.size() == 1) {
                mFloatingData.remove(mFloatingData.size() - 1);
                close();
            } else {
                mFloatingData.remove(mFloatingData.size() - 1);
                refreshView();
            }
        }
        return this;
    }

    Handler handler;

    @Override
    public FloatingView close() {
        if (handler == null) {
            handler = new Handler(Looper.myLooper());
        }
        handler.post(() -> {
            if (mFloatingView == null) {
                return;
            }
            if (ViewCompat.isAttachedToWindow(mFloatingView)) {
                removeViewToWindow(mFloatingView);
            }
            mFloatingView = null;
            mFloatingData = null;
            isFloatingShow = false;
        });
        return this;
    }

    @Override
    public FloatingView attach() {
        attach(getActivityRoot());
        return this;
    }

    @Override
    public FloatingView attach(FrameLayout container) {
        if (container == null || mFloatingView == null) {
            return this;
        }
        if (mFloatingView.getParent() == container) {
            return this;
        }
        if (getActivityRoot() != null && mFloatingView.getParent() == getActivityRoot()) {
            removeViewToWindow(mFloatingView);
        }
        addViewToWindow(mFloatingView);
        return this;
    }

    @Override
    public FloatingView detach() {
        detach(getActivityRoot());
        return this;
    }

    @Override
    public FloatingView detach(FrameLayout container) {
        if (mFloatingView != null && container != null && ViewCompat.isAttachedToWindow(mFloatingView)) {
            container.removeView(mFloatingView);
        }
        return this;
    }

    @Override
    public SmartFloatingView getView() {
        return mFloatingView;
    }

    @Override
    public List<FloatingData> getData() {
        return mFloatingData;
    }

    @Override
    public boolean isShow() {
        return isFloatingShow;
    }

    @Override
    public FloatingView create(FloatingData data) {
        if (mFloatingView != null) {
            return this;
        }
        if (mFloatingData == null) {
            mFloatingData = new ArrayList<>();
        }
        mFloatingData.add(data);
        initView();
        return this;
    }

    @Override
    public FloatingView create(List<FloatingData> data) {
        if (mFloatingView != null) {
            return this;
        }
        if (mFloatingData == null) {
            mFloatingData = new ArrayList<>();
        }
        mFloatingData.addAll(data);
        initView();
        return this;
    }

    private synchronized void initView() {
        if (mFloatingView != null) {
            return;
        }
        SmartFloatingView smartFloatingView = new SmartFloatingView(FloatingContext.getInstance(), mLayoutId);
        mFloatingView = smartFloatingView;
        refreshView();
        addViewToWindow(smartFloatingView);
        isFloatingShow = true;
    }

    private void refreshView() {
        mFloatingView.setThemeText(mFloatingData.get(mFloatingData.size() - 1).theme);
        mFloatingView.setDateText(mFloatingData.get(mFloatingData.size() - 1).date);
    }

    @Override
    public FloatingView custom(SmartFloatingView viewGroup) {
        mFloatingView = viewGroup;
        return this;
    }

    @Override
    public FloatingView custom(@LayoutRes int resource) {
        mLayoutId = resource;
        return this;
    }

    @Override
    public FloatingView listener(MagnetViewListener magnetViewListener) {
        if (mFloatingView == null) {
            return this;
        }
        mFloatingView.setMagnetViewListener(magnetViewListener);
        mFloatingView.setCloseListener(view -> magnetViewListener.onClose(mFloatingView));
        mFloatingView.setCancelListener(view -> magnetViewListener.onCancel(mFloatingView));
        mFloatingView.setConfirmListener(view -> magnetViewListener.onConfirm(mFloatingView));
        return this;
    }

    @Override
    public FloatingView refresh(FloatingData data) {
        if (mFloatingView == null) {
            return this;
        }
        mFloatingData.add(data);
        mFloatingView.setThemeText(data.theme);
        mFloatingView.setDateText(data.date);
        return this;
    }

    private void addViewToWindow(final View view) {
        if (getActivityRoot() == null) {
            return;
        }
        getActivityRoot().addView(view);
    }

    private void removeViewToWindow(final View view) {
        if (getActivityRoot() == null) {
            return;
        }
        getActivityRoot().removeView(view);
    }

    private FrameLayout getActivityRoot() {
        if (ActivityUtils.getTopActivity() == null) {
            return null;
        }
        try {
            return (FrameLayout) ActivityUtils.getTopActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}