package com.wecent.common.network;

/**
 * @desc: 网络加载状态
 * @author: wecent
 * @date: 2020/4/25
 */
public enum ApiState {
    /**
     * 默认加载
     */
    init,
    /**
     * 下拉刷新
     */
    refresh,
    /**
     * 上拉加载
     */
    loadMore
}
