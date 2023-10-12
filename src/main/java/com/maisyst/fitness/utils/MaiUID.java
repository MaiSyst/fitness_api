package com.maisyst.fitness.utils;

public final class MaiUID {
    public static String generate() {
       return gen(20);
    }
     public static String generate(int length) {
        return gen(length);
    }

    private static String gen(int length){
        StringBuilder stringBuilder = new StringBuilder();
        final char[] chars = "qwertyuio12345098760pasdfghjklzxcvbnm@#$-+=)QWERTYUIOP(*&%!~_]ASDFGHJKL[>ZXCVBNM}<.?:{".toCharArray();

        for (var i = 0; i < length; i++) {
            final var random = (int) (Math.random() * chars.length);
            stringBuilder.append(chars[random]);
        }
        return stringBuilder.toString();
    }
}
