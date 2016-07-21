package com.feicui.TreasureMap.net;

import com.feicui.TreasureMap.home.TreasureApi;
import com.feicui.TreasureMap.user.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 16-7-14.
 */
public class NetClient {

    public static final String BASE_URL = "http://admin.syfeicuiedu.com";

    private static NetClient netClient;
    private  final OkHttpClient okHttpClient;
    private final Retrofit retrofit;
    private final Gson gson;
    private UserApi userApi;


    private NetClient(){
        // 非严格模式
        gson = new GsonBuilder().setLenient().create();

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                        // 添加gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static NetClient getInstance(){
        if (netClient == null){
            netClient = new NetClient();
        }
        return  netClient;
    }

    /**
     * 获取用户模型API对象
     */
    public UserApi getUserApi(){
        if(userApi == null){
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }

    private TreasureApi treasureApi;
    /**
     * 获取宝藏API对象
     */
    public TreasureApi getTreasureApi() {
        if (treasureApi == null) {
            treasureApi = retrofit.create(TreasureApi.class);
        }
        return treasureApi;
    }

    public OkHttpClient getClient(){

        return okHttpClient;
    }
}
