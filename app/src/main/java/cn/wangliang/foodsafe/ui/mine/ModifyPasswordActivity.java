package cn.wangliang.foodsafe.ui.mine;

import android.content.Context;
import android.content.Intent;

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

}
