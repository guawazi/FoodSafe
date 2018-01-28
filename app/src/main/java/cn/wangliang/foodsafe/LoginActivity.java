package cn.wangliang.foodsafe;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wangliang.foodsafe.base.base.BaseActivity;
import cn.wangliang.foodsafe.data.network.ApiService;
import cn.wangliang.foodsafe.data.network.bean.LoginBean;
import cn.wangliang.foodsafe.data.network.bean.ResultBean;
import cn.wangliang.foodsafe.data.network.RxFlowable;
import cn.wangliang.foodsafe.data.network.RxSimpleSubscriber;
import cn.wangliang.foodsafe.util.CommonUtils;
import cn.wangliang.foodsafe.util.EncryptUtil;
import cn.wangliang.foodsafe.util.LoadingDialog;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_username)
    EditText mEtUsername;

    @BindView(R.id.et_password)
    EditText mEtPassword;
    private RxSimpleSubscriber<LoginBean> mRxSimpleSubscriber;
    private LoadingDialog mLoadingDialog;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
        }
    }

    private void doLogin() {
        String username = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            CommonUtils.showToastShort("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            CommonUtils.showToastShort("密码不能为空");
            return;
        }

        mLoadingDialog = LoadingDialog.newInstance()
                .setLoadingCancelListener(() -> RxFlowable.disposable(mRxSimpleSubscriber));
        mLoadingDialog.show(getSupportFragmentManager(),"LoadingDialog");
        mRxSimpleSubscriber = ApiService.getInstance()
                .getApi()
                .login(username, EncryptUtil.str2MD5(password))
                .compose(RxFlowable.handleResult())
                .compose(RxFlowable.io_main())
                .subscribeWith(new RxSimpleSubscriber<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean bean) {
                        if (mLoadingDialog != null){
                            mLoadingDialog.dismiss();
                        }
                        MainActivity.actionActivity(LoginActivity.this);
                        finish();
                    }

                    @Override
                    public void onFailed(ResultBean bean) {
                        super.onFailed(bean);
                        if (mLoadingDialog != null){
                            mLoadingDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxFlowable.disposable(mRxSimpleSubscriber);
    }
}
