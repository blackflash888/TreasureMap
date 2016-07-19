package com.feicui.TreasureMap.user.login;


import com.feicui.TreasureMap.net.NetClient;
import com.feicui.TreasureMap.user.UserApi;
import com.feicui.TreasureMap.user.UserInfo;
import com.feicui.TreasureMap.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * 登陆视图业务
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call<LoginInfo> loginCall;

    /**
     * 本类核心业务
     */
    public void login(UserInfo userInfo) {
        getView().showProgress();
        UserApi userApi = NetClient.getInstance().getUserApi();
        if (loginCall != null) loginCall.cancel();
        // 执行登陆请求构建出call模型
        loginCall = userApi.login(userInfo);
        // Call模型异步执行
        loginCall.enqueue(callback);
    }

    private Callback<LoginInfo> callback = new Callback<LoginInfo>() {
        @Override public void onResponse(Call<LoginInfo> call, retrofit2.Response<LoginInfo> response) {
            getView().hideProgress();
            // 是否成功
            if (response.isSuccessful()) {
                LoginInfo Info = response.body();
                if (Info == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                getView().showMessage(Info.getMsg());
                // 登陆成功
                if (Info.getCode() == 1) {
                    UserPrefs.getInstance().setPhoto(NetClient.BASE_URL + Info.getIconUrl());
                    UserPrefs.getInstance().setTokenid(Info.getTokenId());
                    getView().navigateToHome();
                }
                return;
            }
        }

        @Override public void onFailure(Call<LoginInfo> call, Throwable throwable) {
            getView().hideProgress();
            getView().showMessage(throwable.getMessage());
        }
    };

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (loginCall != null){
            loginCall.cancel();
        }
    }
}