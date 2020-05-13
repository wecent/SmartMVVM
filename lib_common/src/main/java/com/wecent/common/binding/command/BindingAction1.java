package com.wecent.common.binding.command;

/**
 * @desc: 没有参数的动作绑定
 * @author: wecent
 * @date: 2020/5/8
 */
public interface BindingAction1<T> {
    /**
     * A one-argument action.
     *
     * @param t
     */
    void call(T t);
}
