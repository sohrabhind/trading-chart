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
import com.hindbyte.tradingchart.klinelib.model.HisData;
import com.hindbyte.tradingchart.klinelib.util.DateUtils;

import java.util.List;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class LineChartXMarkerView extends MarkerView {

    private List<HisData> mList;
    private TextView tvContent;

    public LineChartXMarkerView(Context context, List<HisData> list) {
        super(context, R.layout.view_mp_real_price_marker);
        mList = list;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int value = (int) e.getX();
        if (mList != null && value < mList.size()) {
            tvContent.setText(DateUtils.formatTime(mList.get(value).getDate()));
        }
        super.refreshContent(e, highlight);
    }
}
