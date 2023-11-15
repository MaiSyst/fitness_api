package com.maisyst.fitness.utils;

import java.util.Random;

public final class MaiUID {
    public static String generate() {
       return gen(15);
    }
     public static String generate(int length) {
        return gen(length);
    }

    private static String gen(int length){
        StringBuilder stringBuilder = new StringBuilder();
        final char[] chars = "12345098760".toCharArray();
        final char[] charsAlpha="QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
        var random=new Random();
        final int alphaCharIndex=random.nextInt(charsAlpha.length);
        for (var i = 0; i < length; i++) {
            final var rand = random.nextInt(chars.length);
            stringBuilder.append(chars[rand]);
        }
        stringBuilder.append(charsAlpha[alphaCharIndex]);
        return stringBuilder.toString();
    }
}
