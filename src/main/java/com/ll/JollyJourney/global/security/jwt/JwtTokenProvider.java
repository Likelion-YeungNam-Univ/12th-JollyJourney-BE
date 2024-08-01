package com.ll.JollyJourney.global.security.jwt;

import com.ll.JollyJourney.global.security.authentication.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key accessKey;
    private final Key refreshKey;
    private final Duration accessDuration;
    private final Duration refreshDuration;
    private final UserDetailsServiceImpl userDetailsService;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private JwtTokenProvider(
            @Value("${app.token.access.secret-key}") String accessKey,
            @Value("${app.token.refresh.secret-key}") String refreshKey,
            @Value("${app.token.access.expiration}") String accessDuration,
            @Value("${app.token.refresh.expiration}") String refreshDuration,
            UserDetailsServiceImpl userDetailsService
    ){
        this.accessKey = Keys.hmacShaKeyFor(accessKey.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshKey.getBytes());
        this.accessDuration = Duration.ofSeconds(Long.parseLong(accessDuration));
        this.refreshDuration = Duration.ofSeconds(Long.parseLong(refreshDuration));
        this.userDetailsService = userDetailsService;
    }

    public static String parseJwtFromRequest(HttpServletRequest request) {
        final String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        return parseJwt(headerAuth);
    }

    public String issueAccessToken(String memberId){

        final Instant now = Instant.now();
        final Instant expiryDate = createExpiryDate(now, accessDuration);

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberId, expiryDate))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(accessKey, signatureAlgorithm)
                .compact();
    }

    public String issueRefreshToken(String memberId){

        final Instant now = Instant.now();
        final Instant expiryDate = createExpiryDate(now, refreshDuration);

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberId, expiryDate))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(refreshKey, signatureAlgorithm)
                .compact();
    }

    public Claims getClaimsFromAccessToken(String accessToken){
        return getClaimsFormToken(accessToken, accessKey);
    }

    public Claims getClaimsFromRefreshToken(String refreshToken){
        return getClaimsFormToken(refreshToken, refreshKey);
    }

    public Authentication getAuthentication(Claims claims) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get("sub", String.class));
        log.info("{}", userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    private static String parseJwt(final String headerAuth) {

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring("Bearer ".length()).trim();
        } else {
            return "";
        }
    }

    private Map<String, Object> createHeader() {
        return Map.of(
                Header.TYPE, Header.JWT_TYPE
        );
    }

    private Claims createClaims(String memberId, Instant expiryDate){
        return Jwts.claims()
                .setSubject(memberId)
                .setExpiration(Date.from(expiryDate));
    }

    private Instant createExpiryDate(final Instant since, final Duration tokenExpiration) {
        return since.plus(tokenExpiration);
    }

    private static Claims getClaimsFormToken(String token, Key key){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
