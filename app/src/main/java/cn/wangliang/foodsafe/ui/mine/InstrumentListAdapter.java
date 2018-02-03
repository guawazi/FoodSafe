package cn.wangliang.foodsafe.ui.mine;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.data.network.bean.DeviceBean;

/**
 * 仪器列表页面
 */
public class InstrumentListAdapter extends BaseQuickAdapter<DeviceBean, BaseViewHolder> {


    public InstrumentListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceBean item) {
        helper.setText(R.id.tv_model, item.getModel());
        helper.setText(R.id.tv_devicename, item.getDevicename());
    }


}
