package org.brilloconnetz.test.question1;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.brilloconnetz.test.config.BrilloConfig;
import org.brilloconnetz.test.model.Token;
import org.brilloconnetz.test.model.User;
import org.brilloconnetz.test.utils.Utility;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@AllArgsConstructor
public class ValidateToken {

    private static final Utility utils = null;
    private static final BrilloConfig brilloConfig = null;

    public static void main(String []args )
    {

    }

    public CompletableFuture<String> validateToken(User user, Token token) {
        final var jwtToken = token.getToken();

        String response = "";
        try {
            final var claims = utils.createClaimsFromToken(jwtToken);
            if (isTokenValidAgainstClaims(user, claims)) {
                response = "Verification pass";
            } else {
                response = "Verification failed";
            }
        } catch (Exception ex) {
            response = ex.getMessage();
        }
        return CompletableFuture.completedFuture(response);
    }


    public boolean isTokenValidAgainstClaims(User user, Claims claims) {

        final var id = claims.get("id", String.class);
        final var email = claims.get("email", String.class);
        final var signature = claims.get("signature", String.class);

        final var tokenSignature = Utility.generateRandomString(brilloConfig.getJwtSignatureSource(),10);


        AtomicBoolean isValid = new AtomicBoolean();


        isValid.set(email.equals(user.getEmail()) && id.equals(user.getUsername())
                && Utility.matches(tokenSignature, signature));

        System.out.println("Token claim Validation returns " + isValid.get());
        return isValid.get();
    }
}
