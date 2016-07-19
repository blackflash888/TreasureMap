package com.feicui.TreasureMap.user.account;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 *
 */
public interface AccoutView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    /** 更新头像*/
    void updatePhoto(String url);
}
