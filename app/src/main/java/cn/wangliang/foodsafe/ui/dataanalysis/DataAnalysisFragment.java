package cn.wangliang.foodsafe.ui.dataanalysis;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import cn.wangliang.foodsafe.R;
import cn.wangliang.foodsafe.base.base.BaseFragment;

/**
 * Created by wangliang on 2018/1/22.
 * 大数据分析页面
 */

public class DataAnalysisFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.line_chart)
    LineChart mLineChart;

    @BindView(R.id.pie_chart)
    PieChart mPieChart;

    private DataAnalysisAdapter mDataAnalysisAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_data_analysis;
    }

    @Override
    protected void initEventAndData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataAnalysisAdapter = new DataAnalysisAdapter(R.layout.item_data_analysis);
        mDataAnalysisAdapter.bindToRecyclerView(mRecyclerView);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("" + i);
        }
        mDataAnalysisAdapter.addData(list);

        initLineChart();
        initPieChart();
    }

    private void initLineChart() {

        mLineChart.setDragEnabled(false); // 禁止拖拽
        mLineChart.setScaleEnabled(false); // 禁止缩放
        mLineChart.getDescription().setEnabled(false);  // 关闭描述
        mLineChart.getLegend().setEnabled(false); // 关闭图例


        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        xAxis.setLabelCount(12, true);
        xAxis.setAxisLineColor(Color.parseColor("#418c5b"));

        YAxis axisLeft = mLineChart.getAxisLeft();
        mLineChart.getAxisRight().setEnabled(false);
        axisLeft.setAxisMinimum(40);
        axisLeft.setAxisMaximum(100);
        axisLeft.setLabelCount(7, true);
        axisLeft.setAxisLineColor(Color.parseColor("#418c5b"));

        ArrayList<Entry> entries = new ArrayList<>();
        Random random = new Random();
        entries.add(new Entry(1, random.nextInt(60) + 40));
        entries.add(new Entry(2, random.nextInt(60) + 40));
        entries.add(new Entry(3, random.nextInt(60) + 40));
        entries.add(new Entry(4, random.nextInt(60) + 40));
        entries.add(new Entry(5, random.nextInt(60) + 40));
        entries.add(new Entry(6, random.nextInt(60) + 40));
        entries.add(new Entry(7, random.nextInt(60) + 40));
        entries.add(new Entry(8, random.nextInt(60) + 40));
        entries.add(new Entry(9, random.nextInt(60) + 40));
        entries.add(new Entry(10, random.nextInt(60) + 40));
        entries.add(new Entry(11, random.nextInt(60) + 40));
        entries.add(new Entry(12, random.nextInt(60) + 40));
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

    private void initPieChart() {

        mPieChart.setHoleRadius(0);// 实心圆
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.getDescription().setEnabled(false); // 关闭描述
        // 设置图例
        Legend legend = mPieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(colors);
        set.setSliceSpace(2);

        PieData data = new PieData(set);
        mPieChart.setData(data);
        mPieChart.invalidate(); // refresh
    }


    public static DataAnalysisFragment newInstance() {
        return new DataAnalysisFragment();
    }
}
