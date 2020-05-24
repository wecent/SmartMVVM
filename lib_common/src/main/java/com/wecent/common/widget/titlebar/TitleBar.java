package com.wecent.common.widget.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecent.common.R;
import com.wecent.common.utils.SizeUtils;
import com.wecent.common.utils.StatusBarUtils;

import androidx.core.content.ContextCompat;

/**
 * desc: 一个简单的标题栏
 * author: wecent
 * date: 2017/9/2
 */
public final class TitleBar extends RelativeLayout {

    private RelativeLayout mRootView;
    private View mBottomView;
    private View mStatusView;
    private TextView mTitleView;
    private TextView mLeftView;
    private TextView mRightViw;
    private String titleText;
    private String leftText;
    private String rightText;
    private int leftDrawable;
    private int rightDrawable;
    private boolean showStatus;
    private boolean showBottom;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        titleText = array.getString(R.styleable.TitleBar_titleText);
        leftText = array.getString(R.styleable.TitleBar_leftText);
        rightText = array.getString(R.styleable.TitleBar_rightText);
        leftDrawable = array.getResourceId(R.styleable.TitleBar_leftDrawable, 0);
        rightDrawable = array.getResourceId(R.styleable.TitleBar_rightDrawable, 0);
        showStatus = array.getBoolean(R.styleable.TitleBar_showStatus, true);
        showBottom = array.getBoolean(R.styleable.TitleBar_showBottom, false);
        initView();
    }

    private void initView() {
        View contentView = inflate(getContext(), R.layout.layout_titlebar, this);
        mRootView = contentView.findViewById(R.id.rl_root);
        mStatusView = contentView.findViewById(R.id.v_status);
        showStatusBar(showStatus);
        mBottomView = contentView.findViewById(R.id.v_bottom);
        showBottomLine(showBottom);
        mTitleView = contentView.findViewById(R.id.tv_title);
        setTitleText(titleText);
        mLeftView = contentView.findViewById(R.id.tv_left);
        if (leftDrawable == 0) {
            setLeftText(leftText);
        } else {
            setLeftDrawable(leftDrawable);
        }
        mRightViw = contentView.findViewById(R.id.tv_right);
        if (rightDrawable == 0) {
            setRightText(rightText);
        } else {
            setRightDrawable(rightDrawable);
        }
    }

    /**
     * 设置颜色背景
     *
     * @param resBackground
     */
    public void setColorBackground(int resBackground) {
        mRootView.setBackgroundColor(getContext().getResources().getColor(resBackground));
    }

    /**
     * 设置图片背景
     *
     * @param resBackground
     */
    public void setDrawableBackground(Drawable resBackground) {
        mRootView.setBackground(resBackground);
    }

    /**
     * 设置标题文本
     *
     * @param strTitle
     */
    public void setTitleText(String strTitle) {
        if (!TextUtils.isEmpty(strTitle)) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(strTitle);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        mTitleView.setTextColor(getContext().getResources().getColor(color));
    }

    /**
     * 设置左边文本
     *
     * @param strLeft
     */
    public void setLeftText(String strLeft) {
        if (!TextUtils.isEmpty(strLeft)) {
            mLeftView.setVisibility(View.VISIBLE);
            mLeftView.setText(strLeft);
        } else {
            mLeftView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setLeftTextColor(int color) {
        mLeftView.setTextColor(getContext().getResources().getColor(color));
    }

    /**
     * 设置左边图片
     *
     * @param resLeft
     */
    public void setLeftDrawable(int resLeft) {
        if (resLeft != 0) {
            mLeftView.setVisibility(View.VISIBLE);
            Drawable icLeft = ContextCompat.getDrawable(getContext(), resLeft);
            icLeft.setBounds(0, 0, icLeft.getMinimumWidth(), icLeft.getMinimumHeight());
            mLeftView.setCompoundDrawables(icLeft, null, null, null);
        } else {
            mLeftView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左边监听事件
     *
     * @param listener
     */
    public void setOnLeftClickListener(final OnClickListener listener) {
        mLeftView.setOnClickListener(listener);
    }

    /**
     * 设置右边文本
     *
     * @param strRight
     */
    public void setRightText(String strRight) {
        if (!TextUtils.isEmpty(strRight)) {
            mRightViw.setVisibility(View.VISIBLE);
            mRightViw.setText(strRight);
        } else {
            mRightViw.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边字体的颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        mRightViw.setTextColor(getContext().getResources().getColor(color));
    }

    /**
     * 设置右边字体的图片背景
     *
     * @param resRight
     */
    public void setRightBackground(int resRight) {
        if (resRight != 0) {
            mRightViw.setVisibility(View.VISIBLE);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, SizeUtils.dp2px(16), 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            mRightViw.setLayoutParams(params);
            mRightViw.setBackground(getContext().getResources().getDrawable(resRight));
        } else {
            mRightViw.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边字体是否选择
     *
     * @param selected
     */
    public void setRightSelected(boolean selected) {
        mRightViw.setSelected(selected);
    }

    /**
     * 获取右边字体是否选择
     */
    public boolean getRightSelected() {
        return mRightViw.isSelected();
    }

    /**
     * 设置右边图片
     *
     * @param resRight
     */
    public void setRightDrawable(int resRight) {
        if (resRight != 0) {
            mRightViw.setVisibility(View.VISIBLE);
            Drawable icRight = ContextCompat.getDrawable(getContext(), resRight);
            icRight.setBounds(0, 0, icRight.getMinimumWidth(), icRight.getMinimumHeight());
            mRightViw.setCompoundDrawables(null, null, icRight, null);
        } else {
            mRightViw.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边监听事件
     *
     * @param listener
     */
    public void setOnRightClickListener(final OnClickListener listener) {
        mRightViw.setOnClickListener(listener);
    }

    /**
     * 设置主页文本
     *
     * @param isShow
     */
    public void showStatusBar(Boolean isShow) {
        if (isShow) {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    StatusBarUtils.getStatusBarHeight());
            mStatusView.setLayoutParams(params);
            mStatusView.setVisibility(View.VISIBLE);
        } else {
            mStatusView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置主页文本
     *
     * @param isShow
     */
    public void showBottomLine(Boolean isShow) {
        if (isShow) {
            mBottomView.setVisibility(View.VISIBLE);
        } else {
            mBottomView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置主页文本颜色
     *
     * @param color
     */
    public void setBottomLineColor(int color) {
        mBottomView.setBackgroundColor(getContext().getResources().getColor(color));
    }

}
