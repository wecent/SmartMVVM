package com.wecent.common.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecent.common.R;
import com.wecent.common.utils.ScreenUtils;
import com.wecent.common.utils.SizeUtils;
import com.wecent.common.utils.StatusBarUtils;

import java.util.UUID;

/**
 * @desc: 一款好用的通用标题栏
 * <p/>
 * <declare-styleable name="GCommonTitleBar">
 * <attr name="titleBarBg" format="reference" /> <!-- 标题栏背景 图片或颜色 -->
 * <attr name="titleBarHeight" format="dimension" /> <!-- 标题栏高度 -->
 * <attr name="showBottomLine" format="boolean" /> <!-- 显示标题栏分割线 -->
 * <attr name="bottomLineColor" format="color" /> <!-- 标题栏分割线颜色 -->
 * <attr name="bottomShadowHeight" format="dimension" /> <!-- 底部阴影高度 showBottomLine = false时有效 -->
 * <!-- 状态栏模式 -->
 * <attr name="statusBarMode" format="enum">
 * <enum name="dark" value="0" />
 * <enum name="light" value="1" />
 * </attr>
 * <p/>
 * <!-- 左边视图类型 -->
 * <attr name="leftType">
 * <enum name="none" value="0" />
 * <enum name="textView" value="1" />
 * <enum name="imageButton" value="2" />
 * <enum name="customView" value="3" />
 * </attr>
 * <p/>
 * <attr name="leftText" format="string" /> <!-- TextView 文字, 对应leftType_TextView -->
 * <attr name="leftTextColor" format="color" /> <!-- TextView 颜色, 对应leftType_TextView -->
 * <attr name="leftTextSize" format="dimension" /> <!-- TextView 字体大小, 对应leftType_TextView -->
 * <attr name="leftDrawable" format="reference" /> <!-- TextView 左边图片, 对应leftType_TextView -->
 * <attr name="leftDrawablePadding" format="dimension" /> <!-- TextView 左边padding, 对应leftType_TextView -->
 * <attr name="leftImageResource" format="reference" /> <!-- ImageView 资源, 对应leftType_ImageButton -->
 * <attr name="leftCustomView" format="reference" /> <!-- 左边自定义布局, 对应leftType_CustomView -->
 * <p/>
 * <!-- 右边视图类型 -->
 * <attr name="rightType">
 * <enum name="none" value="0" />
 * <enum name="textView" value="1" />
 * <enum name="imageButton" value="2" />
 * <enum name="customView" value="3" />
 * </attr>
 * <p/>
 * <attr name="rightText" format="string" /> <!-- TextView 文字, 对应rightType_TextView -->
 * <attr name="rightTextColor" format="color" /> <!-- TextView 颜色, 对应rightType_TextView -->
 * <attr name="rightTextSize" format="dimension" /> <!-- TextView 字体大小, 对应rightType_TextView -->
 * <attr name="rightImageResource" format="reference" /> <!-- ImageView 资源, 对应rightType_ImageButton -->
 * <attr name="rightCustomView" format="reference" /> <!-- 右边自定义布局, 对应rightType_CustomView -->
 * <p/>
 * <!-- 中间视图类型 -->
 * <enum name="none" value="0" />
 * <enum name="textView" value="1" />
 * <enum name="customView" value="2" />
 * </attr>
 * <p/>
 * <attr name="centerText" format="string" /> <!-- TextView 文字, 对应centerType_TextView -->
 * <attr name="centerTextColor" format="color" /> <!-- TextView 颜色, 对应centerType_TextView -->
 * <attr name="centerTextSize" format="dimension" /> <!-- TextView 字体大小, 对应centerType_TextView -->
 * <attr name="centerTextMarquee" format="boolean" /> <!-- TextView 跑马灯效果, 对应centerType_TextView -->
 * <attr name="centerSubText" format="string" /> <!-- 子标题TextView 文字, 对应centerType_TextView -->
 * <attr name="centerSubTextColor" format="color" /> <!-- 子标题TextView 颜色, 对应centerType_TextView -->
 * <attr name="centerSubTextSize" format="dimension" /> <!-- 子标题TextView 字体大小, 对应centerType_TextView -->
 * <attr name="centerSearchEdiable" format="boolean" /> <!-- 搜索输入框是否可输入 -->
 * <attr name="centerCustomView" format="reference" /> <!-- 中间自定义布局, 对应centerType_CustomView -->
 * </declare-styleable>
 * <p/>
 * @author: wecent
 * @date: 2020/4/12
 */
@SuppressWarnings("ResourceType")
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    // 左边TextView被点击
    public static final int ACTION_LEFT_TEXT = 1;
    // 左边ImageBtn被点击
    public static final int ACTION_LEFT_BUTTON = 2;
    // 右边TextView被点击
    public static final int ACTION_RIGHT_TEXT = 3;
    // 右边ImageBtn被点击
    public static final int ACTION_RIGHT_BUTTON = 4;
    // 中间文字点击
    public static final int ACTION_CENTER_TEXT = 5;

    private View viewStatusBarFill;                     // 状态栏填充视图
    private View viewBottomLine;                        // 分隔线视图
    private View viewBottomShadow;                      // 底部阴影
    private RelativeLayout rlMain;                      // 主视图

    private TextView tvLeft;                            // 左边TextView
    private ImageButton btnLeft;                        // 左边ImageButton
    private View viewCustomLeft;
    private TextView tvRight;                           // 右边TextView
    private ImageButton btnRight;                       // 右边ImageButton
    private View viewCustomRight;
    private LinearLayout llMainCenter;
    private TextView tvCenter;                          // 标题栏文字
    private TextView tvCenterSub;                       // 副标题栏文字
    private ProgressBar progressCenter;                 // 中间进度条,默认隐藏
    private View centerCustomView;                      // 中间自定义视图

    private int titleBarColor;                          // 标题栏背景颜色
    private int titleBarHeight;                         // 标题栏高度

    private boolean showBottomLine;                     // 是否显示底部分割线
    private int bottomLineColor;                        // 分割线颜色
    private float bottomShadowHeight;                   // 底部阴影高度

    private int leftType;                               // 左边视图类型
    private String leftText;                            // 左边TextView文字
    private int leftTextColor;                          // 左边TextView颜色
    private float leftTextSize;                         // 左边TextView文字大小
    private int leftDrawable;                           // 左边TextView drawableLeft资源
    private float leftDrawablePadding;                  // 左边TextView drawablePadding
    private int leftImageResource;                      // 左边图片资源
    private int leftCustomViewRes;                      // 左边自定义视图布局资源

    private int rightType;                              // 右边视图类型
    private String rightText;                           // 右边TextView文字
    private int rightTextColor;                         // 右边TextView颜色
    private float rightTextSize;                        // 右边TextView文字大小
    private int rightImageResource;                     // 右边图片资源
    private int rightCustomViewRes;                     // 右边自定义视图布局资源

    private int centerType;                             // 中间视图类型
    private String centerText;                          // 中间TextView文字
    private int centerTextColor;                        // 中间TextView字体颜色
    private float centerTextSize;                       // 中间TextView字体大小
    private boolean centerTextMarquee;                  // 中间TextView字体是否显示跑马灯效果
    private String centerSubText;                       // 中间subTextView文字
    private int centerSubTextColor;                     // 中间subTextView字体颜色
    private float centerSubTextSize;                    // 中间subTextView字体大小
    private int centerCustomViewRes;                    // 中间自定义布局资源

    private int PADDING_12;

    private OnTitleBarClickListener clickListener;
    private OnTitleBarDoubleClickListener doubleClickListener;

    private static final int TYPE_LEFT_NONE = 0;
    private static final int TYPE_LEFT_TEXT = 1;
    private static final int TYPE_LEFT_IMAGE = 2;
    private static final int TYPE_LEFT_CUSTOM = 3;
    private static final int TYPE_RIGHT_NONE = 0;
    private static final int TYPE_RIGHT_TEXT = 1;
    private static final int TYPE_RIGHT_IMAGE = 2;
    private static final int TYPE_RIGHT_CUSTOM = 3;
    private static final int TYPE_CENTER_NONE = 0;
    private static final int TYPE_CENTER_TEXT = 1;
    private static final int TYPE_CENTER_CUSTOM = 2;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context, attrs);
        initGlobalViews(context);
        initMainViews(context);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        PADDING_12 = SizeUtils.dp2px(12);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        titleBarColor = array.getColor(R.styleable.TitleBar_titleBarColor, Color.parseColor("#ffffff"));
        titleBarHeight = (int) array.getDimension(R.styleable.TitleBar_titleBarHeight, SizeUtils.dp2px(44));

        showBottomLine = array.getBoolean(R.styleable.TitleBar_showBottomLine, true);
        bottomLineColor = array.getColor(R.styleable.TitleBar_bottomLineColor, Color.parseColor("#dddddd"));
        bottomShadowHeight = array.getDimension(R.styleable.TitleBar_bottomShadowHeight, SizeUtils.dp2px(0));

        leftType = array.getInt(R.styleable.TitleBar_leftType, TYPE_LEFT_NONE);
        if (leftType == TYPE_LEFT_TEXT) {
            leftText = array.getString(R.styleable.TitleBar_leftText);
            leftTextColor = array.getColor(R.styleable.TitleBar_leftTextColor, getResources().getColor(R.color.comm_titlebar_text_selector));
            leftTextSize = array.getDimension(R.styleable.TitleBar_leftTextSize, SizeUtils.dp2px(16));
            leftDrawable = array.getResourceId(R.styleable.TitleBar_leftDrawable, 0);
            leftDrawablePadding = array.getDimension(R.styleable.TitleBar_leftDrawablePadding, 5);
        } else if (leftType == TYPE_LEFT_IMAGE) {
            leftImageResource = array.getResourceId(R.styleable.TitleBar_leftImageResource, R.drawable.comm_titlebar_reback_selector);
        } else if (leftType == TYPE_LEFT_CUSTOM) {
            leftCustomViewRes = array.getResourceId(R.styleable.TitleBar_leftCustomView, 0);
        }

        rightType = array.getInt(R.styleable.TitleBar_rightType, TYPE_RIGHT_NONE);
        if (rightType == TYPE_RIGHT_TEXT) {
            rightText = array.getString(R.styleable.TitleBar_rightText);
            rightTextColor = array.getColor(R.styleable.TitleBar_rightTextColor, getResources().getColor(R.color.comm_titlebar_text_selector));
            rightTextSize = array.getDimension(R.styleable.TitleBar_rightTextSize, SizeUtils.dp2px(16));
        } else if (rightType == TYPE_RIGHT_IMAGE) {
            rightImageResource = array.getResourceId(R.styleable.TitleBar_rightImageResource, 0);
        } else if (rightType == TYPE_RIGHT_CUSTOM) {
            rightCustomViewRes = array.getResourceId(R.styleable.TitleBar_rightCustomView, 0);
        }

        centerType = array.getInt(R.styleable.TitleBar_centerType, TYPE_CENTER_NONE);
        if (centerType == TYPE_CENTER_TEXT) {
            centerText = array.getString(R.styleable.TitleBar_centerText);
            centerTextColor = array.getColor(R.styleable.TitleBar_centerTextColor, Color.parseColor("#333333"));
            centerTextSize = array.getDimension(R.styleable.TitleBar_centerTextSize, SizeUtils.dp2px(18));
            centerTextMarquee = array.getBoolean(R.styleable.TitleBar_centerTextMarquee, true);
            centerSubText = array.getString(R.styleable.TitleBar_centerSubText);
            centerSubTextColor = array.getColor(R.styleable.TitleBar_centerSubTextColor, Color.parseColor("#666666"));
            centerSubTextSize = array.getDimension(R.styleable.TitleBar_centerSubTextSize, SizeUtils.dp2px(11));
        } else if (centerType == TYPE_CENTER_CUSTOM) {
            centerCustomViewRes = array.getResourceId(R.styleable.TitleBar_centerCustomView, 0);
        }

        array.recycle();
    }

    private final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 初始化全局视图
     *
     * @param context 上下文
     */
    private void initGlobalViews(Context context) {
        ViewGroup.LayoutParams globalParams = new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        setLayoutParams(globalParams);

        int statusBarHeight = StatusBarUtils.getStatusBarHeight();
        viewStatusBarFill = new View(context);
        viewStatusBarFill.setId(generateViewId());
        viewStatusBarFill.setBackgroundColor(titleBarColor);
        LayoutParams statusBarParams = new LayoutParams(MATCH_PARENT, statusBarHeight);
        statusBarParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(viewStatusBarFill, statusBarParams);
        // 构建主视图
        rlMain = new RelativeLayout(context);
        rlMain.setId(generateViewId());
        rlMain.setBackgroundColor(titleBarColor);
        LayoutParams mainParams = new LayoutParams(MATCH_PARENT, titleBarHeight);
        mainParams.addRule(RelativeLayout.BELOW, viewStatusBarFill.getId());


        // 计算主布局高度
        if (showBottomLine) {
            mainParams.height = titleBarHeight - Math.max(1, SizeUtils.dp2px(0.4f));
        } else {
            mainParams.height = titleBarHeight;
        }
        addView(rlMain, mainParams);

        // 构建分割线视图
        if (showBottomLine) {
            // 已设置显示标题栏分隔线,5.0以下机型,显示分隔线
            viewBottomLine = new View(context);
            viewBottomLine.setBackgroundColor(bottomLineColor);
            LayoutParams bottomLineParams = new LayoutParams(MATCH_PARENT, Math.max(1, SizeUtils.dp2px(0.4f)));
            bottomLineParams.addRule(RelativeLayout.BELOW, rlMain.getId());

            addView(viewBottomLine, bottomLineParams);
        } else if (bottomShadowHeight != 0) {
            viewBottomShadow = new View(context);
            viewBottomShadow.setBackgroundResource(R.drawable.comm_titlebar_bottom_shadow);
            LayoutParams bottomShadowParams = new LayoutParams(MATCH_PARENT, SizeUtils.dp2px(bottomShadowHeight));
            bottomShadowParams.addRule(RelativeLayout.BELOW, rlMain.getId());

            addView(viewBottomShadow, bottomShadowParams);
        }
    }

    /**
     * 初始化主视图
     *
     * @param context 上下文
     */
    private void initMainViews(Context context) {
        if (leftType != TYPE_LEFT_NONE) {
            initMainLeftViews(context);
        }
        if (rightType != TYPE_RIGHT_NONE) {
            initMainRightViews(context);
        }
        if (centerType != TYPE_CENTER_NONE) {
            initMainCenterViews(context);
        }
    }

    /**
     * 初始化主视图左边部分
     * -- add: adaptive RTL
     *
     * @param context 上下文
     */
    private void initMainLeftViews(Context context) {
        LayoutParams leftInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (leftType == TYPE_LEFT_TEXT) {
            // 初始化左边TextView
            tvLeft = new TextView(context);
            tvLeft.setId(generateViewId());
            tvLeft.setText(leftText);
            tvLeft.setTextColor(leftTextColor);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            tvLeft.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tvLeft.setSingleLine(true);
            tvLeft.setOnClickListener(this);
            // 设置DrawableLeft及DrawablePadding
            if (leftDrawable != 0) {
                tvLeft.setCompoundDrawablePadding((int) leftDrawablePadding);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    tvLeft.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, 0, 0, 0);
                } else {
                    tvLeft.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0);
                }
            }
            tvLeft.setPadding(PADDING_12, 0, PADDING_12, 0);

            rlMain.addView(tvLeft, leftInnerParams);

        } else if (leftType == TYPE_LEFT_IMAGE) {
            // 初始化左边ImageButton
            btnLeft = new ImageButton(context);
            btnLeft.setId(generateViewId());
            btnLeft.setBackgroundColor(Color.TRANSPARENT);
            btnLeft.setImageResource(leftImageResource);
            btnLeft.setPadding(PADDING_12, 0, PADDING_12, 0);
            btnLeft.setOnClickListener(this);

            rlMain.addView(btnLeft, leftInnerParams);

        } else if (leftType == TYPE_LEFT_CUSTOM) {
            // 初始化自定义View
            viewCustomLeft = LayoutInflater.from(context).inflate(leftCustomViewRes, rlMain, false);
            if (viewCustomLeft.getId() == View.NO_ID) {
                viewCustomLeft.setId(generateViewId());
            }
            rlMain.addView(viewCustomLeft, leftInnerParams);
        }
    }

    /**
     * 初始化主视图右边部分
     * -- add: adaptive RTL
     *
     * @param context 上下文
     */
    private void initMainRightViews(Context context) {
        LayoutParams rightInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (rightType == TYPE_RIGHT_TEXT) {
            // 初始化右边TextView
            tvRight = new TextView(context);
            tvRight.setId(generateViewId());
            tvRight.setText(rightText);
            tvRight.setTextColor(rightTextColor);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
            tvRight.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            tvRight.setSingleLine(true);
            tvRight.setPadding(PADDING_12, 0, PADDING_12, 0);
            tvRight.setOnClickListener(this);
            rlMain.addView(tvRight, rightInnerParams);

        } else if (rightType == TYPE_RIGHT_IMAGE) {
            // 初始化右边ImageBtn
            btnRight = new ImageButton(context);
            btnRight.setId(generateViewId());
            btnRight.setImageResource(rightImageResource);
            btnRight.setBackgroundColor(Color.TRANSPARENT);
            btnRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnRight.setPadding(PADDING_12, 0, PADDING_12, 0);
            btnRight.setOnClickListener(this);
            rlMain.addView(btnRight, rightInnerParams);

        } else if (rightType == TYPE_RIGHT_CUSTOM) {
            // 初始化自定义view
            viewCustomRight = LayoutInflater.from(context).inflate(rightCustomViewRes, rlMain, false);
            if (viewCustomRight.getId() == View.NO_ID) {
                viewCustomRight.setId(generateViewId());
            }
            rlMain.addView(viewCustomRight, rightInnerParams);
        }
    }

    /**
     * 初始化主视图中间部分
     *
     * @param context 上下文
     */
    private void initMainCenterViews(Context context) {
        if (centerType == TYPE_CENTER_TEXT) {
            // 初始化中间子布局
            llMainCenter = new LinearLayout(context);
            llMainCenter.setId(generateViewId());
            llMainCenter.setGravity(Gravity.CENTER);
            llMainCenter.setOrientation(LinearLayout.VERTICAL);
            llMainCenter.setOnClickListener(this);

            LayoutParams centerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
            centerParams.setMarginStart(PADDING_12);
            centerParams.setMarginEnd(PADDING_12);
            centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlMain.addView(llMainCenter, centerParams);

            // 初始化标题栏TextView
            tvCenter = new TextView(context);
            tvCenter.setText(centerText);
            tvCenter.setTextColor(centerTextColor);
            tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerTextSize);
            tvCenter.setGravity(Gravity.CENTER);
            tvCenter.setSingleLine(true);
            // 设置跑马灯效果
            tvCenter.setMaxWidth((int) (ScreenUtils.getScreenWidth() * 3 / 5.0));
            if (centerTextMarquee) {
                tvCenter.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tvCenter.setMarqueeRepeatLimit(-1);
                tvCenter.requestFocus();
                tvCenter.setSelected(true);
            }

            LinearLayout.LayoutParams centerTextParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            llMainCenter.addView(tvCenter, centerTextParams);

            // 初始化进度条, 显示于标题栏左边
            progressCenter = new ProgressBar(context);
            progressCenter.setIndeterminateDrawable(getResources().getDrawable(R.drawable.comm_titlebar_progress_draw));
            progressCenter.setVisibility(View.GONE);
            int progressWidth = SizeUtils.dp2px(18);
            LayoutParams progressParams = new LayoutParams(progressWidth, progressWidth);
            progressParams.addRule(RelativeLayout.CENTER_VERTICAL);
            progressParams.addRule(RelativeLayout.START_OF, llMainCenter.getId());
            rlMain.addView(progressCenter, progressParams);

            // 初始化副标题栏
            tvCenterSub = new TextView(context);
            tvCenterSub.setText(centerSubText);
            tvCenterSub.setTextColor(centerSubTextColor);
            tvCenterSub.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerSubTextSize);
            tvCenterSub.setGravity(Gravity.CENTER);
            tvCenterSub.setSingleLine(true);
            if (TextUtils.isEmpty(centerSubText)) {
                tvCenterSub.setVisibility(View.GONE);
            }

            LinearLayout.LayoutParams centerSubTextParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            llMainCenter.addView(tvCenterSub, centerSubTextParams);
        } else if (centerType == TYPE_CENTER_CUSTOM) {
            // 初始化中间自定义布局
            centerCustomView = LayoutInflater.from(context).inflate(centerCustomViewRes, rlMain, false);
            if (centerCustomView.getId() == View.NO_ID) {
                centerCustomView.setId(generateViewId());
            }
            LayoutParams centerCustomParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
            centerCustomParams.setMarginStart(PADDING_12);
            centerCustomParams.setMarginEnd(PADDING_12);
            centerCustomParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlMain.addView(centerCustomView, centerCustomParams);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private Window getWindow() {
        Context context = getContext();
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            activity = (Activity) ((ContextWrapper) context).getBaseContext();
        }
        if (activity != null) {
            return activity.getWindow();
        }
        return null;
    }

    private long lastClickMillis = 0;     // 双击事件中，上次被点击时间

    @Override
    public void onClick(View v) {
        if (clickListener == null) {
            return;
        }

        if (v.equals(llMainCenter) && doubleClickListener != null) {
            long currentClickMillis = System.currentTimeMillis();
            if (currentClickMillis - lastClickMillis < 500) {
                doubleClickListener.onClicked(v);
            }
            lastClickMillis = currentClickMillis;
        } else if (v.equals(tvLeft)) {
            clickListener.onClicked(v, ACTION_LEFT_TEXT, null);
        } else if (v.equals(btnLeft)) {
            clickListener.onClicked(v, ACTION_LEFT_BUTTON, null);
        } else if (v.equals(tvRight)) {
            clickListener.onClicked(v, ACTION_RIGHT_TEXT, null);
        } else if (v.equals(btnRight)) {
            clickListener.onClicked(v, ACTION_RIGHT_BUTTON, null);
        } else if (v.equals(tvCenter)) {
            clickListener.onClicked(v, ACTION_CENTER_TEXT, null);
        }
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    @Override
    public void setBackgroundColor(int color) {
        rlMain.setBackgroundColor(color);
    }

    /**
     * 设置背景图片
     *
     * @param resource
     */
    @Override
    public void setBackgroundResource(int resource) {
        setBackgroundColor(Color.TRANSPARENT);
        super.setBackgroundResource(resource);
    }

    /**
     * 获取标题栏底部横线
     *
     * @return
     */
    public View getBottomLine() {
        return viewBottomLine;
    }

    /**
     * 获取标题栏左边TextView，对应leftType = textView
     *
     * @return
     */
    public TextView getLeftTextView() {
        return tvLeft;
    }

    /**
     * 获取标题栏左边ImageButton，对应leftType = imageButton
     *
     * @return
     */
    public ImageButton getLeftImageButton() {
        return btnLeft;
    }

    /**
     * 获取标题栏右边TextView，对应rightType = textView
     *
     * @return
     */
    public TextView getRightTextView() {
        return tvRight;
    }

    /**
     * 获取标题栏右边ImageButton，对应rightType = imageButton
     *
     * @return
     */
    public ImageButton getRightImageButton() {
        return btnRight;
    }

    public LinearLayout getCenterLayout() {
        return llMainCenter;
    }

    /**
     * 获取标题栏中间TextView，对应centerType = textView
     *
     * @return
     */
    public TextView getCenterTextView() {
        return tvCenter;
    }

    public TextView getCenterSubTextView() {
        return tvCenterSub;
    }

    /**
     * 获取左边自定义布局
     *
     * @return
     */
    public View getLeftCustomView() {
        return viewCustomLeft;
    }

    /**
     * 获取右边自定义布局
     *
     * @return
     */
    public View getRightCustomView() {
        return viewCustomRight;
    }

    /**
     * 获取中间自定义布局视图
     *
     * @return
     */
    public View getCenterCustomView() {
        return centerCustomView;
    }

    /**
     * @param leftView
     */
    public void setLeftView(View leftView) {
        if (leftView.getId() == View.NO_ID) {
            leftView.setId(generateViewId());
        }
        LayoutParams leftInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(leftView, leftInnerParams);
    }

    /**
     * @param centerView
     */
    public void setCenterView(View centerView) {
        if (centerView.getId() == View.NO_ID) {
            centerView.setId(generateViewId());
        }
        LayoutParams centerInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        centerInnerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(centerView, centerInnerParams);
    }

    /**
     * @param rightView
     */
    public void setRightView(View rightView) {
        if (rightView.getId() == View.NO_ID) {
            rightView.setId(generateViewId());
        }
        LayoutParams rightInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(rightView, rightInnerParams);
    }

    /**
     * 显示中间进度条
     */
    public void showCenterProgress() {
        progressCenter.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏中间进度条
     */
    public void dismissCenterProgress() {
        progressCenter.setVisibility(View.GONE);
    }

    /**
     * 设置点击事件监听
     *
     * @param listener
     */

    public void setClickListener(OnTitleBarClickListener listener) {
        this.clickListener = listener;
    }

    /**
     * 设置双击监听
     *
     * @param listener
     */
    public void setDoubleClickListener(OnTitleBarDoubleClickListener listener) {
        this.doubleClickListener = listener;
    }

    /**
     * 标题栏点击事件监听
     */
    public interface OnTitleBarClickListener {
        /**
         * @param view   视图
         * @param action 对应ACTION_XXX, 如ACTION_LEFT_TEXT
         * @param extra  中间为搜索框时,如果可输入,点击键盘的搜索按钮,会返回输入关键词
         */
        void onClicked(View view, int action, String extra);
    }

    /**
     * 标题栏双击事件监听
     */
    public interface OnTitleBarDoubleClickListener {
        /**
         * @param view 视图
         */
        void onClicked(View view);
    }

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }
}
