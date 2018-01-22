package cn.wangliang.foodsafe;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cn.wangliang.foodsafe.data.DatabaseManager;


/**
 * Application
 * extends MultiDexApplication and override
 * {@link MultiDexApplication#attachBaseContext}
 * function for support MultiDex
 * <p>
 */

public class MyApplication extends MultiDexApplication {

    private static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        init();
    }

    /**
     * init Method.
     */
    private void init() {
        //Logger init
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("base")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        DatabaseManager.initialize(this);

        // Umeng
//        UMConfigure.init(this, "", "", UMConfigure.DEVICE_TYPE_PHONE, null);
//        UMConfigure.setLogEnabled(true);
//        UMConfigure.setEncryptEnabled(true);
    }

    //for multidex
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return app;
    }

}
