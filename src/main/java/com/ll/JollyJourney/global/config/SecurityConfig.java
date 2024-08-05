package com.ll.JollyJourney.global.config;

import com.ll.JollyJourney.global.security.handler.CustomAuthenticationEntryPoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityConfig {

    private static final String[] publicEndpoints = {
            "/api/v1/auth/signin",
            "/api/v1/auth/signup",

            /* swagger v3*/
            "/swagger-ui/**",
            "/v3/api-docs/**",
    };

    private final List<String> corsOrigins = List.of("http://localhost:3000", "http://localhost:8080");

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtSecurityConfig jwtSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(sess -> sess.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(getPublicEndpoints()).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(auth -> auth
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );
        jwtSecurityConfig.configure(httpSecurity);

        return httpSecurity.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // 모든 출처 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 모든 HTTP 메서드 허용
        configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*AntPathRequestMatcher를 사용하면 추후 메소드 설정 등 더 유연한 경로를 매칭할 수 있다.*/
    private RequestMatcher[] getPublicEndpoints() {
        return Stream.concat(
                Stream.of(publicEndpoints).map(AntPathRequestMatcher::new),
                Stream.of(new AntPathRequestMatcher("/api/v1/post", "GET")) // GET 요청에 대해서만 permitAll
        ).toArray(RequestMatcher[]::new);
    }
}

//@Configuration
//@EnableWebSecurity(debug = true)
//@EnableMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
//public class SecurityConfig {
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private List<String> corsOrigins = List.of("http://localhost:3000", "http://localhost:8080");
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//
//                .sessionManagement(sess -> sess.sessionCreationPolicy(
//                        SessionCreationPolicy.STATELESS))
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(new AntPathRequestMatcher("/journal/create")).hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(new AntPathRequestMatcher("/journal/modify/{id}")).hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(new AntPathRequestMatcher("/journal/delete/{id}")).hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(new AntPathRequestMatcher("/journal/{journalId}/comments")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/journal/{journalId}/comments/{commentId}")).permitAll()
//                        .requestMatchers("/**").permitAll() // url 수정
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(auth -> auth
//                        .authenticationEntryPoint(customAuthenticationEntryPoint)
//                );
//        // jwtSecurityConfig.configure(httpSecurity);
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() { // Localhost 환경 cors
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*")); // 모든 출처 허용
//        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
//        // configuration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION, AuthConstants.REFRESH_TOKEN));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // antMatchers 부분도 deprecated 되어 requestMatchers로 대체
//        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**");
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}











//        http
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers(new AntPathRequestMatcher("/concert/search")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/members/revoke")).authenticated()
//                        .requestMatchers(new AntPathRequestMatcher("/order/**")).authenticated()
//                        .requestMatchers(new AntPathRequestMatcher("/concert/**/seat")).authenticated()
//                        .requestMatchers(new AntPathRequestMatcher("/members/**")).anonymous()
//                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority(MemberRole.ADMIN.getValue())
//                        .requestMatchers(new AntPathRequestMatcher("/help/**")).authenticated() //고객 센터
//                        .requestMatchers(new AntPathRequestMatcher("/review/**")).authenticated() //리뷰 작성
//                        .requestMatchers(new AntPathRequestMatcher("/journal/create")).hasAuthority(MemberRole.ADMIN.getValue()) // 관리자 저널 생성
//                        .requestMatchers(new AntPathRequestMatcher("/journal/modify/**")).hasAuthority(MemberRole.ADMIN.getValue()) // 관리자 저널 수정
//                        .requestMatchers(new AntPathRequestMatcher("/journal/delete/**")).hasAuthority(MemberRole.ADMIN.getValue()) // 관리자 저널 삭제
//                        .requestMatchers(new AntPathRequestMatcher("/journal/list")).permitAll() // 모든 사용자가 저널 리스트 조회 가능
//                        .requestMatchers(new AntPathRequestMatcher("/likes/journal/**")).authenticated() // 좋아요 기능은 인증된 사용자만 사용 가능
//                        .requestMatchers(new AntPathRequestMatcher("/api/s3/upload")).permitAll() // 파일 업로드 엔드포인트 허용
//                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
//                )
//                .headers((headers) -> headers
//                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/members/login")
//                        .usernameParameter("email")
//                        .defaultSuccessUrl("/"))
//                .logout((logout) -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
//                        .logoutSuccessUrl("/")
//                        .invalidateHttpSession(true)
//                )
//                .exceptionHandling(exceptions
//                        -> exceptions
//                        .accessDeniedHandler(new CustomAccessDeniedHandler()) // 정보글 등록시 권한 없음 팝업 표시
//                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                );