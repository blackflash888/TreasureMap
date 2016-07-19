package com.feicui.TreasureMap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.feicui.TreasureMap.user.UserPrefs;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class TreasureAppplication extends Application{

    @Override public void onCreate() {
        super.onCreate();
        //初始化
        UserPrefs.init(this);
        initImageLoader();
        //百度地图初始化
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initImageLoader() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_icon)
                .showImageOnFail(R.drawable.user_icon)
                .showImageForEmptyUri(R.drawable.user_icon)
                        // .displayer(new RoundedBitmapDisplayer(getResources().getDimensionPixelOffset(R.dimen.dp_10)))
                .cacheOnDisk(true)  // 打开disk
                .cacheInMemory(true) // 打开cache
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(10*1024*1024) // 设置内存缓存5MB
                .defaultDisplayImageOptions(options)// 设置默认的显示选项
                .build();

        ImageLoader.getInstance().init(config);
    }
}
