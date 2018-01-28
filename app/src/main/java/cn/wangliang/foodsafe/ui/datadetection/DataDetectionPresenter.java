package cn.wangliang.foodsafe.ui.datadetection;

import java.util.List;

import cn.wangliang.foodsafe.data.network.RxFlowable;
import cn.wangliang.foodsafe.data.network.RxSubscriber;
import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;
import cn.wangliang.foodsafe.ui.datadetection.data.DataDetectionRepository;

/**
 * Created by wangliang on 2018/1/28.
 */

public class DataDetectionPresenter implements DataDetectionContract.DataDetectionPresenter {

    private final DataDetectionRepository mDataDetectionRepository;
    private DataDetectionContract.DataDetectionView mView;
    private String mDeviceid;
    private String mProjectName;
    private String mSampleName;
    private String mCarNO;
    private String mDstMarket;
    private int mPage;

    public DataDetectionPresenter() {
        mDataDetectionRepository = DataDetectionRepository.newInstance();
    }

    @Override
    public void attachView(DataDetectionContract.DataDetectionView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getData(String deviceid, String projectName, String sampleName, String carNO, String dstMarket) {
        mPage = 0;
        mDeviceid = deviceid;
        mProjectName = projectName;
        mSampleName = sampleName;
        mCarNO = carNO;
        mDstMarket = dstMarket;
        mDataDetectionRepository.getData(mPage, "201801250355443050", mDeviceid, mProjectName, mSampleName, mCarNO, mDstMarket)
                .compose(RxFlowable.io_main())
                .subscribe(new RxSubscriber<List<DataDetectionBean>>(mView) {
                    @Override
                    public void onSuccess(List<DataDetectionBean> beans) {
                        mView.showContent(beans);
                    }
                });
    }

    @Override
    public void getDataMore() {
        mDataDetectionRepository.getData(++mPage,"201801250355443050", mDeviceid, mProjectName, mSampleName, mCarNO, mDstMarket)
                .compose(RxFlowable.io_main())
                .subscribe(new RxSubscriber<List<DataDetectionBean>>(mView) {
                    @Override
                    public void onSuccess(List<DataDetectionBean> beans) {
                        mView.showContentMore(beans);
                    }
                });
    }
}
