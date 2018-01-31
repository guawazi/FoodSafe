package cn.wangliang.foodsafe.ui.detecdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseActivity;

public class DetecDetailActivity extends BaseActivity {


    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> mBaseQuickAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_detec_detail;
    }

    @Override
    protected void initEventAndData() {
        mBaseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_detec_detail) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBaseQuickAdapter.bindToRecyclerView(mRecyclerView);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("样品单位：大昌宏晟");
        }

        mBaseQuickAdapter.addData(strings);
    }


    @OnClick(R.id.iv_back)
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, DetecDetailActivity.class);
        context.startActivity(intent);
    }
}
