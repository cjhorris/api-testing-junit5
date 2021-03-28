package com.rest;

import com.google.gson.Gson;
import com.model.CandleStickResponse;
import com.model.TradeResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;


public class RestClient {
    Logger logger = Logger.getLogger(RestClient.class.getName());

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    final static String HOST = "https://api.crypto.com/v2/public";

    public CandleStickResponse getCandleStick(String instrumentName, String timeframe)
            throws IOException, InterruptedException {
        String url = HOST + "/get-candlestick?" + "instrument_name=" + instrumentName + "&timeframe=" + timeframe;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("getCandleStick response: " + response.body());

        return new Gson().fromJson(response.body(), CandleStickResponse.class);
    }

    public TradeResponse getTrades(String instrumentName) throws IOException, InterruptedException {
        String url = HOST + "/get-trades?" + "instrument_name=" + instrumentName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("getTrades response: " + response.body());

        return new Gson().fromJson(response.body(), TradeResponse.class);
    }


}
