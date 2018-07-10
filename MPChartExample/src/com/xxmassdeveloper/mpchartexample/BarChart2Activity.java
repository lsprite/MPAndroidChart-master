
package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xxmassdeveloper.mpchartexample.custom.MyAxisValueFormatter;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;
import com.xxmassdeveloper.mpchartexample.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarChart2Activity extends DemoBase {

    protected BarChart mChart;
    private List<String> xAxisValues;
    private List<Float> yAxisValues1;
    private List<Float> yAxisValues2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart2);
        mChart = findViewById(R.id.chart1);

        xAxisValues = new ArrayList<>();
        yAxisValues1 = new ArrayList<>();
        yAxisValues2 = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            xAxisValues.add(i + "");
            yAxisValues1.add((float) (Math.random() * 800 + 20));
            yAxisValues2.add((float) (Math.random() * 1000 + 20));
        }
        setTwoBarChart(mChart, xAxisValues, yAxisValues1, yAxisValues2, "柱状图1", "柱状图2");

    }


    public static void setTwoBarChart(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2) {
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(false);//设置不按比例放缩柱状图

        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xAxisValue.size());
        xAxis.setCenterAxisLabels(true);//设置标签居中
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));

        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(1.5f);//上面面空白的部分
        leftAxis.setSpaceBottom(0f);//下面空白的部分


        barChart.getAxisRight().setEnabled(false);

        //设置坐标轴最大最小值
        Float yMin1 = Collections.min(yAxisValue1);
        Float yMin2 = Collections.min(yAxisValue2);
        Float yMax1 = Collections.max(yAxisValue1);
        Float yMax2 = Collections.max(yAxisValue2);
        Float yMin = Double.valueOf((yMin1 < yMin2 ? yMin1 : yMin2) * 0.1).floatValue();
        Float yMax = Double.valueOf((yMax1 > yMax2 ? yMax1 : yMax2) * 1.1).floatValue();
        leftAxis.setAxisMaximum(yMax);
        leftAxis.setAxisMinimum(yMin);
        leftAxis.setStartAtZero(true);//保证y轴的0一定会显示

        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);

        //设置柱状图数据
        setTwoBarChartData(barChart, xAxisValue, yAxisValue1, yAxisValue2, bartilte1, bartitle2);

//        barChart.animateX(1500);//数据显示动画，从左往右依次显示
        barChart.invalidate();
    }

    /**
     * 设置柱状图数据源
     */
    private static void setTwoBarChartData(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2) {
        float groupSpace = 0.04f;
        float barSpace = 0.03f;
        float barWidth = 0.45f;
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int i = 0, n = yAxisValue1.size(); i < n; ++i) {
            entries1.add(new BarEntry(i, yAxisValue1.get(i)));
            entries2.add(new BarEntry(i, yAxisValue2.get(i)));
        }

        BarDataSet dataset1, dataset2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            dataset1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            dataset2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);

            dataset1.setValues(entries1);
            dataset2.setValues(entries2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            dataset1 = new BarDataSet(entries1, bartilte1);
            dataset2 = new BarDataSet(entries2, bartitle2);

            dataset1.setColor(Color.rgb(129, 216, 200));
            dataset2.setColor(Color.rgb(181, 194, 202));
            dataset1.setValueTextColor(Color.rgb(129, 216, 200));
            dataset2.setValueTextColor(Color.rgb(181, 194, 202));
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataset1);
            dataSets.add(dataset2);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return StringUtils.double2String(value, 2);
                }
            });

            barChart.setData(data);
        }
//        barChart.setFitBars(true);
        barChart.getBarData().setHighlightEnabled(false);//设置点击不高亮
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + 0);
        barChart.groupBars(0, groupSpace, barSpace);

    }

}
