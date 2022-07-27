package com.hindbyte.tradingchart.charting.interfaces.dataprovider;

import com.hindbyte.tradingchart.charting.components.YAxis.AxisDependency;
import com.hindbyte.tradingchart.charting.data.BarLineScatterCandleBubbleData;
import com.hindbyte.tradingchart.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
