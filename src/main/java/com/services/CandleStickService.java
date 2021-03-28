package com.services;


import com.model.CandleStickData;
import com.model.CandleStickResponse;
import com.rest.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class CandleStickService {
    Logger logger = Logger.getLogger(CandleStickService.class.getName());
    private final RestClient restClient = new RestClient();

    public CandleStickResponse getCandleStick(String instrumentName, String timeframe)
            throws IOException, InterruptedException {
        return restClient.getCandleStick(instrumentName, timeframe);
    }

    public CandleStickData getLatestCandleStick(String instrumentName, String timeframe)
            throws IOException, InterruptedException {
        logger.info("getLatestCandleStick for: " + "instrument: " + instrumentName + " timeframe: " + timeframe);
        CandleStickResponse candleStickResponse = getCandleStick(instrumentName, timeframe);
        ArrayList<CandleStickData> candleStickData = candleStickResponse.getResult().getData();
        return candleStickData.get(candleStickData.size() - 1);
    }

    public CandleStickData getCandleStickByTimestamp(String instrumentName, String timeframe, Long timestamp)
            throws IOException, InterruptedException {
        logger.info("getCandleStickByTimestamp for: " + "instrument: " + instrumentName + " timeframe: "
                + timeframe + "timestamp: timestamp");
        CandleStickResponse candleStickResponse = getCandleStick(instrumentName, timeframe);
        ArrayList<CandleStickData> candleStickData = candleStickResponse.getResult().getData();
        for (CandleStickData csd : candleStickData) {
            if (csd.getT().equals(timestamp)) {
                return csd;
            }
        }
        return null;
    }
}
