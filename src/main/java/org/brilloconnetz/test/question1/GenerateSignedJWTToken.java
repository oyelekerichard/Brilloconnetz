package org.brilloconnetz.test.question1;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.brilloconnetz.test.config.BrilloConfig;
import org.brilloconnetz.test.model.User;

import java.time.*;
import java.util.*;

import io.jsonwebtoken.Jwts;
import org.brilloconnetz.test.utils.Utility;


@RequiredArgsConstructor
@AllArgsConstructor
public class GenerateSignedJWTToken {

    private static final BrilloConfig brilloConfig = null;
    private static final Utility util = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String username = "";
        String emailAddress = "";
        String userpassword = "";
        String userdob = "";

        // Collecting and validating the username
        while (username.isEmpty() || username.length() < 4) {
            System.out.print("Input the Username: ");
            username = scanner.nextLine();
            if (username.isEmpty() || username.length() < 4) {
                System.out.println("Username should not be empty and should have at least 4 characters.");
            }
        }

        // Collecting and validating the email
        while (emailAddress.isEmpty() || !Utility.isValidEmail(emailAddress)) {
            System.out.print("Input the Email: ");
            emailAddress = scanner.nextLine();
            if (emailAddress.isEmpty() || !Utility.isValidEmail(emailAddress)) {
                System.out.println("Email should not be empty and should be a valid email address.");
            }
        }

        // Collecting and validating the password
        while (userpassword.isEmpty() || !Utility.isStrongPassword(userpassword)) {
            System.out.print("Input the Password: ");
            userpassword = scanner.nextLine();
            if (userpassword.isEmpty() || !Utility.isStrongPassword(userpassword)) {
                System.out.println("Password should not be empty and should be a strong password with at least 1 uppercase letter, 1 special character, 1 number, and a minimum of 8 characters.");
            }
        }

        // Collecting and validating the date of birth
        while (userdob.isEmpty() || !Utility.isValidDob(userdob)) {
            System.out.print("Input the Date of Birth (dd-MM-yyyy): ");
            userdob = scanner.nextLine();
            if (userdob.isEmpty() || !Utility.isValidDob(userdob)) {
                System.out.println("Date of Birth should not be empty and should be in the format dd-MM-yyyy, and the age should be 16 years or greater.");
            }
        }

        // create a user object to pass into the generateUserJwtToken method to return a token for the user
        User createduser = User.builder()
                .email(emailAddress)
                .dob(userdob)
                .password(userpassword)
                .username(username)
                .build();

      System.out.println("TOKEN GENERATED ::: >> "+ generateUserJwtToken(createduser));
    }
    public static String generateUserJwtToken(final User user) {
        final var now = Instant.now();
        final var issuedAtDate = now.atZone(ZoneId.systemDefault()).toLocalDate();
        final var issuedAtTime = now.atZone(ZoneId.systemDefault()).toLocalTime();
        final var signature = Utility.generateRandomString(brilloConfig.getJwtSignatureSource(),10);


        var jwtBuilder = Jwts.builder()
                .claim("password", user.getPassword())
                .claim("email",user.getEmail())
                .claim("signature",signature)
                .setId(user.getUsername());

        jwtBuilder
                .setIssuedAt(Date.from(issuedAtDate.atTime(issuedAtTime).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(util.generateKey(brilloConfig.getJwtSecret()));

        return jwtBuilder.compact();
    }

}
