package cn.wangliang.foodsafe.data.network;

import java.net.SocketTimeoutException;

import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.data.network.bean.ResultBean;
import cn.wangliang.foodsafe.data.network.exception.ApiException;
import cn.wangliang.foodsafe.data.network.okhttputils.NetworkUtil;
import cn.wangliang.foodsafe.util.CommonUtils;
import cn.wangliang.foodsafe.util.Constant;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class RxSimpleSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T bean) {
        onSuccess(bean);
    }

    @Override
    public void onError(Throwable e) {
        if (!NetworkUtil.isConnected()) {
            onFailed(new ResultBean(Constant.STATUS_DISCONNECT, CommonUtils.getString(R.string.api_net_disable)));
        } else if (e instanceof ApiException) {
            ResultBean bean = ((ApiException) e).getBean();
            onFailed(bean);
        } else if (e instanceof SocketTimeoutException) {
            onFailed(new ResultBean(Constant.STATUS_TIMEOUT, CommonUtils.getString(R.string.api_net_timeout)));
        } else {
            onFailed(new ResultBean(Constant.STATUS_ERROR, CommonUtils.getString(R.string.api_net_error)));
        }
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
        CommonUtils.showToastShort(bean.getMsg());
    }

    @Override
    public void onComplete() {
        RxFlowable.disposable(this);
    }

}