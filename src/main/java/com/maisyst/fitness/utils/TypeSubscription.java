package com.maisyst.fitness.utils;

public enum TypeSubscription {
    STANDARD("Un mois"),
    PRIME("Six mois"),
    GOLD("Une annee");

    private String value;

    TypeSubscription(String s) {
        this.value=s;
    }

    public String getValue() {
        return value;
    }
}
