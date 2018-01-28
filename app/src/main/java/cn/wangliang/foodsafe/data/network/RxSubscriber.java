package cn.wangliang.foodsafe.data.network;

import java.net.SocketTimeoutException;

import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.mvp.MvpView;
import cn.wangliang.foodsafe.data.network.bean.ResultBean;
import cn.wangliang.foodsafe.data.network.exception.ApiException;
import cn.wangliang.foodsafe.data.network.okhttputils.NetworkUtil;
import cn.wangliang.foodsafe.util.CommonUtils;
import cn.wangliang.foodsafe.util.Constant;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class RxSubscriber<T> extends DisposableSubscriber<T> {

    private MvpView mView;
    private String mMsg;
    private boolean isShowDialog;

    public RxSubscriber(MvpView view, String msg, boolean showDialog) {
        this.mView = view;
        this.mMsg = msg;
        this.isShowDialog = showDialog;
    }

    public RxSubscriber(MvpView view) {
        this(view, CommonUtils.getString(R.string.api_loading), true);
    }

    public RxSubscriber(MvpView view, boolean showDialog) {
        this(view, CommonUtils.getString(R.string.api_loading), showDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mView == null || mView.isDestroyed()) return;
        if (isShowDialog) mView.showLoading(mMsg);
    }

    @Override
    public void onNext(T bean) {
        if (mView == null || mView.isDestroyed()) return;
//        mView.stateMain();
        mView.hideLoading();
        onSuccess(bean);
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null || mView.isDestroyed()) return;
        mView.hideLoading();
        ResultBean bean;
        if (!NetworkUtil.isConnected()) {
            bean = new ResultBean(Constant.STATUS_DISCONNECT, CommonUtils.getString(R.string.api_net_disable));
        } else if (e instanceof ApiException) {
            bean = ((ApiException) e).getBean();
        } else if (e instanceof SocketTimeoutException) {
            bean = new ResultBean(Constant.STATUS_TIMEOUT, CommonUtils.getString(R.string.api_net_timeout));
        } else {
            bean = new ResultBean(Constant.STATUS_ERROR, CommonUtils.getString(R.string.api_net_error));
        }
        mView.showError(bean.getMsg());
        onFailed(bean);
        e.printStackTrace();
    }

    /**
     * 成功回调方法
     */
    public abstract void onSuccess(T bean);

    /**
     * 失败回调方法
     */
    public void onFailed(ResultBean bean) {
//        mView.showErrorMsg(bean.getMsg());
    }

    @Override
    public void onComplete() {
        RxFlowable.disposable(this);
    }

}
