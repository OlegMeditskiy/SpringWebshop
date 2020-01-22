package com.example.sfff.domain;

import java.util.ArrayList;
import java.util.List;

public enum Category {
    milk("Milk"),
    training("Training"),
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
