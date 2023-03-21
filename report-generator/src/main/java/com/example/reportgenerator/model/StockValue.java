package com.example.reportgenerator.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StockValue {
    private LocalDate date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjustedClose;
    private String volume;
    private String dividendAmount;

}
