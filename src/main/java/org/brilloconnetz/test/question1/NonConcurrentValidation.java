package org.brilloconnetz.test.question1;

import org.brilloconnetz.test.utils.Utility;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NonConcurrentValidation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String email = "";
        String password = "";
        String dob = "";

        // Collecting and validating the username
        while (username.isEmpty() || username.length() < 4) {
            System.out.print("Input the Username: ");
            username = scanner.nextLine();
            if (username.isEmpty() || username.length() < 4) {
                System.out.println("Username should not be empty and should have at least 4 characters.");
            }
        }

        // Collecting and validating the email
        while (email.isEmpty() || !Utility.isValidEmail(email)) {
            System.out.print("Input the Email: ");
            email = scanner.nextLine();
            if (email.isEmpty() || !Utility.isValidEmail(email)) {
                System.out.println("Email should not be empty and should be a valid email address.");
            }
        }

        // Collecting and validating the password
        while (password.isEmpty() || !Utility.isStrongPassword(password)) {
            System.out.print("Input the Password: ");
            password = scanner.nextLine();
            if (password.isEmpty() || !Utility.isStrongPassword(password)) {
                System.out.println("Password should not be empty and should be a strong password with at least 1 uppercase letter, 1 special character, 1 number, and a minimum of 8 characters.");
            }
        }

        // Collecting and validating the date of birth
        while (dob.isEmpty() || !Utility.isValidDob(dob)) {
            System.out.print("Input the Date of Birth (dd-MM-yyyy): ");
            dob = scanner.nextLine();
            if (dob.isEmpty() || !Utility.isValidDob(dob)) {
                System.out.println("Date of Birth should not be empty and should be in the format dd-MM-yyyy, and the age should be 16 years or greater.");
            }
        }

        // Displaying the collected inputs
        System.out.println("Username: " + true);
        System.out.println("Email: " + true);
        System.out.println("Password: " + true);
        System.out.println("Date of Birth: " + true);
    }

}

