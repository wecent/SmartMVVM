package com.wecent.common.binding.viewadapter.view;

import android.annotation.SuppressLint;
import android.view.View;

import com.jakewharton.rxbinding3.view.RxView;
import com.wecent.common.binding.command.BindingCommand;

import java.util.concurrent.TimeUnit;

import androidx.databinding.BindingAdapter;
import io.reactivex.functions.Consumer;

/**
 * @desc: EditAdapter 的 ViewAdapter
 * @author: wecent
 * @date: 2020/5/25
 */
public class ViewAdapter {

    private static final int CLICK_INTERVAL = 1;

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"onClick", "isThrottleFirst"}, requireAll = false)
    public static void setOnClickListener(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe((Consumer<Object>) object -> {
                        if (clickCommand != null) {
                            clickCommand.execute();
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)
                    .subscribe((Consumer<Object>) object -> {
                        if (clickCommand != null) {
                            clickCommand.execute();
                        }
                    });
        }
    }

    /**
     * view的onLongClick事件绑定
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"onLongClick"}, requireAll = false)
    public static void setOnLongClickListener(View view, final BindingCommand clickCommand) {
        RxView.longClicks(view)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        if (clickCommand != null) {
                            clickCommand.execute();
                        }
                    }
                });
    }

}
