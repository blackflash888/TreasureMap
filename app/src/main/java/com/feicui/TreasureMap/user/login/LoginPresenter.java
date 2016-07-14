package com.feicui.TreasureMap.user.login;

import android.os.AsyncTask;

import com.feicui.TreasureMap.net.NetClient;
import com.feicui.TreasureMap.user.UserInfo;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/12 0012.
 *
 * 登陆视图业务
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private UserInfo userInfo;
    private Gson gson;
    private Call call;

    /** 本类核心业务*/
    public void login(UserInfo userInfo){
        this.userInfo = userInfo;
        gson = new Gson();
        new LoginTask().execute();
    }

    private final class LoginTask extends AsyncTask<Void,Void,LoginInfo>{
        // 在doInBackground之前,UI线程来调用
        @Override protected void onPreExecute() {
            super.onPreExecute();
            getView().showProgress();
        }
        // 在onPreExecute之后, 后台线程来调用
        @Override protected LoginInfo doInBackground(Void... params) {
            OkHttpClient okHttpClient = NetClient.getInstance().getClient();
            String content = gson.toJson(userInfo);
            MediaType mediaType = MediaType.parse("treasuremap/json");
            // 请求体
            RequestBody body = RequestBody.create(mediaType, content);
            //请求
            Request request = new Request.Builder()
                    .url("http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=login")
                    .post(body)
                    .build();
            if (call != null) call.cancel();
            call = okHttpClient.newCall(request);
            try {
                // 执行（同步）得到响应
                Response response = call.execute();
                if(response == null){
                    return null;
                }
                if (response.isSuccessful()){
                    // 取出响应体中的数据
                    String strResult = response.body().string();
                    LoginInfo loginInfo = gson.fromJson(strResult, LoginInfo.class);
                    return loginInfo;
                }
            } catch (IOException e) {
                return null;
            }

            return null;
        }
        // 在doInBackground之后,UI线程来调用
        @Override protected void onPostExecute(LoginInfo loginInfo) {
            super.onPostExecute(loginInfo);
            getView().hideProgress();
            if (loginInfo == null){
                getView().showMessage("登录异常");
                return;
            }
            getView().showMessage(loginInfo.getMsg());
            //登录成功
            if (loginInfo.getCode() == 1){
                ///登录成功进入到主页面
                getView().navigateToHome();
            }
        }
    }
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null){
            call.cancel();
        }
    }
}