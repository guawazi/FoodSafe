package cn.wangliang.foodsafe.ui.dataanalysis.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.wangliang.foodsafe.R;

/**
 * Created by wangliang on 2018/3/4.
 * desc:
 */

public class LeftRecyclerAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private int mPosition = 0;

    public LeftRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView textView = (TextView) helper.getView(R.id.tv_platform_left);
        textView.setText(item);
        if (mPosition == helper.getAdapterPosition()){
            textView.setTextColor(mContext.getResources().getColor(R.color.color_52c14b));
        }else {
            textView.setTextColor(mContext.getResources().getColor(R.color.color_282828));
        }
    }

    public void setSelectionItem(View view, int position){
        if (position == mPosition){
            return;
        }else {
            mPosition = position;
            notifyDataSetChanged();
        }
    }
}
