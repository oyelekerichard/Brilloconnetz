package org.brilloconnetz.test.question1;

import lombok.RequiredArgsConstructor;
import org.brilloconnetz.test.utils.Utility;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
public class ConcurrentValidations_1B {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        // Collect inputs concurrently
        CompletableFuture<String> usernameFuture = collectInput("Enter username: ", scanner);
        CompletableFuture<String> emailFuture = collectInput("Enter email: ", scanner);
        CompletableFuture<String> passwordFuture = collectInput("Enter password: ", scanner);
        CompletableFuture<String> dateOfBirthFuture = collectInput("Enter date of birth (YYYY-MM-DD): ", scanner);

        // Validate inputs concurrently
        CompletableFuture.allOf(usernameFuture, emailFuture, passwordFuture, dateOfBirthFuture)
                .thenAccept(v -> {
                    try {
                        String username = usernameFuture.get();
                        String email = emailFuture.get();
                        String password = passwordFuture.get();
                        String dateOfBirth = dateOfBirthFuture.get();

                        // Perform validations
                        validateUsername(username);
                        Utility.isValidEmail(email);
                        Utility.isStrongPassword(password);
                        Utility.isValidDob(dateOfBirth);

                        System.out.println("All inputs are valid!");
                    } catch (Exception e) {
                        System.out.println("Validation error: " + e.getMessage());
                    }
                });
    }

    private static void validateUsername(String username) {
        if (username.isEmpty() || username.length() < 4) {
            throw new IllegalArgumentException("Username must be at least 4 characters long.");
        }
    }

    private static CompletableFuture<String> collectInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return CompletableFuture.completedFuture(scanner.nextLine());
    }
}