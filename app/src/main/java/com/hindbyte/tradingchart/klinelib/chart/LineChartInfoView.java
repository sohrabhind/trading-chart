package com.hindbyte.tradingchart.klinelib.chart;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.hindbyte.tradingchart.R;
import com.hindbyte.tradingchart.klinelib.model.HisData;
import com.hindbyte.tradingchart.klinelib.util.DateUtils;
import com.hindbyte.tradingchart.klinelib.util.DoubleUtil;

/**
 * 分时图点击的信息
 * Created by guoziwei on 2017/9/25.
 */

public class LineChartInfoView extends ChartInfoView {

    private TextView mTvPrice;
    private TextView mTvTime;

    public LineChartInfoView(Context context) {
        this(context, null);
    }

    public LineChartInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_line_chart_info, this);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
    }

    @Override
    public void setData(double lastClose, HisData data) {
        mTvTime.setText(DateUtils.formatTime(data.getDate()));
        mTvPrice.setText(DoubleUtil.formatDecimal(data.getClose()));
        removeCallbacks(mRunnable);
        postDelayed(mRunnable, 2000);
    }

}
