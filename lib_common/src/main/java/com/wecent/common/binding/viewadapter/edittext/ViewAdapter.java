package com.wecent.common.binding.viewadapter.edittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.wecent.common.binding.command.BindingCommand;

import androidx.databinding.BindingAdapter;

/**
 * @desc: EditAdapter 的 ViewAdapter
 * @author: wecent
 * @date: 2020/5/25
 */
public class ViewAdapter {

    /**
     * EditText输入文字改变的监听
     */
    @BindingAdapter(value = {"onTextChanged"}, requireAll = false)
    public static void addTextChangedListener(EditText editText, final BindingCommand<String> textChanged) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (textChanged != null) {
                    textChanged.execute(text.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
