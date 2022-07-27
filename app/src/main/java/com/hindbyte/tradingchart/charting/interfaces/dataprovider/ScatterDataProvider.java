package com.hindbyte.tradingchart.charting.interfaces.dataprovider;

import com.hindbyte.tradingchart.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
