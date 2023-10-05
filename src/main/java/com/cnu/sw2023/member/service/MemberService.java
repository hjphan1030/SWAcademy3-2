package com.cnu.sw2023.member.service;


import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.comment.repository.CommentRepository;
import com.cnu.sw2023.exception.EmailException;
import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.DTO.ModifyPasswordDto;
import com.cnu.sw2023.member.exception.DuplicatedException;
import com.cnu.sw2023.config.jwtconfig.JwtUtil;
import com.cnu.sw2023.member.domain.College;
import com.cnu.sw2023.member.domain.Member;
import com.cnu.sw2023.member.exception.NotFoundException;
import com.cnu.sw2023.member.exception.UnMatchedPasswordException;
import com.cnu.sw2023.member.repository.MemberRepository;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    private final Long expiredMS = 1000 * 60 * 60L;


    public String join(JoinReqDto joinReqDto){
        String email = joinReqDto.getEmail();
        String password = joinReqDto.getPassword();
        String memberId = joinReqDto.getMemberId();
        College college = College.fromString(joinReqDto.getCollege());
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedException("중복된 이메일입니다");
        }
        if (memberRepository.existsByMemberId(memberId)) {
            throw new DuplicatedException("중복된 아이디입니다");
        }
        Member member = Member.builder()
                .email(email)
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .college(college)
                .build();
        memberRepository.save(member);
        return "회원가입 성공";
    }
    public String login(String memberId,String password){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 아이디입니다"));
        if (!passwordEncoder.matches(password,member.getPassword())) {
            throw new UnMatchedPasswordException("비밀번호가 일치하지 않습니다");
        }
        return JwtUtil.createJwt(memberId,secretKey,expiredMS);
    }

    public String temp(){
        return JwtUtil.createJwt("tempId" , secretKey , expiredMS);
    }



    public Page<Post> findMyPosts(String email,int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        return postRepository.findPostsByEmail(email,pageable);

    }

    public Page<Comment> findMyComments(String email, int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdAt"));
        return commentRepository.findByEmail(email,pageable);
    }

    public boolean existingEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public String getEmailByMemberId(String memberId){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 아이디입니다"));
        return member.getEmail();
    }

    @PostConstruct //초기 회원 정보 삽입
    public String join(){
        String email = "1@1";
        String password = "123";
        String memberId = "a123";
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .college(College.fromString("예술대학"))
                .memberId(memberId)
                .build();
        Member member1 = Member.builder()
                .email("2@2")
                .password(passwordEncoder.encode("123"))
                .college(College.경상대학).memberId("b123").build();
        Member member2 = Member.builder()
                .email("3@3")
                .password(passwordEncoder.encode("123"))
                .college(College.사범대학).memberId("c123").build();
        memberRepository.save(member);
        memberRepository.save(member1);
        memberRepository.save(member2);
        log.info("아이디 : {} 비밀번호 : {} 으로 계정이 등록되었습니다",memberId,password);
        return "회원가입 성공";
    }
    public List<String> getCollegeList() {
        List<String> collegeList = new ArrayList<>();
        for (College college : College.values()) {
            collegeList.add(college.getName());
        }
        return collegeList;
    }

    public String getPasswordByEmail(String email) throws EmailException {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            return byEmail.get().getPassword();
        } else {
            throw new EmailException("존재 하지 않는 이메일입니다");
        }
    }

    public void modifyPassword(ModifyPasswordDto modifyPasswordDto, String email) throws EmailException {
        Member member = memberRepository.findByEmail(email).get();
        member.setPassword(passwordEncoder.encode(modifyPasswordDto.getNewPassword1()));
        memberRepository.save(member);
    }
}
