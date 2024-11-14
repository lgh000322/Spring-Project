package UniP_server_chat.Unip_party_chat.global.jwt.info;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey,
        String issuer,
        Long accessTokenExpiration,
        Long refreshTokenExpiration) {

}