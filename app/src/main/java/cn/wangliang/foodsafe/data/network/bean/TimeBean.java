package cn.wangliang.foodsafe.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangliang on 2018/2/4.
 */

public class TimeBean {
    @SerializedName("all")
    private int all;

    @SerializedName("month")
    private int month;

    @SerializedName("yang")
    private int yang;

    @SerializedName("yin")
    private int yin;

    @SerializedName("year")
    private String year;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
