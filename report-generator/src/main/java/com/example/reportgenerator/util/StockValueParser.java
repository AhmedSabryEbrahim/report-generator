package com.example.reportgenerator.util;

import com.example.reportgenerator.model.StockValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StockValueParser {
    public static Map<String, StockValue> parse(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);
        JsonNode monthlyAdjustedTimeSeries = root.get("Monthly Adjusted Time Series");
        Iterator<String> dateIterator = monthlyAdjustedTimeSeries.fieldNames();
        Map<String, StockValue> stockValuesMap = new HashMap<>();
        while (dateIterator.hasNext()) {
            String dateStr = dateIterator.next();
            JsonNode stockNode = monthlyAdjustedTimeSeries.get(dateStr);
            StockValue stockValue = new StockValue();
            stockValue.setDate(LocalDate.parse(dateStr));
            stockValue.setOpen(stockNode.get("1. open").asText());
            stockValue.setHigh(stockNode.get("2. high").asText());
            stockValue.setLow(stockNode.get("3. low").asText());
            stockValue.setClose(stockNode.get("4. close").asText());
            stockValue.setAdjustedClose(stockNode.get("5. adjusted close").asText());
            stockValue.setVolume(stockNode.get("6. volume").asText());
            stockValue.setDividendAmount(stockNode.get("7. dividend amount").asText());

            // Get the year and month from the date string in format yyyy-MM
            String yearMonth = dateStr.substring(0, 7);

            stockValuesMap.put(yearMonth, stockValue);

        }
        return stockValuesMap;
    }

}