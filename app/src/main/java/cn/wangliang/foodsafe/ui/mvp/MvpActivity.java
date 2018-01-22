package cn.wangliang.foodsafe.ui.mvp;

import android.os.Bundle;

import cn.wangliang.foodsafe.ui.base.BaseActivity;
import cn.wangliang.foodsafe.util.CommonUtils;

public abstract class MvpActivity<T extends MvpPresenter> extends BaseActivity implements MvpView {

    protected T mPresenter;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        mPresenter = initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

    protected abstract T initInject();

    @Override
    public void showLoading(String msg) {
        CommonUtils.showToastShort(msg);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        CommonUtils.showToastShort(errorMsg);
    }

    @Override
    public void hideError() {

    }
}