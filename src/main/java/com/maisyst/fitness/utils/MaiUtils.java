package com.maisyst.fitness.utils;

import com.maisyst.MaiDateCompare;

import java.sql.Date;

public final class MaiUtils {
    public static TypeSubscription stringToTypeSubscription(String type) {
        return switch (type.toLowerCase()) {
            case "gold" -> TypeSubscription.GOLD;
            case "prime" -> TypeSubscription.PRIME;
            default -> TypeSubscription.STANDARD;
        };
    }
    public static MaiDay stringToMaiDay(String type) {
        return switch (type.toLowerCase()) {
            case "monday" -> MaiDay.MONDAY;
            case "tuesday" -> MaiDay.TUESDAY;
            case "wednesday" -> MaiDay.WEDNESDAY;
            case "thursday" -> MaiDay.THURSDAY;
            case "friday" -> MaiDay.FRIDAY;
            case "saturday" -> MaiDay.SATURDAY;
            default  -> MaiDay.SUNDAY;
        };
    }
     public static String maiDayToString(MaiDay type) {
        return switch (type) {
            case MONDAY -> "MONDAY";
            case TUESDAY -> "TUESDAY";
            case WEDNESDAY -> "WEDNESDAY";
            case THURSDAY ->"THURSDAY";
            case FRIDAY -> "FRIDAY";
            case SATURDAY -> "SATURDAY";
            default  -> "SUNDAY";
        };
    }
    public static String maiDayToFrench(String type) {
       return switch (type.toLowerCase()) {
            case "monday" -> "Lundi";
            case "tuesday" -> "Mardi";
            case "wednesday" -> "Mercredi";
            case "thursday" -> "Jeudi";
            case "friday" -> "Vendredi";
            case "saturday" -> "Samedi";
            default  -> "Dimanche";
        };
    }

    public static Date[] getDateSubscription(TypeSubscription type) {
        var date = new java.util.Date();
        return switch (type) {
            case GOLD -> new Date[]{getDateSql(date), getDateSql(MaiDateCompare.addYears(date, 1))};
            case PRIME -> new Date[]{getDateSql(date), getDateSql(MaiDateCompare.addMonths(date, 6))};
            default -> new Date[]{getDateSql(date), getDateSql(MaiDateCompare.addMonths(date, 1))};
        };
    }

    public static double getPriceSubscription(TypeSubscription type) {
        return switch (type) {
            case GOLD -> 100_000;
            case PRIME -> 60_000;
            default -> 15_000;
        };
    }

    public static AuthRole stringToAuthRole(String role) {
        if (role.toLowerCase().equals("admin")) {
            return AuthRole.ADMIN;
        } else {
              return AuthRole.USER;
        }
    }

    public static Date getDateSql(java.util.Date date) {
        return new Date(date.getTime());
    }
}
