package cn.wangliang.foodsafe.ui.dataanalysis;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseFragment;
import cn.wangliang.foodsafe.data.network.ApiService;
import cn.wangliang.foodsafe.data.network.RxFlowable;
import cn.wangliang.foodsafe.data.network.RxSimpleSubscriber;
import cn.wangliang.foodsafe.data.network.bean.StatisticsBean;
import cn.wangliang.foodsafe.data.network.bean.StatisticsSampleBean;
import cn.wangliang.foodsafe.data.network.bean.TimeBean;
import cn.wangliang.foodsafe.data.network.bean.TopBean;
import cn.wangliang.foodsafe.util.Constant;
import cn.wangliang.foodsafe.util.SPUtils;
import cn.wangliang.foodsafe.util.TimeUtils;

/**
 * Created by wangliang on 2018/1/22.
 * 大数据分析页面
 */

public class DataAnalysisFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    LineChart mLineChart;

    PieChart mPieChart;

    @BindView(R.id.tv_year)
    TextView mTvYear;

    @BindView(R.id.tv_total_count)
    TextView mTvTotalCount;

    @BindView(R.id.tv_yin)
    TextView mTvYin;

    @BindView(R.id.tv_yang)
    TextView mTvYang;

    @BindView(R.id.tv_yin_percent)
    TextView mTvYinPercent;

    @BindView(R.id.tv_yang_percent)
    TextView mTvYangPercent;


    private DataAnalysisAdapter mDataAnalysisAdapter;
    private TimePickerView mTimePickerView;

    private int minTime = 0;
    private int maxTime = 0;

    private String mDefaultYear = "2018";

    @Override
    protected int getLayout() {
        return R.layout.fragment_data_analysis;
    }

    @Override
    protected void initEventAndData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataAnalysisAdapter = new DataAnalysisAdapter(R.layout.item_data_analysis);
        mDataAnalysisAdapter.bindToRecyclerView(mRecyclerView);

        View qualifiedHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_analysis_qualified_header, mRecyclerView, false);
        mLineChart = qualifiedHeader.findViewById(R.id.line_chart);

        View noQualifiedHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_analysis_not_qualified_header, mRecyclerView, false);
        mPieChart = noQualifiedHeader.findViewById(R.id.pie_chart);

        mDataAnalysisAdapter.addHeaderView(qualifiedHeader);
        mDataAnalysisAdapter.addHeaderView(noQualifiedHeader);

        mTimePickerView = new TimePickerView.Builder(getContext(), (date, v) -> {
            String format = TimeUtils.format3(date.getTime());
            mTvYear.setText(format + "年");
            initData(format);
        })
                .setType(new boolean[]{true, false, false, false, false, false})
                .setTitleText("选择时间")
                .build();
        mTimePickerView.setDate(Calendar.getInstance());
        initData(mDefaultYear);
    }

    private void initLineChart(List<TimeBean> timeBeanList) {

        mLineChart.setDragEnabled(false); // 禁止拖拽
        mLineChart.setScaleEnabled(false); // 禁止缩放
        mLineChart.getDescription().setEnabled(false);  // 关闭描述
        mLineChart.getLegend().setEnabled(false); // 关闭图例


        ArrayList<Entry> entries = new ArrayList<>();

        for (TimeBean timeBean : timeBeanList) {
            int yin = timeBean.getYin();
            if (yin > maxTime) {
                maxTime = yin;
            }
            if (yin < minTime) {
                minTime = yin;
            }
            entries.add(new Entry(timeBean.getMonth(), yin));
        }


        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        xAxis.setLabelCount(12, true);
        xAxis.setAxisLineColor(Color.parseColor("#418c5b"));

        YAxis axisLeft = mLineChart.getAxisLeft();
        mLineChart.getAxisRight().setEnabled(false);
        axisLeft.setAxisMinimum(minTime);
        axisLeft.setAxisMaximum(maxTime);
        axisLeft.setLabelCount(7, false);
        axisLeft.setAxisLineColor(Color.parseColor("#418c5b"));


        LineDataSet lineDataSet = new LineDataSet(entries, "label");

        lineDataSet.setDrawIcons(false);
        lineDataSet.setHighlightEnabled(true);//显示高亮线
        lineDataSet.setDrawCircles(false);//不画节点
        lineDataSet.setColor(Color.parseColor("#458B5D"));

        // 3. 根据数据和图表信息(颜色等) 构造出一个完整的图表数据源
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.invalidate(); // refresh


    }

    private void initPieChart(List<StatisticsSampleBean> sampleBeans) {

        int r = 69;
        int g = 139;
        int b = 93;

        mPieChart.setHoleRadius(0);// 实心圆
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.getDescription().setEnabled(false); // 关闭描述
        // 设置图例
        Legend legend = mPieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        List<PieEntry> entries = new ArrayList<>();
        int totalYang = 0;
        for (StatisticsSampleBean sampleBean : sampleBeans) {
            totalYang = totalYang + sampleBean.getYang();
        }
        List<Integer> colors = new ArrayList<>();
        for (StatisticsSampleBean sampleBean : sampleBeans) {
            float value = (float) sampleBean.getYang() / totalYang * 100;
            if (value > 0) {
                entries.add(new PieEntry(value, sampleBean.getSampleName()));
                colors.add(Color.rgb(r, g, b));
                g += 20;
            }
        }
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(colors);
        set.setSliceSpace(2);

        PieData data = new PieData(set);
        mPieChart.setData(data);
        mPieChart.invalidate(); // refresh
    }


    private void initData(String year) {
        ApiService.getInstance()
                .getApi()
                .getStatistics(SPUtils.getString(Constant.LOGIN_USERID), year)
                .compose(RxFlowable.handleResult())
                .compose(RxFlowable.io_main())
                .subscribe(new RxSimpleSubscriber<StatisticsBean>() {
                    @Override
                    public void onSuccess(StatisticsBean bean) {
                        try {
                            TopBean top = bean.getTop();
                            mTvTotalCount.setText(top.getAll() + "");
                            mTvYin.setText("合格 " + top.getYin() + " 次");
                            mTvYinPercent.setText((float) top.getYin() / top.getAll() * 100 + "%");
                            mTvYang.setText("不合格 " + top.getYang() + " 次");
                            mTvYangPercent.setText((float) top.getYang() / top.getAll() * 100 + "%");

                            List<TimeBean> beanTimes = bean.getTime();
                            initLineChart(beanTimes);

                            List<StatisticsSampleBean> sampleBeanList = bean.getSample();
                            initPieChart(sampleBeanList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @OnClick({R.id.ll_chose_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_chose_time:
                mTimePickerView.show();
                break;
        }
    }


    public static DataAnalysisFragment newInstance() {
        return new DataAnalysisFragment();
    }
}
