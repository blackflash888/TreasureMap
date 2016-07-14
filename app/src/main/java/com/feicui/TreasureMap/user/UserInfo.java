package com.feicui.TreasureMap.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 16-7-14.
 *  "UserName":"qjd",
 *  " Password":"654321"
 */
public class UserInfo {

    @SerializedName("UserName")
    private String username;
    @SerializedName("Password")
    private String password;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {

        return password;
    }
}
