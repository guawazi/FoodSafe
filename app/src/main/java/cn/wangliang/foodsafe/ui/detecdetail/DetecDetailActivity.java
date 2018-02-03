package cn.wangliang.foodsafe.ui.detecdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseActivity;
import cn.wangliang.foodsafe.data.network.ApiService;
import cn.wangliang.foodsafe.data.network.RxFlowable;
import cn.wangliang.foodsafe.data.network.RxSimpleSubscriber;
import cn.wangliang.foodsafe.data.network.bean.DetecDeatilBean;
import cn.wangliang.foodsafe.util.CommonUtils;
import cn.wangliang.foodsafe.util.TimeUtils;

public class DetecDetailActivity extends BaseActivity {

    public static final String DATA_ID = "id";

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_test_result)
    TextView mtvTestResult;

    @BindView(R.id.tv_test_member)
    TextView mTvTestMember;

    @BindView(R.id.tv_sample_name)
    TextView mTvSampleName;

    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;

    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;

    private BaseQuickAdapter<String, BaseViewHolder> mBaseQuickAdapter;
    private String mId;

    @Override
    protected int getLayout() {
        return R.layout.activity_detec_detail;
    }

    @Override
    protected void initEventAndData() {

        Intent intent = getIntent();
        mId = intent.getStringExtra(DATA_ID);

        mBaseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_detec_detail) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBaseQuickAdapter.bindToRecyclerView(mRecyclerView);

        ArrayList<String> strings = new ArrayList<>();
        ApiService.getInstance()
                .getApi()
                .getDeteDetailData(mId)
                .compose(RxFlowable.handleResult())
                .compose(RxFlowable.io_main())
                .subscribe(new RxSimpleSubscriber<DetecDeatilBean>() {
                    @Override
                    public void onSuccess(DetecDeatilBean bean) {

                        if (bean.getTestResult().equals("1")) {
                            mtvTestResult.setText("阳性");
                            mRlBg.setBackground(CommonUtils.getDrawable(R.drawable.img_detail_yang));
                        } else {
                            mtvTestResult.setText("阴性");
                            mRlBg.setBackground(CommonUtils.getDrawable(R.drawable.img_detecdetail_bg));
                        }
                        mTvTestMember.setText(bean.getTestMember());
                        mTvProjectName.setText(bean.getProjectName());
                        mTvSampleName.setText(bean.getSampleName());

                        strings.add("样品单位：" + bean.getTestMember());
                        strings.add("样品名称：" + bean.getSampleName());
                        strings.add("仪器编号：" + bean.getDevicename());
                        strings.add("检测项目：" + bean.getProjectName());
                        strings.add("检测结果：" + bean.getTestValue() + bean.getSampleUnit());
                        strings.add("检测标准：" + bean.getStandard() + bean.getSampleUnit());
                        strings.add("检测人员：" + bean.getTestMember());
                        strings.add("检测时间：" + TimeUtils.format(bean.getTestTime()));
                        strings.add("送往市场：" + bean.getDstMarket());
                        strings.add("车牌号码：" + bean.getCarno());
                        mBaseQuickAdapter.addData(strings);
                    }
                });
    }

    @OnClick(R.id.iv_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    public static void actionActivity(Context context,String id) {
        Intent intent = new Intent(context, DetecDetailActivity.class);
        intent.putExtra(DATA_ID,id);
        context.startActivity(intent);
    }

}
