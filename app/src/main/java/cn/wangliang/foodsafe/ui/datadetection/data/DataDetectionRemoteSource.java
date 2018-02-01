package cn.wangliang.foodsafe.ui.datadetection.data;

import java.util.List;

import cn.wangliang.foodsafe.data.network.ApiService;
import cn.wangliang.foodsafe.data.network.RxFlowable;
import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;
import io.reactivex.Flowable;

/**
 * Created by wangliang on 2018/1/28.
 */

public class DataDetectionRemoteSource {

    public Flowable<List<DataDetectionBean>> getData(int page, String userid, String deviceid, String projectName, String sampleName, String carNO, String dstMarket,int result,long starttime,long endtime) {
        return ApiService.getInstance()
                .getApi()
                .getDataDetectionData(page, userid, deviceid, projectName, sampleName, carNO, dstMarket,result,starttime,endtime)
                .compose(RxFlowable.handleResult());
    }
}
