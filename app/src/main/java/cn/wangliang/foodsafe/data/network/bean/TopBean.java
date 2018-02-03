package cn.wangliang.foodsafe.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangliang on 2018/2/4.
 */

public class TopBean {
    @SerializedName("all")
    private int all;

    @SerializedName("yang")
    private int yang;

    @SerializedName("yin")
    private int yin;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getYang() {
        return yang;
    }

    public void setYang(int yang) {
        this.yang = yang;
    }

    public int getYin() {
        return yin;
    }

    public void setYin(int yin) {
        this.yin = yin;
    }
}
