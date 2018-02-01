package cn.wangliang.foodsafe.ui.datadetection;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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
import cn.wangliang.foodsafe.util.TimeUtils;

/**
 * Created by wangliang on 2018/1/22.
 * 检测数据页面
 */

public class DataDetectionFragment extends MvpFragment<DataDetectionContract.DataDetectionPresenter> implements DataDetectionContract.DataDetectionView {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;


    @BindView(R.id.tv_start_data)
    TextView mTvStartData;

    @BindView(R.id.tv_end_data)
    TextView mTvEndData;

    @BindView(R.id.tv_type_result)
    TextView mTvTypeResult;

    @BindView(R.id.tv_sample_name)
    TextView mTvSampleName;


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
    private Calendar mCalendar;
    private TimePickerView mStartTimePickerView;
    private TimePickerView mEndTimePickerView;
    private PopupWindow mTypeResultPopupWindow;
    private PopupWindow mSampleNamePopupWindow;
    private BaseQuickAdapter<String, BaseViewHolder> mTypeResultAdapter;
    private ArrayList<String> mTypeResultList;
    private BaseQuickAdapter<SampleNameBean, BaseViewHolder> mSampleNameAdapter;

    private int mTypeResult = 0;
    // !!! 这不是真正的 uid
    private String mUserId = SPUtils.getString(Constant.LOGIN_USERID);

    private long mStartTime = 0;
    private long mEndTime = 0;

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
        mPresenter.getData(mUserId, mDeviceid, mProjectName, mSampleName, mCarNo, mDstMarket, mTypeResult, mStartTime, mEndTime);

        mTypeResultList = new ArrayList<>();
        mTypeResultList.add("阳性");
        mTypeResultList.add("阴性");

        mSearchHeader.findViewById(R.id.tv_reset).setOnClickListener(v -> {
            mEtDeviceid.setText("");
            mEtSampleName.setText("");
            mEtDstMarket.setText("");
            mEtProjectName.setText("");
            mEtCarNo.setText("");
        });
        mSearchHeader.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            checkSelectCondition();
            mPresenter.getData(mUserId, mDeviceid, mProjectName, mSampleName, mCarNo, mDstMarket, mTypeResult, mStartTime, mEndTime);
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

        mStartTimePickerView = new TimePickerView.Builder(getContext(), (date, v) -> {
            mStartTime = date.getTime();
            String format = TimeUtils.format2(date.getTime());
            mTvStartData.setText(format);
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setTitleText("开始时间")
                .build();
        mStartTimePickerView.setDate(Calendar.getInstance());

        mEndTimePickerView = new TimePickerView.Builder(getContext(), (date, v) -> {
            mEndTime = date.getTime();
            String format = TimeUtils.format2(date.getTime());
            mTvEndData.setText(format);
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setTitleText("结束时间")
                .build();
        mEndTimePickerView.setDate(Calendar.getInstance());

        initSampleWindow();
        initDataTypeWindow();
        ApiService.getInstance()
                .getApi()
                .getSampleNameList(SPUtils.getString(Constant.LOGIN_USERID, ""))
                .compose(RxFlowable.handleResult())
                .compose(RxFlowable.io_main())
                .subscribe(new RxSimpleSubscriber<List<SampleNameBean>>() {
                    @Override
                    public void onSuccess(List<SampleNameBean> bean) {
                        mSampleNameAdapter.setNewData(bean);
                    }
                });
    }

    private void initSampleWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.item_popwindow, null);
        mSampleNamePopupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSampleNameAdapter = new BaseQuickAdapter<SampleNameBean, BaseViewHolder>(R.layout.item_popwindow_recycler) {
            @Override
            protected void convert(BaseViewHolder helper, SampleNameBean item) {
                helper.setText(R.id.tv_item, item.getRealname());
            }
        };

        mSampleNameAdapter.setOnItemClickListener((adapter, view, position) -> {
            SampleNameBean sampleNameBean = mSampleNameAdapter.getData().get(position);
            mTvSampleName.setText(sampleNameBean.getRealname());
            mUserId = sampleNameBean.getId();
            mSampleNamePopupWindow.dismiss();
            mPresenter.getData(mUserId, mDeviceid, mProjectName, mSampleName, mCarNo, mDstMarket, mTypeResult, mStartTime, mEndTime);
        });

        mSampleNameAdapter.bindToRecyclerView(recyclerView);
    }

    private void initDataTypeWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.item_popwindow, null);
        mTypeResultPopupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTypeResultAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_popwindow_recycler) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_item, item);
            }
        };
        mTypeResultAdapter.setOnItemClickListener((adapter, view, position) -> {
            mTvTypeResult.setText(mTypeResultAdapter.getData().get(position));
            mTypeResult = position + 1;
            mTypeResultPopupWindow.dismiss();
            mPresenter.getData(mUserId, mDeviceid, mProjectName, mSampleName, mCarNo, mDstMarket, mTypeResult, mStartTime, mEndTime);
        });
        mTypeResultAdapter.bindToRecyclerView(recyclerView);
        mTypeResultAdapter.setNewData(mTypeResultList);
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
        if (dataDetectionBeans == null || dataDetectionBeans.size() < 1) {
            CommonUtils.showToastShort("没有搜索到数据");
        }
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

    @OnClick({R.id.tv_start_data, R.id.tv_end_data, R.id.tv_type_result, R.id.tv_sample_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_data:
                mStartTimePickerView.show();
                break;
            case R.id.tv_end_data:
                mEndTimePickerView.show();
                break;
            case R.id.tv_type_result:
                mTypeResultPopupWindow.showAsDropDown(mTvTypeResult);
                break;
            case R.id.tv_sample_name:
                mSampleNamePopupWindow.showAsDropDown(mTvSampleName);
                break;
        }
    }

    @Override
    protected DataDetectionPresenter initInject() {
        return new DataDetectionPresenter();
    }

}
