package org.brilloconnetz.test.question2;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class NonConcurrentPancakeOrdering {

    private static final int MAX_PANCAKES = 12;
    private static final int MAX_PANCAKES_PER_USER = 5;

    public static void main(String[] args) {
        Random random = new Random();
        int totalPancakes = 0;
        int totalPancakesEaten = 0;
        int totalPancakesWasted = 0;
        int totalOrdersNotMet = 0;

        for (int i = 0; i < 6; i++) {
            System.out.println("30 seconds slot starts");
            System.out.println("Pancakes made by shopkeeper: ");
            int pancakesMade = random.nextInt(MAX_PANCAKES + 1); // Generate random number of pancakes made
            totalPancakes += pancakesMade;
            System.out.println(pancakesMade);

            int[] userOrders = new int[3];
            int pancakesEaten = 0;
            for (int j = 0; j < 3; j++) {
                int maxPancakesPerUser = Math.min(MAX_PANCAKES_PER_USER, pancakesMade - pancakesEaten);
                int pancakesToEat = random.nextInt(maxPancakesPerUser + 1); // Generate random number of pancakes to eat
                userOrders[j] = pancakesToEat;
                pancakesEaten += pancakesToEat;
            }

            System.out.println("Pancakes eaten by users: ");
            for (int j = 0; j < 3; j++) {
                System.out.println("User " + j + ": " + userOrders[j]);
                totalPancakesEaten += userOrders[j];
            }

            int ordersNotMet = Math.max(0, (pancakesMade - pancakesEaten));
            totalOrdersNotMet += ordersNotMet;
            System.out.println("Orders not met: " + ordersNotMet);

            int pancakesWasted = Math.max(0, (pancakesMade - pancakesEaten - ordersNotMet));
            totalPancakesWasted += pancakesWasted;
            System.out.println("Pancakes wasted: " + pancakesWasted);

            System.out.println("30 seconds slot ends");
        }

        System.out.println("Total pancakes made: " + totalPancakes);
        System.out.println("Total pancakes eaten by users: " + totalPancakesEaten);
        System.out.println("Total pancakes wasted: " + totalPancakesWasted);
        System.out.println("Total orders not met: " + totalOrdersNotMet);
    }

}
