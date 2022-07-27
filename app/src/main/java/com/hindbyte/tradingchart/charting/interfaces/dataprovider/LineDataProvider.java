package com.hindbyte.tradingchart.charting.interfaces.dataprovider;

import com.hindbyte.tradingchart.charting.components.YAxis;
import com.hindbyte.tradingchart.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
