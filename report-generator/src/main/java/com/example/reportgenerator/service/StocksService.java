package com.example.reportgenerator.service;

import com.example.reportgenerator.model.StockValue;
import com.example.reportgenerator.util.StockValueParser;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StocksService {
    private final String baseUrl = "https://www.alphavantage.co/query";
    private final String apiKey = "J2HINXJEHZ3PID0Q";
    private final RestTemplate restTemplate;

    private final Map<String, Map<String, StockValue>> stockCache;

    @Autowired
    public StocksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.stockCache = new HashMap<>();
    }

    public String getStockValueFromAPI(String symbol) {
        String url = baseUrl + "?function=TIME_SERIES_MONTHLY_ADJUSTED&symbol=" + symbol + "&apikey=" + apiKey;

        return restTemplate.getForObject(url, String.class);
    }

    private StockValue getStockValueFromCache(String symbol, String date) {
        if (stockCache != null && !stockCache.isEmpty()) {

            Map<String, StockValue> result = stockCache.get(symbol);
            if (result != null && !result.isEmpty()) {
                return result.get(date);
            }
        }
        log.error("Can't find this info in the Cache {} -> {} !!", symbol, date);
        throw new RuntimeException();
    }

    public StockValue getStockValue(String symbol, String date) throws Exception {

        if (!this.stockCache.containsKey(symbol)) {
            String result = this.getStockValueFromAPI(symbol);
            if (StringUtils.isNotBlank(result)) {
                this.stockCache.put(symbol, StockValueParser.parse(result));
            } else {
                log.info("Couldn't retrieve from the provider, {} -> {} .", symbol, date);
            }
        }
        return getStockValueFromCache(symbol, date);
    }

    public Map<String, StockValue> getStocksValues(String str, String date) {
        String[] symbols = str.split(",");
        Map<String, StockValue> map = new HashMap<>();
        for (String s : symbols) {
            try {
                map.put(s, this.getStockValue(s, date));
                log.info("{} processed successfully!", s);
            } catch (Exception e) {

                log.error("{} has some failures, {}", s, e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return map;
    }
//  TODO if needed to implement generatePDF in server side , but to be removed and replaced by same functionality in FE

//    public File generatePDF(String season) throws IOException {
//
//        File f = new File("tournament-portfolio.pdf");
//        String pdfStringData = this.getTournamentPortfolio(season);
//        byte[] pdfData = pdfStringData.getBytes();
//        FileOutputStream fileOutputStream = new FileOutputStream(f);
//        fileOutputStream.write(pdfData);
//        fileOutputStream.close();
//
//        return f;
//    }
}