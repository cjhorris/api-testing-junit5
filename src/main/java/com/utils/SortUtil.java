package com.utils;

import com.model.TradeData;

import java.util.ArrayList;

public class SortUtil {
    public Number getHighestPrice(ArrayList<TradeData> tradeData) {
        Number tradesHighestPrice = tradeData.get(0).getP();
        for (TradeData td : tradeData) {
            if (td.getP().floatValue() > tradesHighestPrice.floatValue()) {
                tradesHighestPrice = td.getP().floatValue();
            }
        }
        return tradesHighestPrice;
    }


    public Number getLowestPrice(ArrayList<TradeData> tradeData) {
        Number tradesLowestPrice = tradeData.get(0).getP();
        for (TradeData td : tradeData) {
            if (td.getP().floatValue() < tradesLowestPrice.floatValue()) {
                tradesLowestPrice = td.getP().floatValue();
            }
        }
        return tradesLowestPrice;

    }
}
