package cn.wangliang.foodsafe.ui.datadetection;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.mvp.MvpFragment;
import cn.wangliang.foodsafe.data.network.ApiService;
import cn.wangliang.foodsafe.data.network.RxFlowable;
import cn.wangliang.foodsafe.data.network.RxSimpleSubscriber;
import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;
import cn.wangliang.foodsafe.data.network.bean.SampleNameBean;
import cn.wangliang.foodsafe.ui.detecdetail.DetecDetailActivity;
import cn.wangliang.foodsafe.util.CommonUtils;
import cn.wangliang.foodsafe.util.Constant;
import cn.wangliang.foodsafe.util.SPUtils;

/**
 * Created by wangliang on 2018/1/22.
 * 检测数据页面
 */

public class DataDetectionFragment extends MvpFragment<DataDetectionContract.DataDetectionPresenter> implements DataDetectionContract.DataDetectionView {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.spinner_unit)
    Spinner mSpinnerUnit;

    private DataDetectionAdapter mDataDetectionAdapter;
    private View mAlldataHeader;
    private View mSearchHeader;
    private EditText mEtDeviceid;
    private EditText mEtSampleName;
    private EditText mEtDstMarket;
    private EditText mEtProjectName;
    private EditText mEtCarNo;
    private String mDeviceid;
    private String mSampleName;
    private String mDstMarket;
    private String mProjectName;
    private String mCarNo;

    @Override
    protected int getLayout() {
        return R.layout.fragment_data_detection;
    }

    @Override
    protected void initEventAndData() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataDetectionAdapter = new DataDetectionAdapter(R.layout.item_data_detetion);
        mRecycler.setAdapter(mDataDetectionAdapter);
        mAlldataHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_alldata_header, mRecycler, false);
        mSearchHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_search_header, mRecycler, false);
        mDataDetectionAdapter.addHeaderView(mSearchHeader);
        mDataDetectionAdapter.addHeaderView(mAlldataHeader);

        mEtDeviceid = mSearchHeader.findViewById(R.id.et_deviceid);
        mEtSampleName = mSearchHeader.findViewById(R.id.et_sample_name);
        mEtDstMarket = mSearchHeader.findViewById(R.id.et_dst_market);
        mEtProjectName = mSearchHeader.findViewById(R.id.et_project_name);
        mEtCarNo = mSearchHeader.findViewById(R.id.et_car_no);

        checkSelectCondition();
        mPresenter.getData(mDeviceid, mProjectName, mSampleName, mCarNo, mDstMarket);


        ApiService.getInstance()
                .getApi()
                .getSampleNameList(SPUtils.getString(Constant.LOGIN_USERID, ""))
                .compose(RxFlowable.handleResult())
                .compose(RxFlowable.io_main())
                .subscribe(new RxSimpleSubscriber<List<SampleNameBean>>() {
                    @Override
                    public void onSuccess(List<SampleNameBean> bean) {

                    }
                });

        mSearchHeader.findViewById(R.id.tv_reset).setOnClickListener(v -> {
            mEtDeviceid.setText("");
            mEtSampleName.setText("");
            mEtDstMarket.setText("");
            mEtProjectName.setText("");
            mEtCarNo.setText("");
        });
        mSearchHeader.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            checkSelectCondition();
            mPresenter.getData(mDeviceid, mProjectName, mSampleName, mCarNo, mDstMarket);
        });

        mDataDetectionAdapter.setOnLoadMoreListener(() -> {
            if (mPresenter != null) {
                mPresenter.getDataMore();
            }
        }, mRecycler);

        mDataDetectionAdapter.setOnItemClickListener((adapter, view, position) -> {
            DataDetectionBean dataDetectionBean = mDataDetectionAdapter.getData().get(position);
            DetecDetailActivity.actionActivity(getActivity(), dataDetectionBean.getId());
        });


//        initSpinner();

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            strings.add("这是" + i);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getContext(), R.layout.item_spinner, strings);
        mSpinnerUnit.setAdapter(spinnerAdapter);
        mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = strings.get(position);
                CommonUtils.showToastShort("点击了" + s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkSelectCondition() {
        mDeviceid = mEtDeviceid.getText().toString().trim();
        if (TextUtils.isEmpty(mDeviceid)) {
            mDeviceid = "0";
        }
        mSampleName = mEtSampleName.getText().toString().trim();
        mDstMarket = mEtDstMarket.getText().toString().trim();
        mProjectName = mEtProjectName.getText().toString().trim();
        mCarNo = mEtCarNo.getText().toString().trim();
    }

    public static DataDetectionFragment newInstance() {
        return new DataDetectionFragment();
    }


    @Override
    public void showContent(List<DataDetectionBean> dataDetectionBeans) {
        mDataDetectionAdapter.getData().clear();
        mDataDetectionAdapter.addData(dataDetectionBeans);
    }

    @Override
    public void showContentMore(List<DataDetectionBean> dataDetectionBean) {
        if (dataDetectionBean != null && dataDetectionBean.size() > 0) {
            mDataDetectionAdapter.addData(dataDetectionBean);
            if (mDataDetectionAdapter != null) {
                mDataDetectionAdapter.loadMoreComplete();
            }
        } else {
            if (mDataDetectionAdapter != null) {
                mDataDetectionAdapter.loadMoreEnd();
            }
        }

    }

    @Override
    public void showError(String errorMsg) {
        super.showError(errorMsg);
        if (mDataDetectionAdapter != null) {
            mDataDetectionAdapter.loadMoreFail();
        }
    }

    @Override
    protected DataDetectionPresenter initInject() {
        return new DataDetectionPresenter();
    }
}
