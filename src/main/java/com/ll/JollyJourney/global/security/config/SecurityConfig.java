package com.ll.JollyJourney.global.security.config;

import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/concert/search")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/members/revoke")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/order/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/concert/**/seat")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/members/**")).anonymous()
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority(MemberRole.ADMIN.getValue())
                        .requestMatchers(new AntPathRequestMatcher("/help/**")).authenticated() //고객 센터
                        .requestMatchers(new AntPathRequestMatcher("/review/**")).authenticated() //리뷰 작성
                        .requestMatchers(new AntPathRequestMatcher("/journal/create")).hasAuthority(MemberRole.ADMIN.getValue()) // 관리자 저널 생성
                        .requestMatchers(new AntPathRequestMatcher("/journal/modify/**")).hasAuthority(MemberRole.ADMIN.getValue()) // 관리자 저널 수정
                        .requestMatchers(new AntPathRequestMatcher("/journal/delete/**")).hasAuthority(MemberRole.ADMIN.getValue()) // 관리자 저널 삭제
                        .requestMatchers(new AntPathRequestMatcher("/journal/list")).permitAll() // 모든 사용자가 저널 리스트 조회 가능
                        .requestMatchers(new AntPathRequestMatcher("/likes/journal/**")).authenticated() // 좋아요 기능은 인증된 사용자만 사용 가능
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                )
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .formLogin(formLogin -> formLogin
                        .loginPage("/members/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .exceptionHandling(exceptions
                        -> exceptions
                        .accessDeniedHandler(new CustomAccessDeniedHandler()) // 정보글 등록시 권한 없음 팝업 표시
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // antMatchers 부분도 deprecated 되어 requestMatchers로 대체
        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**");
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}