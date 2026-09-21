package com.huaihan.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtils {

    // 密钥：原始字符串，至少32字符(256bit)，满足HS256要求
    private static final String SECRET_KEY = "5L2g5aW95LiW55WM5L2g5aW95LiW55WM";
    // 过期时间 1小时，单位毫秒
    private static final long EXPIRE_TIME = 3600 * 1000L;

    /**
     * 生成JWT令牌
     * @param claims 自定义载荷数据
     * @return jwt token字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                // 设置过期时间 当前时间+1小时
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌，获取载荷Claims
     * @param token jwt令牌字符串
     * @return Claims 载荷对象
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
