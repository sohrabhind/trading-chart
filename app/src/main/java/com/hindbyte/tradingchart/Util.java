package com.hindbyte.tradingchart;

import com.hindbyte.tradingchart.klinelib.model.HisData;

import java.util.ArrayList;
import java.util.List;


public class Util {

    public static List<HisData> get1Day() {
        List<HisData> hisData = new ArrayList<>();
        double price = 21000;
        for (int i = 0; i < 1; i++) {
            HisData data = new HisData();
            price += Math.random() >= 0.5 ? +15 : -15;
            data.setClose(price);
            data.setDate(System.currentTimeMillis());
            hisData.add(data);
        }
        return hisData;
    }


}
