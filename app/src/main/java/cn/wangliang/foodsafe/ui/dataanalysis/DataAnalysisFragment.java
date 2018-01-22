package cn.wangliang.foodsafe.ui.dataanalysis;

import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseFragment;

/**
 * Created by wangliang on 2018/1/22.
 * 大数据分析页面
 */

public class DataAnalysisFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_data_analysis;
    }

    @Override
    protected void initEventAndData() {

    }
    public static DataAnalysisFragment newInstance() {
        return new DataAnalysisFragment();
    }
}
