package com.brick.codesnippetbackend.interceptor;


import com.brick.codesnippetbackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Slf4j
@Component
@NoArgsConstructor
public class ResponseWithTokenInterceptor implements HandlerInterceptor
{
    private JwtUtils jwtUtils;

    @Autowired
    ResponseWithTokenInterceptor(JwtUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (Objects.isNull(request.getHeader("token"))) {
            log.warn("Token is null: {}", request.getServletPath());
            return false;
        }

        return jwtUtils.isTokenExpired(
                request.getHeader("token"));
    }
}
