package com.wecent.common.network.lifecycle;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.components.RxFragment;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

/**
 * @desc: LifecycleTransformer
 * @author: wecent
 * @date: 2020/5/9
 */
public class LifecycleTransformer {

    /**
     * 生命周期绑定
     *
     * @param activity Activity
     */
    public static <T> com.trello.rxlifecycle3.LifecycleTransformer<T> bindToLifecycle(RxAppCompatActivity activity) {
        return activity.bindToLifecycle();
    }

    /**
     * 生命周期绑定
     *
     * @param fragment Fragment
     */
    public static com.trello.rxlifecycle3.LifecycleTransformer bindToLifecycle(RxFragment fragment) {
        return fragment.bindToLifecycle();
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    public static com.trello.rxlifecycle3.LifecycleTransformer bindToLifecycle(LifecycleProvider lifecycle) {
        return lifecycle.bindToLifecycle();
    }

}
