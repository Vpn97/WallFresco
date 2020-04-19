package com.apkzube.wallfresco.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class NetworkReceiver extends BroadcastReceiver {

    private BroadcastListener listener;

    public NetworkReceiver(BroadcastListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Boolean status = NetworkUtil.getConnectivityStatusString(context);
        if (!status) {

            listener.updateUI(false);
        }else {

            listener.updateUI(true);
        }
    }
}
