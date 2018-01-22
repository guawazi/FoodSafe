package cn.wangliang.foodsafe.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.wangliang.foodsafe.AppManager;


public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;
        mUnBinder = ButterKnife.bind(this);
        onViewCreated(savedInstanceState);
        initEventAndData();
        AppManager.getAppManager().addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        mUnBinder.unbind();
    }

    protected void onViewCreated(Bundle savedInstanceState) {

    }

    protected abstract int getLayout();

    protected abstract void initEventAndData();


}
