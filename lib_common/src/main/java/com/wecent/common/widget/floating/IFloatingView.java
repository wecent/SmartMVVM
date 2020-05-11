package com.wecent.common.widget.floating;

import android.app.Activity;
import android.widget.FrameLayout;

import java.util.List;

import androidx.annotation.LayoutRes;

/**
 * @desc: FloatingData
 * @author: wecent
 * @date: 2020/4/20
 */
public interface IFloatingView {

    /**
     * 获取悬浮窗对象
     *
     * @return
     */
    SmartFloatingView getView();

    /**
     * 获取悬浮窗数据
     *
     * @return
     */
    List<FloatingData> getData();

    /**
     * 获取悬浮窗是否显示
     *
     * @return
     */
    boolean isShow();

    /**
     * 移除悬浮窗
     *
     * @return
     */
    FloatingView remove();

    /**
     * 关闭悬浮窗
     *
     * @return
     */
    FloatingView close();


    /**
     * 绑定悬浮窗到Activity
     *
     * @return
     */
    FloatingView attach();

    /**
     * 绑定悬浮窗到FrameLayout
     *
     * @param container
     * @return
     */
    FloatingView attach(FrameLayout container);

    /**
     * 解除绑定在Activity的悬浮窗
     *
     * @return
     */
    FloatingView detach();

    /**
     * 解除绑定在FrameLayout的悬浮窗
     *
     * @param container
     * @return
     */
    FloatingView detach(FrameLayout container);

    /**
     * 自定义视图
     *
     * @param viewGroup
     * @return
     */
    FloatingView custom(SmartFloatingView viewGroup);

    /**
     * 自定义视图
     *
     * @param resource
     * @return
     */
    FloatingView custom(@LayoutRes int resource);

    /**
     * 绑定监听事件
     *
     * @param listener
     * @return
     */
    FloatingView listener(MagnetViewListener listener);

    /**
     * 设置悬浮窗初始化数据，需要在add()之前
     *
     * @param data
     * @return
     */
    FloatingView create(FloatingData data);

    /**
     * 设置悬浮窗初始化数据，需要在add()之前
     *
     * @param data
     * @return
     */
    FloatingView create(List<FloatingData> data);

    /**
     * 刷新悬浮窗数据，可在add()之后
     *
     * @param data
     * @return
     */
    FloatingView refresh(FloatingData data);

}
