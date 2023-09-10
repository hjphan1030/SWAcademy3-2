package com.cnu.sw2023.member.JwtConfig;

import com.cnu.sw2023.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(authorization);
        if (authorization == null ) {
            log.error("authorization 이 null 입니다");
            filterChain.doFilter(request,response);
            return ;
        }
        if (!authorization.startsWith("Bearer ")) {
            log.error("authorization 을 잘못보냈습니다");
            filterChain.doFilter(request,response);
            return ;
        }

        String token = authorization.split(" ")[1];
        System.out.println(token);
        if (JwtUtil.isExpired(token,secretKey)) {
            log.error("Token이 만료되었습니다");
            filterChain.doFilter(request,response);
            return ;
        }


        String email = JwtUtil.getUserName(token,secretKey);
        log.info("email : {}",email);
        log.info("토큰 확인 완료 {}",token);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email , null , List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
