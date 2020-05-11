package com.wecent.common.widget.floating;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/4/20
 */
public interface MagnetViewListener {

    /**
     * 关闭事件监听
     *
     * @param magnetView
     */
    void onClose(MagnetFloatingView magnetView);

    /**
     * 点击事件监听
     *
     * @param magnetView
     */
    void onCancel(MagnetFloatingView magnetView);

    /**
     * 点击事件监听
     *
     * @param magnetView
     */
    void onConfirm(MagnetFloatingView magnetView);
}
