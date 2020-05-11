package com.wecent.common.base;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/5/8
 */
public interface IModel {
    /**
     * ViewModel销毁时清除Model，Model层同样不能持有长生命周期对象。
     */
    void onCleared();
}
