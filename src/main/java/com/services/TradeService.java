package com.services;

import com.model.TradeData;
import com.model.TradeResponse;
import com.rest.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TradeService {
    Logger logger = Logger.getLogger(TradeService.class.getName());
    private final RestClient restClient = new RestClient();

    public TradeResponse getTrade(String instrumentName) throws IOException, InterruptedException {
        return restClient.getTrades(instrumentName);
    }

    public ArrayList<TradeData> getTradesWithinOneMinute(String instrumentName, Long endTime)
            throws IOException, InterruptedException {
        TradeResponse tradeResponse = getTrade(instrumentName);
        ArrayList<TradeData> tradeData = tradeResponse.getResult().getData();
        logger.info("getTradesWithinOneMinute flitter out trade time: trade time < " + endTime);
        tradeData.removeIf(trade -> trade.getDataTime() < endTime);
        return tradeData;
    }

    public ArrayList<TradeData> getTradesByTimePeriod(String instrumentName, Long startTime, Long endTime)
            throws IOException, InterruptedException {
        TradeResponse tradeResponse = getTrade(instrumentName);
        ArrayList<TradeData> tradeData = tradeResponse.getResult().getData();
        logger.info("getTradesByTimePeriod by " + startTime + " < trade time < " + endTime);
        tradeData.removeIf(trade -> trade.getDataTime() < startTime || trade.getDataTime() > endTime - 1);
        return tradeData;
    }
}
