package com.tests;

import com.model.CandleStickData;
import com.model.CandleStickResponse;
import com.model.TradeData;
import com.model.TradeResponse;
import com.rest.RestClient;
import com.services.CandleStickService;
import com.services.TradeService;
import com.utils.SortUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class CandleSticksTests {

    private final CandleStickService candleStickService = new CandleStickService();
    private final TradeService tradeService = new TradeService();
    private final RestClient restClient = new RestClient();
    private final SortUtil sortUtil = new SortUtil();

    @Test
    void verifyResponseMethod() throws IOException, InterruptedException {
        CandleStickResponse candleStickResponse = restClient.getCandleStick("BTC_USDT", "1m");
        TradeResponse tradeResponse = restClient.getTrades("BTC_USDT");
        assert candleStickResponse.getMethod().equals("public/get-candlestick");
        assert tradeResponse.getMethod().equals("public/get-trades");
    }

    @Test
    void verifyInstrumentName() throws IOException, InterruptedException {
        CandleStickResponse btcCandleStickResponse = restClient.getCandleStick("BTC_USDT", "1m");
        TradeResponse btcTradeResponse = restClient.getTrades("BTC_USDT");
        assert btcCandleStickResponse.getResult().getInstrumentName().equals("BTC_USDT");
        assert btcTradeResponse.getResult().getInstrumentName().equals("BTC_USDT");

        CandleStickResponse ethCandleStickResponse = restClient.getCandleStick("ETH_CRO", "1m");
        TradeResponse ethTradeResponse = restClient.getTrades("ETH_CRO");
        assert ethCandleStickResponse.getResult().getInstrumentName().equals("ETH_CRO");
        assert ethTradeResponse.getResult().getInstrumentName().equals("ETH_CRO");
    }

    @Test
    void verifyCandleStickTimeFrame() throws IOException, InterruptedException {
        CandleStickResponse oneMinCandleStickResponse = restClient.getCandleStick("BTC_USDT", "1m");
        assert oneMinCandleStickResponse.getResult().getInterval().equals("1m");

        CandleStickResponse fiveCandleStickResponse = restClient.getCandleStick("BTC_USDT", "5m");
        assert fiveCandleStickResponse.getResult().getInterval().equals("5m");
    }

    @Test
    void verifyLatestPeriodOpenPrice() throws IOException, InterruptedException {
        CandleStickData candleStickData = candleStickService.getLatestCandleStick("BTC_USDT", "1m");
        ArrayList<TradeData> tradeData = tradeService.getTradesWithinOneMinute("BTC_USDT", candleStickData.getT());
        Number firstTradePrice = tradeData.get(tradeData.size() - 1).getP().floatValue();
        Number candleStickOpenPrice = candleStickData.getO().floatValue();


        assert firstTradePrice.equals(candleStickOpenPrice);

    }

    @Test
    void verifyCandleStickForSecondLast() throws IOException, InterruptedException {
        long latestTimestamp = candleStickService.getLatestCandleStick("BTC_USDT", "1m").getT();
        long latestMinusOneMin = latestTimestamp - 60000;
        CandleStickData candleStickData =
                candleStickService.getCandleStickByTimestamp("BTC_USDT", "1m", latestMinusOneMin);
        ArrayList<TradeData> tradeData =
                tradeService.getTradesByTimePeriod("BTC_USDT", latestMinusOneMin, latestTimestamp);

        Number firstTradePrice = tradeData.get(tradeData.size() - 1).getP().floatValue();
        Number candleStickOpenPrice = candleStickData.getO().floatValue();

        assert firstTradePrice.equals(candleStickOpenPrice);

        Number lastTradePrice = tradeData.get(0).getP().floatValue();
        Number candleStickClosePrice = candleStickData.getC().floatValue();

        assert lastTradePrice.equals(candleStickClosePrice);

        Number candleStickHighestPrice = candleStickData.getH().floatValue();
        Number candleStickLowestPrice = candleStickData.getL().floatValue();

        Number tradesHighestPrice = sortUtil.getHighestPrice(tradeData);
        Number tradesLowestPrice = sortUtil.getLowestPrice(tradeData);

        assert tradesLowestPrice.equals(candleStickLowestPrice);
        assert tradesHighestPrice.equals(candleStickHighestPrice);
    }

    @Test
    void verifyCandleStickForThirdLast() throws IOException, InterruptedException {
        long latestTimestamp = candleStickService.getLatestCandleStick("BTC_USDT", "1m").getT();
        long latestMinusOneMin = latestTimestamp - 60000;
        long latestMinusTwoMin = latestMinusOneMin - 60000;
        CandleStickData candleStickData =
                candleStickService.getCandleStickByTimestamp("BTC_USDT", "1m", latestMinusTwoMin);
        ArrayList<TradeData> tradeData =
                tradeService.getTradesByTimePeriod("BTC_USDT", latestMinusTwoMin, latestMinusOneMin);

        Number firstTradePrice = tradeData.get(tradeData.size() - 1).getP().floatValue();
        Number candleStickOpenPrice = candleStickData.getO().floatValue();

        assert firstTradePrice.equals(candleStickOpenPrice);

        Number lastTradePrice = tradeData.get(0).getP().floatValue();
        Number candleStickClosePrice = candleStickData.getC().floatValue();

        assert lastTradePrice.equals(candleStickClosePrice);

        Number candleStickHighestPrice = candleStickData.getH().floatValue();
        Number candleStickLowestPrice = candleStickData.getL().floatValue();

        Number tradesHighestPrice = sortUtil.getHighestPrice(tradeData);
        Number tradesLowestPrice = sortUtil.getLowestPrice(tradeData);

        assert tradesLowestPrice.equals(candleStickLowestPrice);
        assert tradesHighestPrice.equals(candleStickHighestPrice);
    }
}
