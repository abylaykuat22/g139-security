package kz.bitlab.g139market.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    /**
     * Секретный ключ для подписи JWT токенов
     */
    private String secret;

    /**
     * Время жизни access token в миллисекундах (по умолчанию 30 минут)
     */
    private Long accessTokenExpiration = 1800000L;

    /**
     * Время жизни refresh token в миллисекундах (по умолчанию 7 дней)
     */
    private Long refreshTokenExpiration = 604800000L;
}

