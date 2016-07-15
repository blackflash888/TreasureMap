package com.feicui.TreasureMap.user.register;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 16-7-14.
 */
public class RegisterInfo {

    @SerializedName("tokenid")
    private int tokenId;

    @SerializedName("errcode")
    private int code;

    @SerializedName("errmsg")
    private String msg;

    public int getTokenId() {

        return tokenId;
    }

    public int getCode() {

        return code;
    }

    public String getMsg() {

        return msg;
    }

}
