package cn.wangliang.foodsafe.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangliang on 2018/2/4.
 */

public class DeviceBean {
    @SerializedName("devicename")
    private String devicename;

    @SerializedName("id")
    private String id;

    @SerializedName("model")
    private String model;

    @SerializedName("testcount")
    private String testcount;

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTestcount() {
        return testcount;
    }

    public void setTestcount(String testcount) {
        this.testcount = testcount;
    }
}
