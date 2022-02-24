package com.example.blogApplication.service;

import org.springframework.data.domain.Sort;

public enum DateOrderConstant {
    ASCENDING("ASC"),
    DESCENDING("DESC"),
    DATE_COLUMN("dateStamp");

    private final String value;
    private DateOrderConstant(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
