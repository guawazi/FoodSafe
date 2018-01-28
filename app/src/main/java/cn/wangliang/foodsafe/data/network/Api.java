package cn.wangliang.foodsafe.data.network;

import java.util.List;

import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;
import cn.wangliang.foodsafe.data.network.bean.LoginBean;
import cn.wangliang.foodsafe.data.network.bean.ResultBean;
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
            @Field("dst_market") String dstMarket // 送往市场（不查询传空字符串，非NULL）
    );
}
