package cn.wangliang.foodsafe.ui.dataanalysis.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.wangliang.foodsafe.R;

/**
 * Created by wangliang on 2018/3/4.
 * desc:
 */

public class RightRecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RightRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_platform_right, item);
    }
}
