package com.feicui.TreasureMap.user.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 16-7-14.
 *      "errcode": 1, //状态值
 "errmsg": "登录成功！",//返回信息
 "headpic": "add.jpg",          //头像地址
 "tokenid": 171//用户令牌
 */
public class LoginInfo {

    @SerializedName("errcode")
    private int code;
    @SerializedName("errmsg")
    private String msg;
    @SerializedName("headpic")
    private String iconUrl;
    @SerializedName("tokenid")
    private int tokenId;

    public int getCode() {
        return code;
    }

    public String getMsg() {

        return msg;
    }

    public String getIconUrl() {

        return iconUrl;
    }

    public int getTokenId() {

        return tokenId;
    }
}
