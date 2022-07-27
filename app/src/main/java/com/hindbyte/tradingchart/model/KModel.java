package com.hindbyte.tradingchart.model;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class KModel {

    private String time;
    private double price_o;
    private double price_c;
    private double price_h;
    private double price_l;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice_o() {
        return price_o;
    }

    public void setPrice_o(double price_o) {
        this.price_o = price_o;
    }

    public double getPrice_c() {
        return price_c;
    }

    public void setPrice_c(double price_c) {
        this.price_c = price_c;
    }

    public double getPrice_h() {
        return price_h;
    }

    public void setPrice_h(double price_h) {
        this.price_h = price_h;
    }

    public double getPrice_l() {
        return price_l;
    }

    public void setPrice_l(double price_l) {
        this.price_l = price_l;
    }

}
