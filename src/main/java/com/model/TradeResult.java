package com.model;

import java.util.ArrayList;

public class TradeResult {
    String instrument_name;
    ArrayList<TradeData> data;

    public String getInstrumentName() {
        return instrument_name;
    }

    public ArrayList<TradeData> getData() {
        return data;
    }
}
