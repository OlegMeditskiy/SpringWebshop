package com.example.sfff.domain;

public enum OrderStatus {
    checking("Checking"),
    processing("Processing"),
    packing("Packing"),
    sent("Sent out"),
    delivered("Delivered"),
    returned("Returned");
    private String displayValue;

    private OrderStatus(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue(){
        return  displayValue;
    }


}
