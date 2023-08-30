package com.cnu.sw2023.member.service;

import com.cnu.sw2023.member.DTO.MemberJoinDto;
import com.cnu.sw2023.member.DTO.MemberLoginDto;
import com.cnu.sw2023.member.domain.Member;
import com.cnu.sw2023.member.repository.MemberRepository;
import com.cnu.sw2023.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("%{jwt.secret}")
    private String secretKey;

    private Long expireMs = 1000 * 60 * 60l;
    public void createMember(MemberJoinDto memberJoinDto) {

        if (!memberJoinDto.getPassword1().equals(memberJoinDto.getPassword2())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if (memberRepository.existsMemberByEmail(memberJoinDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 계정입니다");
        }
        Member newMember = Member.builder()
                .email(memberJoinDto.getEmail())
                .password(passwordEncoder.encode(memberJoinDto.getPassword1()))
                .college(memberJoinDto.getCollege())
                .department(memberJoinDto.getDepartment())
                .university(memberJoinDto.getUniversity())
                .studentNum(memberJoinDto.getStudentNum())
                .build();
        memberRepository.save(newMember);
    }

    public String login(MemberLoginDto memberLoginDto) {
        String email = memberLoginDto.getEmail();
        String password = memberLoginDto.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new RuntimeException(email+" 은 존재하지 않는 계정입니다"));

        if (!passwordEncoder.matches(password , member.getPassword())) {
            throw new IllegalArgumentException("잘못된 패스워드입니다");
        }


        return JwtUtil.createJwt(email,secretKey,expireMs);
    }
}
