package com.huaihan;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    public static void main(String[] args) {
        JwtTest jwtTest = new JwtTest();
        String token = jwtTest.testGenerateJwt();// 生成JWT令牌
        jwtTest.testParseJwT(token); // 解析JWT令牌
    }


    // 密钥：原始字符串，至少32字符(256bit)，满足HS256要求
    private static final String SECRET_KEY = "5L2g5aW95LiW55WM5L2g5aW95LiW55WM";

    /**
     * 生成JWT令牌
     */
    private String testGenerateJwt(){
        //1. 先构建map，存放要存入JWT的信息
        Map<String,Object> map = new HashMap<>();
        map.put("id",123);          //员工id
        map.put("username","linli");//账号
        map.put("name","林立");      //员工姓名

        String compact = Jwts.builder()
                // 签名：只传密钥，算法自动从密钥识别
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                // 批量放入map里所有数据
                .addClaims(map)
                // 设置 token 过期时间: 当前时间 + 1小时
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000L))
                .compact();
        System.out.println("生成的token：" + compact);
        return compact;
    }

    /**
     * 解析JWT令牌
     */
    private void testParseJwT(String token) {
        Claims body = Jwts.parser()
                // 设置签名校验密钥
                .setSigningKey(SECRET_KEY)
                // 解析令牌
                .parseClaimsJws(token)
                .getBody();
        System.out.println("解析生成的令牌：" + body);
    }
}
