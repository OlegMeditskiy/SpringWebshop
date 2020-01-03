package com.example.sfff.domain;

public enum Category {
    ice_cream("Ice cream"), chocolate("Chocolate");
    private String displayValue;

    Category(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue(){
        return  displayValue;
    }
}
