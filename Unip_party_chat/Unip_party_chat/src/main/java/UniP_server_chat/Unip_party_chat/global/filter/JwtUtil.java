package UniP_server_chat.Unip_party_chat.global.filter;

import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.repository.MemberRepository;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.MemberErrorCode;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.OAuthErrorCode;
import UniP_server_chat.Unip_party_chat.global.jwt.info.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private Key key;

    private static final String MEMBER_ROLE = "role";
    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Long memberId, List<String> roles) {
        long now = (new Date()).getTime();

        // Access token 유효 기간 설정
        Date accessValidity = new Date(now + jwtProperties.accessTokenExpiration());

        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(accessValidity)
                .setIssuer(jwtProperties.issuer())
                .setSubject(memberId.toString())
                .addClaims(Map.of(MEMBER_ROLE, roles))
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(Long memberId, List<String> roles) {
        long now = (new Date()).getTime();

        // Refresh token 유효 기간 설정
        Date refreshValidity = new Date(now + jwtProperties.refreshTokenExpiration());

        // Refresh token 생성
        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(refreshValidity)
                .setIssuer(jwtProperties.issuer())
                .setSubject(memberId.toString())
                .addClaims(Map.of(MEMBER_ROLE, roles))
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    public void validateToken(final String token) {
        try {
            log.info("now date: {}", new Date());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            claims.getBody().getExpiration();
        } catch (Exception e) {
            throw new CustomException(OAuthErrorCode.TOKEN_VALID_FAIL);
        }
    }

    public Member getMember(String token) {
        Long id = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject());

        log.info("in getMember() socialId: {}", id);

        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
