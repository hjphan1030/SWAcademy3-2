package com.cnu.sw2023.member.JwtConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {
   @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session != null) {
            String token = (String) session.getAttribute("token");
            log.info("interceptor token : {}",token);
            if (token != null) {
                request.setAttribute("Authorization", "Bearer " + token);
            }
        }
        return true;
    }


}