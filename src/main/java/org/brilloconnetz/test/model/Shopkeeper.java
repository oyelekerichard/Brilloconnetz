package org.brilloconnetz.test.model;

import java.util.Random;

public class Shopkeeper {
    private static final int MAX_PANCAKES = 12;

    public int makePancakes() {
        Random random = new Random();
        int pancakes = random.nextInt(MAX_PANCAKES + 1);
        System.out.println("Pancakes made by shopkeeper: " + pancakes);
        return pancakes;
    }
}
