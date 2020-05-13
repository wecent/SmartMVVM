package com.wecent.common.binding.command;

/**
 * @desc: 没有参数的函数
 * @author: wecent
 * @date: 2020/5/8
 */
public interface BindingFunction0<T> {
    /**
     * Represents a function with zero arguments.
     *
     * @return T
     */
    T call();
}
