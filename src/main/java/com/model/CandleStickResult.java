package com.model;

import java.util.ArrayList;

public class CandleStickResult {
    String instrument_name;
    String interval;
    Integer depth;
    ArrayList<CandleStickData> data;

    public String getInstrumentName() {
        return instrument_name;
    }

    public String getInterval() {
        return interval;
    }

    public Integer getDepth() {
        return depth;
    }
    public ArrayList<CandleStickData> getData() {
        return data;
    }
}
