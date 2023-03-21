package com.example.reportgenerator;


import com.example.reportgenerator.model.StockValue;
import com.example.reportgenerator.service.StocksService;
import com.example.reportgenerator.util.StockValueParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ServiceTest {
    private static final RestTemplate restTemplate= new RestTemplate();
    private static final StocksService stocksService = new StocksService(restTemplate);

    @Test
    void testParser() throws Exception {
        String aaplResult = "{\"Monthly Adjusted Time Series\": {\"2022-02-28\": {\"1. open\": \"130.17\", \"2. high\": \"145.09\", \"3. low\": \"123.61\", \"4. close\": \"141.05\", \"5. adjusted close\": \"141.05\", \"6. volume\": \"241670440\", \"7. dividend amount\": \"0.2200\"}}}";
        Map<String, StockValue> result = StockValueParser.parse(aaplResult);
        Assertions.assertNotEquals(result, null);
        Assertions.assertEquals(result.keySet().size(), 1);
        Assertions.assertEquals(result.values().size(), 1);
    }

    @Test
    void testGetStocksValues() throws Exception {
        String str = "AAPL,MSFT";
        String date = "2022-02";

        Map<String, StockValue> result = stocksService.getStocksValues(str, date);
        Assertions.assertNotEquals(result, null);
        Assertions.assertEquals(result.keySet().size(), 2);
        Assertions.assertEquals(result.values().size(), 2);
    }

}

