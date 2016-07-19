package com.feicui.TreasureMap.user;


import com.feicui.TreasureMap.user.account.Update;
import com.feicui.TreasureMap.user.account.UpdateResult;
import com.feicui.TreasureMap.user.account.UploadResult;
import com.feicui.TreasureMap.user.login.LoginInfo;
import com.feicui.TreasureMap.user.register.RegisterInfo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 将用户模块API，转为Java接口
 */
public interface UserApi {

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterInfo> register(@Body UserInfo userInfo);

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginInfo> login(@Body UserInfo userInfo);

    // 头像上传(是一个多部分请求)
    @Multipart
    @POST("/Handler/UserLoadPicHandler1.ashx")
    Call<UploadResult> upload(@Part MultipartBody.Part part);

    // 更新头像
    @POST("/Handler/UserHandler.ashx?action=update")
    Call<UpdateResult> update(@Body Update update);
}