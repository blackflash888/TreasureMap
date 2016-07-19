package com.feicui.TreasureMap.user.account;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Update {
//    {
//        "Tokenid":3,"
//        "HeadPic": "05a1a7e18ab940679dbd0e506be31add.jpg"
//    }

    @SerializedName("Tokenid")
    private int tokenId;

    @SerializedName("HeadPic")
    public String photoUrl;

    public Update(int tokenId, String photoUrl) {
        this.tokenId = tokenId;
        this.photoUrl = photoUrl;
    }
}
