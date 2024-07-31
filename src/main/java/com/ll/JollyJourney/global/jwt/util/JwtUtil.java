//package com.ll.JollyJourney.global.jwt.util;
//
//import com.ll.JollyJourney.domain.member.member.service.MemberService;
//import com.ll.JollyJourney.global.redis.service.RedisService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.Date;
//
//public class JwtUtil {
//    private final String secretKey;
//    private final long accessExpiration;
//    private final long refreshExpiration;
//    private final String issuer;
//    private MemberService memberService;
//    private RedisService redisService;
//
//    public JwtUtil(
//            @Value("${jwt.secret}") String secretKey,
//            @Value("${jwt.accessExpiration}") long accessExpiration,
//            @Value("${jwt.refreshExpiration}") long refreshExpiration,
//            @Value("${jwt.issuer}") String issuer,
//            MemberService memberService,
//            RedisService redisService
//    ) {
//        this.secretKey = secretKey;
//        this.accessExpiration = accessExpiration;
//        this.refreshExpiration = refreshExpiration;
//        this.issuer = issuer;
//        this.memberService = memberService;
//        this.redisService = redisService;
//    }
//
//    /**
//     * AccessToken 생성 메소드
//     * @param username
//     * @param userId
//     * @return
//     */
//    public String generateToken(String username, Long userId) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("userId", userId)
//                .setIssuer(issuer)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
//                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
//                .compact();
//    }
//
//    /**
//     * RefreshToken 생성 메소드
//     * @param username
//     * @return
//     */
//    public String generateRefreshToken(String username) {
//        String refreshToken = Jwts.builder()
//                .setSubject(username)
//                .setIssuer(issuer)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
//                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
//                .compact();
//        redisService.setDataExpire(username, refreshToken, refreshExpiration); // Redis에 저장하고 만료시간 설정
//        return refreshToken;
//    }
//
//    /**
//     * JWT 유효성 검사 메소드
//     * @param token
//     * @return
//     */
//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
//            // 존재하는 회원인지 확인
//            if (memberService.read(claims.getBody().get("userId", Long.class)) == null) {
//                return false;
//            }
//
//            // 만료되었을 시 false
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * HTTP Header에서 JWT 토큰을 가져오는 메소드
//     * @param request
//     * @return
//     */
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("Authorization");
//    }
//
//    /**
//     * JWT 토큰에서 userId 추출
//     * @param token
//     * @return
//     */
//    public Long getUserId(String token) {
//        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
//        return claims.getBody().get("userId", Long.class);
//    }
//
//    /**
//     * RefreshToken 유효성 검사 메소드
//     * @param token
//     * @param username
//     * @return
//     */
//    public boolean validateRefreshToken(String token, String username) {
//        try {
//            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
//            String storedRefreshToken = redisService.getData(username); // Redis에서 토큰을 가져옴
//            return storedRefreshToken != null && storedRefreshToken.equals(token) && !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}