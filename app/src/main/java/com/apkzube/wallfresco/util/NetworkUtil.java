package com.apkzube.wallfresco.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static boolean getConnectivityStatusString(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        try {
            activeNetwork = cm.getActiveNetworkInfo();
        } catch (Exception e) {
        }

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                return true;
            }
        } else {
            return false;
        }
        return false;
    }
}
