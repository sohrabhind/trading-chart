package com.hindbyte.tradingchart.klinelib.chart;

/**
 * Created by Administrator on 2016/2/1.
 */

import android.content.Context;
import android.widget.TextView;

import com.hindbyte.tradingchart.R;
import com.hindbyte.tradingchart.charting.components.MarkerView;
import com.hindbyte.tradingchart.charting.data.Entry;
import com.hindbyte.tradingchart.charting.highlight.Highlight;
import com.hindbyte.tradingchart.klinelib.util.DoubleUtil;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class LineChartYMarkerView extends MarkerView {

    private final int digits;
    private TextView tvContent;

    public LineChartYMarkerView(Context context, int digits) {
        super(context, R.layout.view_mp_real_price_marker);
        this.digits = digits;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float value = e.getY();
        tvContent.setText(DoubleUtil.getStringByDigits(value, digits));
        super.refreshContent(e, highlight);
    }

}
