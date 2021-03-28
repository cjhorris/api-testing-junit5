package com.model;


public class CandleStickResponse {
    Integer code;
    String method;
    CandleStickResult result;

    public Integer getCode() {
        return code;
    }

    public String getMethod() {
        return method;
    }

    public CandleStickResult getResult() {
        return result;
    }
}
