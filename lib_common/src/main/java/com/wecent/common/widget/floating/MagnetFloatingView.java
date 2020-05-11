package com.wecent.common.widget.floating;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @desc: 磁力吸附悬浮窗
 * @author: wecent
 * @date: 2020/4/20
 */
public class MagnetFloatingView extends FrameLayout {

    public static final int MARGIN_EDGE = 16;
    private float mOriginalRawX;
    private float mOriginalRawY;
    private float mOriginalX;
    private float mOriginalY;
    private MagnetViewListener mMagnetViewListener;

    protected int mScreenWidth;
    private int mScreenHeight;
    private int mStatusBarHeight;
    private boolean isNearestLeft = true;

    public void setMagnetViewListener(MagnetViewListener magnetViewListener) {
        this.mMagnetViewListener = magnetViewListener;
    }

    public MagnetFloatingView(Context context) {
        this(context, null);
    }

    public MagnetFloatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnetFloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStatusBarHeight = SystemUtils.getStatusBarHeight(getContext());
        updateViewPosition();
        updateSize();
    }

    private void updateViewPosition() {
        // 限制不可超出屏幕高度
        float desY = mOriginalY - mOriginalRawY;
        if (desY < mStatusBarHeight) {
            desY = mStatusBarHeight;
        }
        setY(desY);
    }

    protected void updateSize() {
        mScreenWidth = (SystemUtils.getScreenWidth(getContext()) - this.getWidth());
        mScreenHeight = SystemUtils.getScreenHeight(getContext());
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateSize();
    }
}
