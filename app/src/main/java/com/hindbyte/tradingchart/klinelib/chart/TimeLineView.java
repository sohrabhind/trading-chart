package com.hindbyte.tradingchart.klinelib.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.hindbyte.tradingchart.R;
import com.hindbyte.tradingchart.charting.charts.BarLineChartBase;
import com.hindbyte.tradingchart.charting.components.Legend;
import com.hindbyte.tradingchart.charting.components.LimitLine;
import com.hindbyte.tradingchart.charting.components.XAxis;
import com.hindbyte.tradingchart.charting.components.YAxis;
import com.hindbyte.tradingchart.charting.data.CombinedData;
import com.hindbyte.tradingchart.charting.data.Entry;
import com.hindbyte.tradingchart.charting.data.LineData;
import com.hindbyte.tradingchart.charting.data.LineDataSet;
import com.hindbyte.tradingchart.charting.interfaces.datasets.ILineDataSet;
import com.hindbyte.tradingchart.charting.utils.Transformer;
import com.hindbyte.tradingchart.klinelib.model.HisData;
import com.hindbyte.tradingchart.klinelib.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * kline
 * Created by guoziwei on 2017/10/26.
 */
public class TimeLineView extends BaseView implements CoupleChartGestureListener.OnAxisChangeListener {

    protected AppCombinedChart mChartPrice;

    protected ChartInfoView mChartInfoView;
    protected Context mContext;

    /**
     * last price
     */
    private double mLastPrice;

    /**
     * yesterday close price
     */
    private double mLastClose;


    public TimeLineView(Context context) {
        this(context, null);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_timeline, this);
        mChartPrice = findViewById(R.id.price_chart);
        mChartInfoView = findViewById(R.id.line_info);

        mChartInfoView.setChart(mChartPrice);

        mChartPrice.setNoDataText(context.getString(R.string.loading));
        initChartPrice();
        initChartListener();
    }


    protected void initChartPrice() {
        mChartPrice.setScaleEnabled(true);
        mChartPrice.setDrawBorders(false);
        mChartPrice.setBorderWidth(4);
        mChartPrice.setDragEnabled(true);
        mChartPrice.setScaleXEnabled(true);
        mChartPrice.setScaleYEnabled(false);
        mChartPrice.getDescription().setEnabled(false);
        mChartPrice.setAutoScaleMinMaxEnabled(true);
        mChartPrice.setDragDecelerationEnabled(false);
        LineChartXMarkerView mvx = new LineChartXMarkerView(mContext, mData);
        mvx.setChartView(mChartPrice);
        mChartPrice.setXMarker(mvx);
        Legend lineChartLegend = mChartPrice.getLegend();
        lineChartLegend.setEnabled(true);

        XAxis xAxisPrice = mChartPrice.getXAxis();
        xAxisPrice.setDrawLabels(true);
        xAxisPrice.setDrawAxisLine(true);
        xAxisPrice.setDrawGridLines(true);
        xAxisPrice.setTextColor(mAxisColor);
        xAxisPrice.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisPrice.setLabelCount(5, false);
        xAxisPrice.setAvoidFirstLastClipping(true);
        xAxisPrice.setAxisMinimum(-0.5f);
        xAxisPrice.setAxisMaximum(-0.5f);

        xAxisPrice.setValueFormatter((value, axis) -> {
            if (mData.isEmpty()) {
                return "";
            }
            if (value < 0) {
                value = 0;
            }
            if (value < mData.size()) {
                return DateUtils.formatTime(mData.get((int) value).getDate());
            }
            return "";
        });


        YAxis axisLeftPrice = mChartPrice.getAxisLeft();
        //axisLeftPrice.setLabelCount(0, false);
        axisLeftPrice.setDrawLabels(false);
        axisLeftPrice.setDrawGridLines(false);
        axisLeftPrice.setDrawAxisLine(false);

        //int[] colorArray = {mDecreasingColor, mDecreasingColor, mAxisColor, mIncreasingColor, mIncreasingColor};
        Transformer leftYTransformer = mChartPrice.getRendererLeftYAxis().getTransformer();
        ColorContentYAxisRenderer leftColorContentYAxisRenderer = new ColorContentYAxisRenderer(mChartPrice.getViewPortHandler(), mChartPrice.getAxisLeft(), leftYTransformer);
        //leftColorContentYAxisRenderer.setLabelColor(colorArray);
        leftColorContentYAxisRenderer.setLabelInContent(true);
        leftColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        mChartPrice.setRendererLeftYAxis(leftColorContentYAxisRenderer);


        YAxis axisRightPrice = mChartPrice.getAxisRight();
        axisRightPrice.setLabelCount(5, true);
        axisRightPrice.setDrawLabels(true);
        axisRightPrice.setDrawGridLines(true);
        axisRightPrice.setDrawAxisLine(true);
        axisRightPrice.setTextColor(mAxisColor);
        axisRightPrice.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


        Transformer rightYTransformer = mChartPrice.getRendererRightYAxis().getTransformer();
        ColorContentYAxisRenderer rightColorContentYAxisRenderer = new ColorContentYAxisRenderer(mChartPrice.getViewPortHandler(), mChartPrice.getAxisRight(), rightYTransformer);
        rightColorContentYAxisRenderer.setLabelInContent(true);
        rightColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        //rightColorContentYAxisRenderer.setLabelColor(colorArray);
        mChartPrice.setRendererRightYAxis(rightColorContentYAxisRenderer);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initChartListener() {
        mChartPrice.setOnChartGestureListener(new CoupleChartGestureListener(this, mChartPrice));
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener(mContext, mLastClose, mData, mChartInfoView));

        mChartPrice.setOnTouchListener(new ChartInfoViewHandler(mChartPrice));
    }


    public void initData(List<HisData> hisData) {

        mData.clear();
        mData.addAll(hisData);

        ArrayList<Entry> priceEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            priceEntries.add(new Entry(i, (float) mData.get(i).getClose()));
        }
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setLine(priceEntries));
        LineData lineData = new LineData(sets);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        mChartPrice.setData(combinedData);

        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.notifyDataSetChanged();
        mChartPrice.moveViewToX(combinedData.getEntryCount());
        moveToLast(mChartPrice);

        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);

        mChartPrice.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);
    }


    @androidx.annotation.NonNull
    private LineDataSet setLine(ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "BTCUSDT");
        lineDataSetMa.setDrawValues(false);
        lineDataSetMa.setColor(getResources().getColor(R.color.normal_line_color));
        lineDataSetMa.setCircleColor(ContextCompat.getColor(mContext, R.color.normal_line_color));

        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setCircleRadius(1f);

        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setDrawCircleHole(false);

        return lineDataSetMa;
    }



    /**
     * according to the price to refresh the last data of the chart
     */
    public void refreshData(float price) {
        if (price <= 0 || price == mLastPrice) {
            return;
        }
        mLastPrice = price;
        CombinedData data = mChartPrice.getData();
        if (data == null) return;
        LineData lineData = data.getLineData();
        if (lineData != null) {
            ILineDataSet set = lineData.getDataSetByIndex(0);
            if (set.removeLast()) {
                set.addEntry(new Entry(set.getEntryCount(), price));
            }
        }

        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
    }


    public void addData(HisData hisData) {
        CombinedData combinedData = mChartPrice.getData();
        LineData priceData = combinedData.getLineData();
        ILineDataSet priceSet = priceData.getDataSetByIndex(0);
        ILineDataSet aveSet = priceData.getDataSetByIndex(1);
        if (mData.contains(hisData)) {
            int index = mData.indexOf(hisData);
            priceSet.removeEntry(index);
            aveSet.removeEntry(index);
            mData.remove(index);
        }
        mData.add(hisData);
        priceSet.addEntry(new Entry(priceSet.getEntryCount(), (float) hisData.getClose()));

        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 1.5f);

        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
    }


    /**
     * add limit line to chart
     */
    public void setLimitLine(double lastClose) {
        LimitLine limitLine = new LimitLine((float) lastClose);
        limitLine.enableDashedLine(5, 10, 0);
        limitLine.setLineColor(getResources().getColor(R.color.limit_color));
        mChartPrice.getAxisLeft().addLimitLine(limitLine);
    }

    public void setLimitLine() {
        setLimitLine(mLastClose);
    }

    public void setLastClose(double lastClose) {
        mLastClose = lastClose;
        mChartPrice.setYCenter((float) lastClose);
        mChartPrice.setOnChartValueSelectedListener(new InfoViewListener(mContext, mLastClose, mData, mChartInfoView));
    }


    public HisData getLastData() {
        if (mData != null && !mData.isEmpty()) {
            return mData.get(mData.size() - 1);
        }
        return null;
    }

    @Override
    public void onAxisChange(BarLineChartBase chart) {

    }
}
