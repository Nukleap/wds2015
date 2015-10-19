package com.ida.manager;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;

public class ServiceTools {
    private static String TAG = ServiceTools.class.getName();
    public static boolean isServiceRunning(String serviceClassName,Context context){
        final ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
            	Log.i(TAG, "Already Started");
                return true;
            }
        }
        return false;
     }
}
