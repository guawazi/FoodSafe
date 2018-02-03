package cn.wangliang.foodsafe.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseActivity;
import cn.wangliang.foodsafe.data.network.ApiService;
import cn.wangliang.foodsafe.data.network.RxFlowable;
import cn.wangliang.foodsafe.data.network.RxSimpleSubscriber;
import cn.wangliang.foodsafe.data.network.bean.DeviceBean;
import cn.wangliang.foodsafe.util.Constant;
import cn.wangliang.foodsafe.util.SPUtils;

/**
 * 仪器列表页面
 */
public class InstrumentListActivity extends BaseActivity {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private InstrumentListAdapter mInstrumentListAdapter;
    private View mHeaderView;

    @Override
    protected int getLayout() {
        return R.layout.activity_instrument_list;
    }

    @Override
    protected void initEventAndData() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mInstrumentListAdapter = new InstrumentListAdapter(R.layout.item_instrument);
        mRecycler.setAdapter(mInstrumentListAdapter);
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.item_instrument_header, mRecycler, false);
        mInstrumentListAdapter.addHeaderView(mHeaderView);


        ApiService.getInstance()
                .getApi()
                .getdevice(SPUtils.getString(Constant.LOGIN_USERID))
                .compose(RxFlowable.handleResult())
                .compose(RxFlowable.io_main())
                .subscribe(new RxSimpleSubscriber<List<DeviceBean>>() {
                    @Override
                    public void onSuccess(List<DeviceBean> bean) {
                        mInstrumentListAdapter.setNewData(bean);
                    }
                });
    }


    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }


    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, InstrumentListActivity.class);
        context.startActivity(intent);
    }
}
