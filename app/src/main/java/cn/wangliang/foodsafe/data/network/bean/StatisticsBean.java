package cn.wangliang.foodsafe.data.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wangliang on 2018/2/4.
 */

public class StatisticsBean {
    @SerializedName("sample")
    List<StatisticsSampleBean> sample;

    @SerializedName("time")
    List<TimeBean> time;

    @SerializedName("top")
    private TopBean top;

    public List<StatisticsSampleBean> getSample() {
        return sample;
    }

    public void setSample(List<StatisticsSampleBean> sample) {
        this.sample = sample;
    }

    public List<TimeBean> getTime() {
        return time;
    }

    public void setTime(List<TimeBean> time) {
        this.time = time;
    }

    public TopBean getTop() {
        return top;
    }

    public void setTop(TopBean top) {
        this.top = top;
    }
}
