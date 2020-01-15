package com.example.sfff.domain;

public enum Category {
    dairy_free("Dairy-free"),
    training("Traning"),
    ice_cream("Ice cream"),
    chocolate("Chocolate");
    private String displayValue;

    private Category(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue(){
        return  displayValue;
    }
}
