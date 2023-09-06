package com.cnu.sw2023.member.service;


import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.comment.repository.CommentRepository;
import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.JwtConfig.JwtUtil;
import com.cnu.sw2023.member.domain.Member;
import com.cnu.sw2023.member.exception.NotFoundException;
import com.cnu.sw2023.member.repository.MemberRepository;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMS = 1000 * 60 * 60L;


    public String join(JoinReqDto joinReqDto){
        String email = joinReqDto.getEmail();
        String password = joinReqDto.getPassword();
        if (memberRepository.existsByEmail(email)) {
            return "이미 존재하는 회원입니다";
        }
        Member member = Member.builder().email(email).password(passwordEncoder.encode(password)).build();
        memberRepository.save(member);
        return "회원가입 성공";
    }
    public String login(String email,String password){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 아이디입니다"));
        if (!passwordEncoder.matches(password,member.getPassword())) {
            throw new NotFoundException("비밀번호가 틀렸습니다");
        }
        return JwtUtil.createJwt(email,secretKey,expiredMS);
    }

    public String temp(){
        return JwtUtil.createJwt("temp" , secretKey , expiredMS);
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
}
