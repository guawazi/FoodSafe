package cn.wangliang.foodsafe.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangliang on 2018/1/31.
 * 数据检测 详情页
 */

public class DetecDeatilBean {

    @SerializedName("batch")
    private String batch;

    @SerializedName("concentration")
    private String concentration;

    @SerializedName("id")
    private String id;

    @SerializedName("project_name")
    private String projectName;

    @SerializedName("sample_name")
    private String sampleName;

    @SerializedName("sample_unit")
    private String sampleUnit;

    @SerializedName("standard")
    private String standard;

    @SerializedName("test_result")
    private String testResult;

    @SerializedName("test_time")
    private long testTime;

    @SerializedName("test_value")
    private String testValue;

    @SerializedName("type")
    private String type;

    @SerializedName("carno")
    private String carno;

    @SerializedName("dst_market")
    private String dstMarket;

    @SerializedName("devicename")
    private String devicename;

    @SerializedName("test_member")
    private String testMember;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleUnit() {
        return sampleUnit;
    }

    public void setSampleUnit(String sampleUnit) {
        this.sampleUnit = sampleUnit;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public long getTestTime() {
        return testTime;
    }

    public void setTestTime(long testTime) {
        this.testTime = testTime;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getDstMarket() {
        return dstMarket;
    }

    public void setDstMarket(String dstMarket) {
        this.dstMarket = dstMarket;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getTestMember() {
        return testMember;
    }

    public void setTestMember(String testMember) {
        this.testMember = testMember;
    }
}
