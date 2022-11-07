package com.zerobase.dividend.service;

import com.zerobase.dividend.model.Auth;
import com.zerobase.dividend.model.MemberEntity;
import com.zerobase.dividend.persist.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.memberRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));

    }

    /*회원가입하기*/
    public MemberEntity register(Auth.SignUp member) {
        boolean exists = this.memberRepository.existsByUserName(member.getUserName());
        if (exists) {
            throw new RuntimeException("이미 사용 중인 아이디 입니다.");
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        return this.memberRepository.save(member.toEntity());
    }

    /*인증하기*/
    public MemberEntity authenticate(Auth.SignIn member) {
        return null;
    }
}
