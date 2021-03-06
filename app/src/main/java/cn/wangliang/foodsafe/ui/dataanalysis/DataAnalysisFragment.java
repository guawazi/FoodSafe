package cn.wangliang.foodsafe.ui.dataanalysis;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import cn.wangliang.foodsafe.ui.dataanalysis.adapter.DataAnalysisAdapter;
import cn.wangliang.foodsafe.ui.dataanalysis.adapter.LeftRecyclerAdapter;
import cn.wangliang.foodsafe.ui.dataanalysis.adapter.RightRecyclerAdapter;
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

    @BindView(R.id.tv_start_data)
    TextView mTvStartData;

    @BindView(R.id.tv_end_data)
    TextView mTvEndData;

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

    @BindView(R.id.tv_platform)
    TextView mTvPlatform;

    private DataAnalysisAdapter mDataAnalysisAdapter;
    private TimePickerView mStartTimePickerView;
    private TimePickerView mEndTimePickerView;

    private int minTime = 0;
    private int maxTime = 0;

    private String mDefaultYear = "2018";
    private PopupWindow mPlatformWindow;
    private RecyclerView mRecyclerLeft;
    private RecyclerView mRecyclerRight;
    private LeftRecyclerAdapter mLeftRecyclerAdapter;
    private RightRecyclerAdapter mRightRecyclerAdapter;
    private ArrayList<String> mLeftData;
    private ArrayList<String> mRightCityData;
    private ArrayList<String> mRightMarketData;
    private ArrayList<String> mRightFactoryData;
    private ArrayList<List> mRightDatas;

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

        View itemHeader3 = LayoutInflater.from(getContext()).inflate(R.layout.item_header3, mRecyclerView, false);

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.item_platform_popwindow, null);
        mPlatformWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPlatformWindow.setOnDismissListener(() -> backgroundAlpha(1));
        mRecyclerLeft = contentView.findViewById(R.id.recycler_left);
        mRecyclerLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerRight = contentView.findViewById(R.id.recycler_right);
        mRecyclerRight.setLayoutManager(new LinearLayoutManager(getContext()));

        mLeftRecyclerAdapter = new LeftRecyclerAdapter(R.layout.item_platform_left);
        mLeftRecyclerAdapter.bindToRecyclerView(mRecyclerLeft);

        mRightRecyclerAdapter = new RightRecyclerAdapter(R.layout.item_platform_right);
        mRightRecyclerAdapter.bindToRecyclerView(mRecyclerRight);

        mLeftData = new ArrayList<>();
        mLeftData.add("区域选择");
        mLeftData.add("市场选择");
        mLeftData.add("厂家选择");
        mLeftRecyclerAdapter.setNewData(mLeftData);

        mRightCityData = new ArrayList<>();
        mRightCityData.add("北京  朝阳区");
        mRightCityData.add("北京  通州区");
        mRightCityData.add("北京  顺义区");
        mRightCityData.add("北京  昌平区");
        mRightCityData.add("北京  朝阳区");
        mRightCityData.add("北京  朝阳区");
        mRightCityData.add("北京  朝阳区");
        mRightCityData.add("北京  朝阳区");
        mRightCityData.add("北京  朝阳区");
        mRightCityData.add("北京  朝阳区");
        mRightCityData.add("北京  朝阳区");
        mRightRecyclerAdapter.setNewData(mRightCityData);

        mRightMarketData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mRightMarketData.add("市场" + i);
        }

        mRightFactoryData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mRightFactoryData.add("厂家" + i);
        }

        mRightDatas = new ArrayList<>();
        mRightDatas.add(mRightCityData);
        mRightDatas.add(mRightMarketData);
        mRightDatas.add(mRightFactoryData);

        mLeftRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> {
            mLeftRecyclerAdapter.setSelectionItem(view, position);
            mRightRecyclerAdapter.setNewData(mRightDatas.get(position));
        });


        mRightRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> {
            String s = mRightRecyclerAdapter.getData().get(position);
            mTvPlatform.setText(s);
            mPlatformWindow.dismiss();
        });

        mDataAnalysisAdapter.addHeaderView(qualifiedHeader);
        mDataAnalysisAdapter.addHeaderView(noQualifiedHeader);
//        mDataAnalysisAdapter.addHeaderView(itemHeader3);

        mStartTimePickerView = new TimePickerView.Builder(getContext(), (date, v) -> {
            String format = TimeUtils.format2(date.getTime());
            mTvStartData.setText(format + "年");
//            initData(format);
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setTitleText("选择开始时间")
                .build();
        mStartTimePickerView.setDate(Calendar.getInstance());

        mEndTimePickerView = new TimePickerView.Builder(getContext(), (date, v) -> {
            String format = TimeUtils.format2(date.getTime());
            mTvEndData.setText(format + "年");
//            initData(format);
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setTitleText("选择结束时间")
                .build();
        mEndTimePickerView.setDate(Calendar.getInstance());

        initData(mDefaultYear);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("新发地");
        strings.add("盛华宏林");
        strings.add("岳各庄");
        strings.add("锦绣大地");
        strings.add("八里桥");
//        mDataAnalysisAdapter.setNewData(strings);
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


    @OnClick({R.id.tv_start_data, R.id.tv_end_data, R.id.tv_platform})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_data:
                mStartTimePickerView.show();
                break;
            case R.id.tv_end_data:
                mEndTimePickerView.show();
                break;
            case R.id.tv_platform:
                backgroundAlpha(0.5f);
                mPlatformWindow.showAsDropDown(mTvPlatform);
                break;
        }
    }


    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getActivity().getWindow().setAttributes(lp); //act 是上下文context
    }

    public static DataAnalysisFragment newInstance() {
        return new DataAnalysisFragment();
    }
}
