package com.example.reportgenerator.model;

import lombok.Data;

import java.util.Map;
@Data
public class StockData {

    private Map<String, StockValue> data;
}
