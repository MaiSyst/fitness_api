package com.maisyst.fitness.utils;

public enum TypeSubscription {
    STANDARD("Un(1) mois"),
    PRIME("Six(6) mois"),
    GOLD("Une année");
    

    private String value;

    TypeSubscription(String s) {
        this.value=s;
    }

    public String getValue() {
        return value;
    }
}
