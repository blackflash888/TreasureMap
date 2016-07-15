package com.feicui.TreasureMap;

import android.app.Application;

import com.feicui.TreasureMap.user.UserPrefs;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class TreasureAppplication extends Application{

    @Override public void onCreate() {
        super.onCreate();
        UserPrefs.init(this);
    }
}
