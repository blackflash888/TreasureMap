package com.feicui.TreasureMap.home.map;

import com.feicui.TreasureMap.home.Treasure;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;


public interface MapMvpView extends MvpView{

    void showMessage(String msg);

    void setData(List<Treasure> datas);
}
