package com.wecent.common.widget.floating;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecent.common.R;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * @desc: 磁力吸附悬浮窗
 * @author: wecent
 * @date: 2020/4/20
 */
public class SmartFloatingView extends MagnetFloatingView {

    private final TextView mTheme;
    private final TextView mDate;
    private final ImageView mClose;
    private final TextView mCancel;
    private final TextView mConfirm;

    public SmartFloatingView(@NonNull Context context) {
        this(context, R.layout.layout_dialog_remind);
    }

    public SmartFloatingView(@NonNull Context context, @LayoutRes int resource) {
        super(context, null);
        inflate(context, resource, this);
        mTheme = findViewById(R.id.tv_theme_message);
        mDate = findViewById(R.id.tv_date_message);
        mClose = findViewById(R.id.iv_close);
        mCancel = findViewById(R.id.tv_cancel);
        mConfirm = findViewById(R.id.tv_confirm);
    }

    public void setThemeText(String txt){
        mTheme.setText(txt);
    }

    public void setDateText(String txt){
        mDate.setText(txt);
    }

    public void setCloseListener(OnClickListener listener){
        mClose.setOnClickListener(listener);
    }

    public void setCancelListener(OnClickListener listener){
        mCancel.setOnClickListener(listener);
    }

    public void setConfirmListener(OnClickListener listener){
        mConfirm.setOnClickListener(listener);
    }

}
