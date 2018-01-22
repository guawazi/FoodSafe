package cn.wangliang.foodsafe.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseActivity;

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
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.item_instrument_header, null);
        mInstrumentListAdapter.addHeaderView(mHeaderView);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("这是");
        }
        mInstrumentListAdapter.addData(list);
    }


    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, InstrumentListActivity.class);
        context.startActivity(intent);
    }
}
