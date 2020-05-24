package com.wecent.common.binding.viewadapter.checkbox;

import android.widget.CheckBox;

import com.wecent.common.binding.command.BindingCommand;

import androidx.databinding.BindingAdapter;

/**
 * @desc: CheckBox 的 ViewAdapter
 * @author: wecent
 * @date: 2020/5/25
 */
public class ViewAdapter {
    /**
     * @param bindingCommand //绑定监听
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"onCheckedChanged"}, requireAll = false)
    public static void setCheckedChanged(final CheckBox checkBox, final BindingCommand<Boolean> bindingCommand) {
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> bindingCommand.execute(b));
    }
}
