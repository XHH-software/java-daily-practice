package com.huaihan.interceptor;

import com.huaihan.utils.UserContext;
import com.huaihan.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 3. 获取请求头中的token
        String token = request.getHeader("token");

        // 4. 判断token是否存在
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，响应401状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 5. 校验令牌，解析empId存入UserContext
        try {
            Claims claims = JwtUtils.parseToken(token);
            Integer empId = Integer.valueOf(claims.get("empId").toString());
            UserContext.setEmpId(empId);
            log.info("当前登录员工ID:{},将其存入ThreadLocal",empId);
        } catch (Exception e) {
            log.info("令牌非法，响应401状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        log.info("令牌合法，放行");
        return true;
    }

    // 请求结束，清理ThreadLocal，防止内存泄漏
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}
