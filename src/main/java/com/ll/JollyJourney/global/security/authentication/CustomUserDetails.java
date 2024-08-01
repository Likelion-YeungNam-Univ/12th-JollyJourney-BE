package com.ll.JollyJourney.global.security.authentication;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public Long getMemberId() {
        return member.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>(2);
        authorities.add(new SimpleGrantedAuthority(member.getRole().name()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.member.getName();
    }

    /*
    계정의 만료 여부 리턴
    */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /*
    계정의 잠김 여부 리턴
    */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

