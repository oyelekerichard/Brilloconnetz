package org.brilloconnetz.test.model;

import java.util.Random;

public class PancakeUser {
    private static final int MAX_PANCAKES = 5;
    private int id;

    public PancakeUser(int id) {
        this.id = id;
    }

    public int eatPancakes() {
        Random random = new Random();
        int pancakes = random.nextInt(MAX_PANCAKES + 1);
        System.out.println("User " + id + " ate " + pancakes + " pancakes");
        return pancakes;
    }
}
