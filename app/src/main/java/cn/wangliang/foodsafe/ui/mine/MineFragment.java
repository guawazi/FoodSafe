package cn.wangliang.foodsafe.ui.mine;

import android.view.View;

import butterknife.OnClick;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseFragment;
import cn.wangliang.foodsafe.util.CommonUtils;

/**
 * Created by wangliang on 2018/1/22.
 * 我的页面
 */

public class MineFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.tv_modify_password,R.id.tv_instrument_num,R.id.tv_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_modify_password:
                ModifyPasswordActivity.actionActivity(getContext());
                break;
            case R.id.tv_instrument_num:
                InstrumentListActivity.actionActivity(getContext());
                break;
            case R.id.tv_update:
                CommonUtils.showToastShort("当前已经是最新版本");
                break;
        }
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }
}
