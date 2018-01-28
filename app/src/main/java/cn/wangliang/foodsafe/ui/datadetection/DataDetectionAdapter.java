package cn.wangliang.foodsafe.ui.datadetection;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.data.network.bean.DataDetectionBean;
import cn.wangliang.foodsafe.util.CommonUtils;

/**
 * Created by wangliang on 2018/1/22.
 */

public class DataDetectionAdapter extends BaseQuickAdapter<DataDetectionBean, BaseViewHolder> {
    public DataDetectionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataDetectionBean item) {
        helper.setText(R.id.tv_project_name, item.getProjectName());
        helper.setText(R.id.tv_sample_name, item.getSampleName());
        helper.setText(R.id.tv_sample_unit, item.getSampleUnit());
        helper.setText(R.id.tv_test_time, item.getTestTime());
        helper.setText(R.id.tv_test_value, item.getTestValue() + "/" + item.getStandard());
        ImageView ivTestResult =  helper.getView(R.id.iv_test_result);
        if (item.getTestResult().equals("1")){// 阳性
            ivTestResult.setBackground(CommonUtils.getDrawable(R.drawable.img_test_yang));
            helper.setText(R.id.tv_test_result,"阳性");
        }else {
            ivTestResult.setBackground(CommonUtils.getDrawable(R.drawable.img_test_yin));
            helper.setText(R.id.tv_test_result,"阴性");
        }

    }


}
