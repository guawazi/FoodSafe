package cn.wangliang.foodsafe.ui.datadetection;

import java.util.List;

import cn.wangliang.foodsafe.base.mvp.MvpPresenter;
import cn.wangliang.foodsafe.base.mvp.MvpView;
import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;

/**
 * Created by wangliang on 2018/1/28.
 */

public class DataDetectionContract {

    public interface DataDetectionView extends MvpView {

        void showContent(List<DataDetectionBean> dataDetectionBean);

        void showContentMore(List<DataDetectionBean> dataDetectionBean);
    }

    public interface DataDetectionPresenter extends MvpPresenter<DataDetectionView> {
        void getData(String userId,String deviceid, String projectName, String sampleName, String carNO, String dstMarket,int result,long starttime,long endtime,String marketid);

        void getDataMore();
    }
}
