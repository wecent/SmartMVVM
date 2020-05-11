package com.wecent.common.bus.event;

/**
 * @desc: BaseEvent
 * @author: wecent
 * @date: 2020/5/9
 */
public class BaseEvent<T> {
    public EventType type;
    public T data;
}
