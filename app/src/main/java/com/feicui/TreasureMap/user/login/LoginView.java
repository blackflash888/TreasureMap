package com.feicui.TreasureMap.user.login;

/**
 * Created by Administrator on 16-7-12.
 */
public interface LoginView {

    //显示进度条
    void showProgressBar();
    //隐藏进度条
    void hideProgressBar();
    //显示信息
    void showMessage(String msg);
    //跳转到主界面
    void navigateToHome();
}
