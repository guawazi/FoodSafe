package cn.wangliang.foodsafe;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wangliang.foodsafe.ui.dataanalysis.DataAnalysisFragment;
import cn.wangliang.foodsafe.ui.datadetection.DataDetectionFragment;
import cn.wangliang.foodsafe.ui.mine.MineFragment;
import cn.wangliang.foodsafe.base.base.BaseActivity;


public class MainActivity extends BaseActivity {


    @BindView(R.id.tv_data_detection)
    TextView mTvDataDetection;

    @BindView(R.id.tv_data_analysis)
    TextView mTvDataAnalysis;

    @BindView(R.id.tv_mine)
    TextView mTvMine;


    @BindView(R.id.container)
    FrameLayout mContainer;

    private FragmentManager mSupportFragmentManager;
    private Fragment mShowingFragment;
    private DataAnalysisFragment mDataAnalysisFragment;
    private DataDetectionFragment mDataDetectionFragment;
    private MineFragment mMineFragment;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        mSupportFragmentManager = getSupportFragmentManager();
        mDataDetectionFragment = DataDetectionFragment.newInstance();
        mDataAnalysisFragment = DataAnalysisFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        showFragment(mDataDetectionFragment);
    }


    @OnClick({R.id.tv_data_detection, R.id.tv_data_analysis, R.id.tv_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_data_detection:
                showFragment(mDataDetectionFragment);
                mTvDataDetection.setTextColor(Color.GREEN);
                mTvDataAnalysis.setTextColor(Color.GRAY);
                mTvMine.setTextColor(Color.GRAY);
                break;
            case R.id.tv_data_analysis:
                showFragment(mDataAnalysisFragment);
                mTvDataDetection.setTextColor(Color.GRAY);
                mTvDataAnalysis.setTextColor(Color.GREEN);
                mTvMine.setTextColor(Color.GRAY);
                break;
            case R.id.tv_mine:
                showFragment(mMineFragment);
                mTvDataDetection.setTextColor(Color.GRAY);
                mTvDataAnalysis.setTextColor(Color.GRAY);
                mTvMine.setTextColor(Color.GREEN);
                break;
        }
    }

    private void showFragment(Fragment showFragment) {
        if (showFragment != mShowingFragment) {
            if (showFragment.isAdded()) { // 被添加了
                if (mShowingFragment != null) {
                    mSupportFragmentManager.beginTransaction()
                            .hide(mShowingFragment)
                            .show(showFragment)
                            .commit();
                } else {
                    mSupportFragmentManager.beginTransaction()
                            .show(showFragment)
                            .commit();
                }

            } else {
                if (mShowingFragment != null) {
                    mSupportFragmentManager.beginTransaction()
                            .hide(mShowingFragment)
                            .add(R.id.container, showFragment)
                            .commit();
                } else {
                    mSupportFragmentManager.beginTransaction()
                            .add(R.id.container, showFragment)
                            .commit();
                }
            }
        }
        mShowingFragment = showFragment;
    }


}
