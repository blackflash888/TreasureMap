package com.feicui.TreasureMap.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 16-7-14.
 */
public class NetClient {

    private static NetClient netClient;
    private  final OkHttpClient okHttpClient;


    private NetClient(){
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static NetClient getInstance(){
        if (netClient == null){
            netClient = new NetClient();
        }
        return  netClient;
    }

    public OkHttpClient getClient(){
        return okHttpClient;
    }
}
