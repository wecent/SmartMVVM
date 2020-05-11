package com.wecent.common.widget.status;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.wecent.common.R;

import androidx.annotation.LayoutRes;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/4/14
 */
public class MultiStateLayout extends BaseStateLayout {

    private static final int MIN_SHOW_TIME = 100;
    private static final int MIN_SHOW_DELAY = 100;

    private int resIdEmpty;
    private int resIdLoading;
    private int resIdError;
    private int resIdNoNet;

    private int mTargetState = -1;
    private long mLoadingStartTime = -1;

    private final Runnable mLoadingHide = () -> {
        setViewState(mTargetState);
        mLoadingStartTime = -1;
        mTargetState = -1;
    };

    public MultiStateLayout(Context context) {
        this(context, null);
    }

    public MultiStateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiStateLayout);
        resIdEmpty = typedArray.getResourceId(R.styleable.MultiStateLayout_state_emptyLayout, R.layout.layout_status_empty);
        resIdLoading = typedArray.getResourceId(R.styleable.MultiStateLayout_state_loadingLayout, R.layout.layout_status_loading);
        resIdError = typedArray.getResourceId(R.styleable.MultiStateLayout_state_errorLayout, R.layout.layout_status_error);
        resIdNoNet = typedArray.getResourceId(R.styleable.MultiStateLayout_state_nonetLayout, R.layout.layout_status_nonet);

        addViewForStatus(STATE_EMPTY, resIdEmpty);
        addViewForStatus(STATE_ERROR, resIdError);
        addViewForStatus(STATE_LOADING, resIdLoading);
        addViewForStatus(STATE_NONET, resIdNoNet);
        typedArray.recycle();

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(mLoadingHide);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mLoadingHide);
    }

    /**
     * 显示进度页
     */
    public void showLoading() {
        setViewState(STATE_LOADING);
    }

    /**
     * 显示错误页
     */
    public void showError() {
        postDelayed(() -> setViewState(STATE_ERROR), 100);
    }

    /**
     * 无数据时
     */
    public void showEmpty() {
        postDelayed(() -> setViewState(STATE_EMPTY), 100);
    }

    /**
     * 无网络时
     */
    public void showNoNet() {
        this.postDelayed(() -> setViewState(STATE_NONET), 100);
    }

    /**
     * 显示内容
     */
    public void showContent() {
        this.postDelayed(() -> setViewState(STATE_CONTENT), 100);
    }

    @Override
    public void setViewState(int state) {
        if (getViewState() == STATE_LOADING && state != STATE_LOADING) {
            long diff = System.currentTimeMillis() - mLoadingStartTime;
            if (diff < MIN_SHOW_TIME) {
                mTargetState = state;
                postDelayed(mLoadingHide, MIN_SHOW_DELAY);
            } else {
                super.setViewState(state);
            }
            return;
        } else if (state == STATE_LOADING) {
            mLoadingStartTime = System.currentTimeMillis();
        }
        super.setViewState(state);
    }


    /**
     * 设置emptyView的自定义Layout
     *
     * @param emptyResource emptyView的layoutResource
     */
    public MultiStateLayout setEmptyResource(@LayoutRes int emptyResource) {
        this.resIdEmpty = emptyResource;
        addViewForStatus(STATE_EMPTY, resIdEmpty);
        return this;
    }

    /**
     * 设置retryView的自定义Layout
     *
     * @param errorResource errorView的layoutResource
     */
    public MultiStateLayout setErrorResource(@LayoutRes int errorResource) {
        this.resIdError = errorResource;
        addViewForStatus(STATE_ERROR, resIdError);
        return this;
    }

    /**
     * 设置loadingView的自定义Layout
     *
     * @param loadingResource loadingView的layoutResource
     */
    public MultiStateLayout setLoadingResource(@LayoutRes int loadingResource) {
        resIdLoading = loadingResource;
        addViewForStatus(STATE_LOADING, resIdLoading);
        return this;
    }

    /**
     * 设置NoNetView的自定义Layout
     *
     * @param noNetResource loadingView的layoutResource
     */
    public MultiStateLayout setNoNetResource(@LayoutRes int noNetResource) {
        resIdNoNet = noNetResource;
        addViewForStatus(STATE_NONET, resIdNoNet);
        return this;
    }

    public MultiStateLayout build() {
        showLoading();
        return this;
    }

}