package cn.wangliang.foodsafe.data.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangliang on 2018/1/28.
 */

public class DataDetectionBean {

    // 生产批次
    @SerializedName("batch")
    private String batch;

    // 浓度
    @SerializedName("concentration")
    private String concentration;

    @SerializedName("id")
    private String id;

    //项目名称
    @SerializedName("project_name")
    private String projectName;

    // 样品名称
    @SerializedName("sample_name")
    private String sampleName;

    // 样品单位
    @SerializedName("sample_unit")
    private String sampleUnit;

    // 合格标准
    @SerializedName("standard")
    private String standard;

    // 检测结果 1 疑似阳性 2阴性
    @SerializedName("test_result")
    private String testResult;

    // 检查时间 long时间戳
    @SerializedName("test_time")
    private long testTime;

    // 检测值
    @SerializedName("test_value")
    private String testValue;

    //
    @SerializedName("type")
    private String type;

    @SerializedName("test_member")
    private String testMember;

    @SerializedName("dst_market")
    private String dstMarket;

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

    public String getTestMember() {
        return testMember;
    }

    public void setTestMember(String testMember) {
        this.testMember = testMember;
    }

    public String getDstMarket() {
        return dstMarket;
    }

    public void setDstMarket(String dstMarket) {
        this.dstMarket = dstMarket;
    }
}
