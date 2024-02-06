package org.brilloconnetz.test.question2;

import org.brilloconnetz.test.model.PancakeUser;
import org.brilloconnetz.test.model.Shopkeeper;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ConcurrentCakeOrdering {
    private static final int MAX_PANCAKES = 12;

    public static void main(String[] args) {
        System.out.println("Welcome to the Pancake Shop!");

        Scanner scanner = new Scanner(System.in);

        System.out.println("");

        Shopkeeper shopkeeper = new Shopkeeper();
        PancakeUser[] users = new PancakeUser[3];
        for (int i = 0; i < 3; i++) {
            users[i] = new PancakeUser(i + 1);
        }

        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("Starting time: " + startTime);

        CompletableFuture<Integer> pancakesMadeFuture = CompletableFuture.supplyAsync(() -> {
            return shopkeeper.makePancakes();
        });

        CompletableFuture<Integer>[] pancakesEatenFutures = new CompletableFuture[3];
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            pancakesEatenFutures[i] = CompletableFuture.supplyAsync(() -> {
                return users[finalI].eatPancakes();
            });
        }

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(pancakesMadeFuture, pancakesEatenFutures[0],
                pancakesEatenFutures[1], pancakesEatenFutures[2]);

        try {
            allFutures.get(); // Wait for all tasks to complete
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        int pancakesMade = 0;
        int pancakesEaten = 0;
        for (CompletableFuture<Integer> future : pancakesEatenFutures) {
            try {
                pancakesEaten += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            pancakesMade = pancakesMadeFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("Ending time: " + endTime);
        System.out.println("Pancakes made by shopkeeper: " + pancakesMade);
        System.out.println("Pancakes eaten by users: " + pancakesEaten);
        if (pancakesMade >= pancakesEaten) {
            System.out.println("Shopkeeper met the needs of all users!");
            System.out.println("Pancakes wasted: " + (pancakesMade - pancakesEaten));
        } else {
            int pancakeOrdersNotMet = pancakesEaten - pancakesMade;
            System.out.println("Shopkeeper was not able to meet the needs of all users.");
            System.out.println("Pancake orders not met: " + pancakeOrdersNotMet);
        }
    }

    public int makePancakes() {
        Random random = new Random();
        int pancakes = random.nextInt(MAX_PANCAKES + 1);
        System.out.println("Pancakes made by shopkeeper: " + pancakes);
        return pancakes;
    }
}

