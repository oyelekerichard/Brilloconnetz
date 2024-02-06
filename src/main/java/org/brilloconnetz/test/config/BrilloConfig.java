package org.brilloconnetz.test.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "validation")
public class BrilloConfig {

    private String jwtSecret;
    private Long jwtExpirationTime;
    private String jwtSignatureSource;
}
