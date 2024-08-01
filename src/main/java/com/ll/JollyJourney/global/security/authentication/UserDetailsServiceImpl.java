package com.ll.JollyJourney.global.security.authentication;


import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(
                memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다.")));
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다"));
////        return UserPrincipal.create(member);
//        return new SecurityUser(
//                member,
//                member.getUserId(),
//                member.getEmail(),
//                member.getPassword(),
//                member.getAuthorities());
//    }
}
