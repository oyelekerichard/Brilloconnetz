package org.brilloconnetz.test.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.brilloconnetz.test.config.BrilloConfig;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;


@AllArgsConstructor
@NoArgsConstructor
public class Utility {

    private final BrilloConfig brilloConfig = null;

    private static BCrypt.Hasher encoder;
    private static BCrypt.Verifyer verifyer;

    public String encryptToken(String plainData) {
        return encoder.hashToString(10, plainData.toCharArray());
    }

    public static boolean matches(String hashedData, String plainData) {
        return verifyer.verify(plainData.toCharArray(), hashedData).verified;
    }

    // Method to validate email
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate password
    public static boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Method to validate date of birth
    public static boolean isValidDob(String dob) {
        // Assuming the date of birth input is in the format dd-MM-yyyy
        String[] parts = dob.split("-");
        if (parts.length != 3) {
            return false;
        }
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            // Validate the age (16 years or greater)
            // You can customize this validation according to the specific requirements
            // In this example, we're assuming the current date is 2022-01-01

            int currentYear = 2022;
            int currentMonth = 1;
            int currentDay = 1;
            int age = currentYear - year;
            if (month > currentMonth || (month == currentMonth && day > currentDay)) {
                age--;
            }
            return age >= 16;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Key generateKey(String secret_key) {
        return new SecretKeySpec(Base64.getDecoder().decode(secret_key), SignatureAlgorithm.HS256.getJcaName());
    }

    public Claims createClaimsFromToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey(brilloConfig.getJwtSecret()))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public static String generateRandomString(String randomSource, int length) {
        // Use the current LocalDateTime's hash code as a seed
        long seed = LocalDateTime.now().hashCode();
        Random random = new Random(seed);

        // Define the characters that can be used in the random string


        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // Generate a random index within the range of available characters
            int randomIndex = random.nextInt(randomSource.length());

            // Append the randomly selected character to the string
            stringBuilder.append(randomSource.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

}
