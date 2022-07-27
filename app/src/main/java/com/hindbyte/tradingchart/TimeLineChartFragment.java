package com.hindbyte.tradingchart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hindbyte.tradingchart.klinelib.chart.TimeLineView;
import com.hindbyte.tradingchart.klinelib.model.HisData;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TimeLineChartFragment extends Fragment {


    private TimeLineView mTimeLineView;
    private int mType;

    public TimeLineChartFragment() {
        // Required empty public constructor
    }

    public static TimeLineChartFragment newInstance(int type) {
        TimeLineChartFragment fragment = new TimeLineChartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTimeLineView = new TimeLineView(getContext());
        initData();
        return mTimeLineView;
    }


    protected void initData() {
        final List<HisData> hisData = Util.get1Day();
        mTimeLineView.setLastClose(hisData.get(0).getClose());
        mTimeLineView.initData(hisData);
        final double[] price = {21000};
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimeLineView.post(() -> {
                    HisData newData = new HisData();
                    price[0] += Math.random() >= 0.5 ? +30 : -15;
                    newData.setClose(price[0]);
                    newData.setDate(System.currentTimeMillis());
                    hisData.add(newData);
                    mTimeLineView.addData(newData);
                });
            }
        }, 1000, 500L *mType);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimeLineView.post(() -> mTimeLineView.refreshData((float) (hisData.get(0).getClose() + 10 * Math.random())));
            }
        }, 1000, 1000L *mType);
    }

}
