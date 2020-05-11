package com.wecent.common.network.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.wecent.common.R;
import com.wecent.common.utils.NetworkUtils;
import com.wecent.common.utils.ToastUtils;

import java.util.Objects;

/**
 * @desc: 网络变化监听接收者
 * @author: wecent
 * @date: 2020/4/12
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (!NetworkUtils.isConnected()) {
                ToastUtils.showShort(context.getString(R.string.network_disconnect));
            }
        }
    }

}
