package cn.wangliang.foodsafe.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseActivity;

public class ModifyPasswordActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initEventAndData() {

    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, ModifyPasswordActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
