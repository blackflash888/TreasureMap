package com.feicui.TreasureMap.user;


import com.feicui.TreasureMap.user.login.LoginInfo;
import com.feicui.TreasureMap.user.register.RegisterInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 将用户模块API，转为Java接口
 */
public interface UserApi {

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterInfo> register(@Body UserInfo userInfo);

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginInfo> login(@Body UserInfo userInfo);
}