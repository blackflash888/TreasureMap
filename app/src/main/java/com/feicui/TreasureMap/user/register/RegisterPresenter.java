package com.feicui.TreasureMap.user.register;

import android.os.AsyncTask;

import com.feicui.TreasureMap.net.NetClient;
import com.feicui.TreasureMap.user.UserApi;
import com.feicui.TreasureMap.user.UserInfo;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/12 0012.
 * 注册相关业务
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private Call<RegisterInfo> registerCall;

    /** 本类核心业务*/
    public void regiser(UserInfo userInfo){
        getView().showProgress();
        UserApi userApi = NetClient.getInstance().getUserApi();
        registerCall = userApi.register(userInfo);
        registerCall.enqueue(callback);
    }

    private Callback<RegisterInfo> callback = new Callback<RegisterInfo>() {
        @Override
        public void onResponse(Call<RegisterInfo> call, Response<RegisterInfo> response) {
            getView().hideProgress();
            // 成功得到响应 (200 - 299)
            if (response != null && response.isSuccessful()) {
                final RegisterInfo Info = response.body();
                if (Info == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                // 注册成功
                if (Info.getCode() == 1) {
                    getView().navigateToHome();
                    return;
                }
                getView().showMessage(Info.getMsg());
            }
        }
        @Override
        public void onFailure(Call<RegisterInfo> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (registerCall != null) {
            registerCall.cancel();
        }
    }
}
