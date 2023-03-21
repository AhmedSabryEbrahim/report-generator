package com.example.reportgenerator.controller;

import com.example.reportgenerator.model.StockValue;
import com.example.reportgenerator.service.StocksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stock")
@Slf4j
public class StockContoller {

    private final StocksService alphaVantageService;

    @Autowired
    public StockContoller(StocksService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    @GetMapping("/{symbols}/{date}")
    public ResponseEntity<Map<String, StockValue>> getStockValuesMap(@PathVariable String symbols,
                                                                     @PathVariable String date)  {
        Map<String, StockValue> response = alphaVantageService.getStocksValues(symbols, date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
