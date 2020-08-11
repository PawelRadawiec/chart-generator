package com.info.chartgenerator.model;

public enum ChartType {

    LINE("LINE"),
    COLUMN("COLUMN");

    private String value;

    ChartType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
