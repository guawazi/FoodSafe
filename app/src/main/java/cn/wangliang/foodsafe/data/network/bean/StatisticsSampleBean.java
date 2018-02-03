package cn.wangliang.foodsafe.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangliang on 2018/2/4.
 */

public class StatisticsSampleBean {
    @SerializedName("all")
    private int all;

    @SerializedName("sampleName")
    private String sampleName;

    @SerializedName("sampleid")
    private String sampleid;

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

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
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
