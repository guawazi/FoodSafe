package cn.wangliang.foodsafe.ui.datadetection.data;

import java.util.List;

import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;
import io.reactivex.Flowable;

/**
 * Created by wangliang on 2018/1/28.
 */

public class DataDetectionRepository {
    private static volatile DataDetectionRepository INSTANCE;
    private final DataDetectionRemoteSource mDataDetectionRemoteSource;


    private DataDetectionRepository() {
        mDataDetectionRemoteSource = new DataDetectionRemoteSource();
    }

    public static DataDetectionRepository newInstance() {
        if (INSTANCE == null) {
            synchronized (DataDetectionRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataDetectionRepository();
                }
            }
        }
        return INSTANCE;
    }


    public Flowable<List<DataDetectionBean>> getData(int page, String userid, String deviceid, String projectName, String sampleName, String carNO, String dstMarket) {
        return mDataDetectionRemoteSource.getData(
                page,
                userid,
                deviceid,
                projectName,
                sampleName,
                carNO,
                dstMarket
        );
    }


}
