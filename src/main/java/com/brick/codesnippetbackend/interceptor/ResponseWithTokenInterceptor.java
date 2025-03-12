package com.brick.codesnippetbackend.interceptor;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.brick.codesnippetbackend.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
@Component
@NoArgsConstructor
public class ResponseWithTokenInterceptor implements HandlerInterceptor
{
    private JWTUtil jwtUtils;

    @Autowired
    ResponseWithTokenInterceptor(JWTUtil jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (Objects.isNull(request.getHeader("token"))) {
            log.warn("Token is null: {}", request.getServletPath());

            JSONObject obj = JSONUtil.createObj();
            obj.set("code", 401);
            obj.set("data", "Token is null");

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(401);
            PrintWriter writer = response.getWriter();
            writer.write(obj.toStringPretty());
            writer.flush();
            writer.close();


            return false;
        }
        boolean isExpired = jwtUtils.isExpired(request.getHeader("token"));
        if (isExpired) {
            log.info("Token is expired: {}", request.getServletPath());
            JSONObject obj = JSONUtil.createObj();
            obj.set("code", 401);
            obj.set("data", "Token is expired");

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(401);
            PrintWriter writer = response.getWriter();
            writer.write(obj.toStringPretty());
            writer.flush();
            writer.close();
            return false;
        }

        return true;

    }
}
