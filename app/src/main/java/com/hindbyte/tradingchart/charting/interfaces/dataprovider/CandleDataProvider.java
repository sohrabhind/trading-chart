package com.hindbyte.tradingchart.charting.interfaces.dataprovider;

import com.hindbyte.tradingchart.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
