package com.avidly.adsdk.demo;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;


/**
 * Created by Holaverse on 2017/3/30.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(base);
        }
        super.attachBaseContext(base);


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
