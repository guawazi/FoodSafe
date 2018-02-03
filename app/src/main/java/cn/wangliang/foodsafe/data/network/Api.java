package cn.wangliang.foodsafe.data.network;

import java.util.List;

import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;
import cn.wangliang.foodsafe.data.network.bean.DetecDeatilBean;
import cn.wangliang.foodsafe.data.network.bean.DeviceBean;
import cn.wangliang.foodsafe.data.network.bean.LoginBean;
import cn.wangliang.foodsafe.data.network.bean.MarketBean;
import cn.wangliang.foodsafe.data.network.bean.ResultBean;
import cn.wangliang.foodsafe.data.network.bean.SampleNameBean;
import cn.wangliang.foodsafe.data.network.bean.StatisticsBean;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("memberAPI/login")
    @FormUrlEncoded
    Flowable<ResultBean<LoginBean>> login(@Field("username") String username, @Field("password") String password);

    @POST("testresultAPI/getresultlist")
    @FormUrlEncoded
    Flowable<ResultBean<List<DataDetectionBean>>> getDataDetectionData(
            @Field("page") int page, // 页码（从0开始）
            @Field("userid") String userid, //登录用户ID
            @Field("deviceid") String deviceid, //设备id（不查询传0）
            @Field("projectName") String projectName, //项目名称（不查询传空字符串，非NULL）
            @Field("sampleName") String sampleName, //样品名称（不查询传空字符串，非NULL）
            @Field("carNO") String carNO, // 车牌号（不查询传空字符串，非NULL）
            @Field("dst_market") String dstMarket, // 送往市场（不查询传空字符串，非NULL）
            @Field("result") int result, // 检测结果 0所有 1 疑似阳性 2阴性
            @Field("starttime") long starttime, // 开始查询时间  0默认所有 Data().getTime()时间戳
            @Field("endtime") long endtime,
            @Field("marketid") String marketid
    );

    @POST("memberAPI/getmembers")
    @FormUrlEncoded
    Flowable<ResultBean<List<SampleNameBean>>> getSampleNameList(@Field("userid") String userid);

    // 检测详情
    @POST("testresultAPI/getresultdetail")
    @FormUrlEncoded
    Flowable<ResultBean<DetecDeatilBean>> getDeteDetailData(@Field("id") String id);

    // 获取样品单位
    @POST("marketAPI/getmarket")
    @FormUrlEncoded
    Flowable<ResultBean<List<MarketBean>>> getMarket(@Field("userid") String userid);

    // 统计页面
    @POST("testresultAPI/statistics")
    @FormUrlEncoded
    Flowable<ResultBean<StatisticsBean>> getStatistics(@Field("userid") String userid,
                                                             @Field("year") String year);

    // 设备列表
    @POST("deviceAPI/getdevice")
    @FormUrlEncoded
    Flowable<ResultBean<List<DeviceBean>>> getdevice(@Field("userid") String userid);

    // 修改密码
    @POST("deviceAPI/changepass")
    @FormUrlEncoded
    Flowable<ResultBean> changepass(@Field("userid") String userid);

}
