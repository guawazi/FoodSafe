package cn.wangliang.foodsafe.ui.datadetection;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseFragment;

/**
 * Created by wangliang on 2018/1/22.
 * 检测数据页面
 */

public class DataDetectionFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private DataDetectionAdapter mDataDetectionAdapter;
    private View mAlldataHeader;
    private View mSearchHeader;

    @Override
    protected int getLayout() {
        return R.layout.fragment_data_detection;
    }

    @Override
    protected void initEventAndData() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataDetectionAdapter = new DataDetectionAdapter(R.layout.item_data_detetion);
        mRecycler.setAdapter(mDataDetectionAdapter);

        mAlldataHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_alldata_header, null);
        mSearchHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_search_header, null);
        mDataDetectionAdapter.addHeaderView(mSearchHeader);
        mDataDetectionAdapter.addHeaderView(mAlldataHeader);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("这是");
        }
        mDataDetectionAdapter.addData(list);
    }

    public static DataDetectionFragment newInstance() {
        return new DataDetectionFragment();
    }

}
