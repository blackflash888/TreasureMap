package com.feicui.TreasureMap.user.account;

import com.feicui.TreasureMap.net.NetClient;
import com.feicui.TreasureMap.user.UserApi;
import com.feicui.TreasureMap.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 个人信息页面业务处理
 * 主要做了头像更新业务：先做头像上传处理,再做头像更新处理
 */
public class AccountPresenter extends MvpNullObjectBasePresenter<AccoutView> {

    private Call<UploadResult> uploadCall; // 头像上传call
    private Call<UpdateResult> updateCall; // 关像更新call

    /**
     * 上传头像
     */
    public void uploadPhoto(File file) {
        getView().showProgress();
        UserApi userApi = NetClient.getInstance().getUserApi();
        // 构建“部分”
        RequestBody body = RequestBody.create(null, file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", "photo.png", body);
        // 上传头像
        if (uploadCall != null) uploadCall.cancel();
        uploadCall = userApi.upload(part);
        uploadCall.enqueue(upLoadCallback);
    }

    /**
     * 上传头像的callback
     */
    private Callback<UploadResult> upLoadCallback = new Callback<UploadResult>() {
        @Override public void onFailure(Call<UploadResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }

        @Override public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
            if (response != null && response.isSuccessful()) {// 成功响应
                // 取得响应体内数据，结果
                UploadResult result = response.body();
                if (result == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                getView().showMessage(result.getMsg());
                if (result.getCount() != 1) { // 上传不成功
                    return;
                }
                // 上传成功 , 取出结果内的头像地址
                String photoUrl = result.getUrl(); // 上传后的，头像URL地址
                UserPrefs.getInstance().setPhoto(NetClient.BASE_URL + photoUrl);
                getView().updatePhoto(NetClient.BASE_URL + photoUrl);// 视图更新头像
                // 用户头像
                String photoName = photoUrl.substring(photoUrl.lastIndexOf("/") + 1, photoUrl.length());
                // 用户token
                int tokenId = UserPrefs.getInstance().getTokenid();
                // 头像更新
                UserApi userApi = NetClient.getInstance().getUserApi();
                if (updateCall != null) updateCall.cancel();
                updateCall = userApi.update(new Update(tokenId, photoName));
                updateCall.enqueue(updateCallback);
            }
        }
    };

    // 更新头像callback
    private Callback<UpdateResult> updateCallback = new Callback<UpdateResult>() {
        @Override public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
            getView().hideProgress();
            if (response != null && response.isSuccessful()) {
                // 取出当前“更新”响应结果
                UpdateResult result = response.body();
                if (result == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                getView().showMessage(result.getMsg());
                if (result.getCode() != 1) {
                    return;
                }
            }
        }

        @Override public void onFailure(Call<UpdateResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (uploadCall != null) uploadCall.cancel();
        if (updateCall != null) updateCall.cancel();
    }
}