package com.wecent.common.binding.viewadapter.titlebar;

import com.wecent.common.binding.command.BindingCommand;
import com.wecent.common.widget.titlebar.TitleBar;

import androidx.databinding.BindingAdapter;

/**
 * @desc: EditAdapter 的 ViewAdapter
 * @author: wecent
 * @date: 2020/5/25
 */
public class ViewAdapter {

    @BindingAdapter(value = {"leftDrawable"}, requireAll = false)
    public static void setLeftDrawable(TitleBar titleBar, final int drawable) {
        titleBar.setLeftDrawable(drawable);
    }

    @BindingAdapter(value = {"onLeftClick"}, requireAll = false)
    public static void setOnLeftClickListener(TitleBar titleBar, final BindingCommand bindingCommand) {
        titleBar.setOnLeftClickListener(v -> bindingCommand.execute());
    }

    @BindingAdapter(value = {"titleText"}, requireAll = false)
    public static void setTitleText(TitleBar titleBar, final String string) {
        titleBar.setTitleText(string);
    }

    @BindingAdapter(value = {"onRightClick"}, requireAll = false)
    public static void setOnRightClickListener(TitleBar titleBar, final BindingCommand bindingCommand) {
        titleBar.setOnRightClickListener(v -> bindingCommand.execute());
    }
}
