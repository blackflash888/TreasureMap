package com.feicui.TreasureMap.home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：yuanchao on 2016/7/19 0019 10:46
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface TreasureApi {

    @POST("/Handler/TreasureHandler.ashx?action=show")
    Call<List<Treasure>> getTreasureInArea(@Body Area body);
}
