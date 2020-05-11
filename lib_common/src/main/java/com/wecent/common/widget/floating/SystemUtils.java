package com.wecent.common.widget.floating;

import android.content.Context;

/**
 * @desc: FloatingData
 * @author: wecent
 * @date: 2020/4/20
 */
public class SystemUtils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getScreenWidth(Context context) {
        int screenWith = -1;
        try {
            screenWith = context.getResources().getDisplayMetrics().widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenWith;
    }

    public static int getScreenHeight(Context context) {
        int screenHeight = -1;
        try {
            screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenHeight;
    }

}
